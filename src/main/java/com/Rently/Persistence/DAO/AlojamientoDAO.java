package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.Servicio;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Repository.AlojamientoRepository;
import com.Rently.Persistence.Repository.AnfitrionRepository;
import com.Rently.Persistence.Repository.ServicioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AlojamientoDAO {

    private final AlojamientoRepository alojamientoRepository;
    private final AnfitrionRepository anfitrionRepository;
    private final ServicioRepository servicioRepository;
    private final AlojamientoMapper alojamientoMapper;

    public AlojamientoDAO(AlojamientoRepository alojamientoRepository,
                          AlojamientoMapper alojamientoMapper,
                          AnfitrionRepository anfitrionRepository,
                          ServicioRepository servicioRepository) {
        this.alojamientoRepository = alojamientoRepository;
        this.alojamientoMapper = alojamientoMapper;
        this.anfitrionRepository = anfitrionRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional
    public AlojamientoDTO crearAlojamiento(AlojamientoDTO dto) {
        Alojamiento alojamiento = alojamientoMapper.toEntity(dto);

        // Setear anfitrión
        Anfitrion anfitrion = anfitrionRepository.findById(dto.getAnfitrionId())
                .orElseThrow(() -> new RuntimeException("El anfitrión no existe"));
        alojamiento.setAnfitrion(anfitrion);

        // Setear servicios
        if (dto.getServiciosId() != null && !dto.getServiciosId().isEmpty()) {
            List<Servicio> servicios = servicioRepository.findAllById(dto.getServiciosId());
            alojamiento.setServicios(servicios);
        }

        // Guardar alojamiento
        Alojamiento saved = alojamientoRepository.save(alojamiento);

        // 🔹 Recargar para asegurar que se carguen relaciones ManyToMany
        Alojamiento fullyLoaded = alojamientoRepository.findById(saved.getId())
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        return alojamientoMapper.toDTO(fullyLoaded);
    }

    public Optional<AlojamientoDTO> actualizar(Long id, AlojamientoDTO dto) {
        return alojamientoRepository.findById(id).map(alojamiento -> {
            alojamientoMapper.updateFromDTO(alojamiento, dto);

            // Actualizar servicios si vienen IDs
            if (dto.getServiciosId() != null) {
                List<Servicio> servicios = servicioRepository.findAllById(dto.getServiciosId());
                alojamiento.setServicios(servicios);
            }

            Alojamiento actualizado = alojamientoRepository.save(alojamiento);
            return alojamientoMapper.toDTO(actualizado);
        });
    }

    // Los demás métodos se mantienen igual
    public Optional<AlojamientoDTO> buscarPorId(Long id) {
        return alojamientoRepository.findById(id)
                .map(alojamientoMapper::toDTO);
    }

    public List<AlojamientoDTO> listarTodos() {
        return alojamientoMapper.toDTOList(alojamientoRepository.findAll());
    }

    public List<AlojamientoDTO> listarActivos() {
        return alojamientoMapper.toDTOList(
                alojamientoRepository.findAll().stream()
                        .filter(a -> !a.isEliminado())
                        .toList()
        );
    }

    public List<AlojamientoDTO> buscarPorCiudad(String ciudad) {
        return alojamientoMapper.toDTOList(alojamientoRepository.findByCiudadAndEliminadoFalse(ciudad));
    }

    public List<AlojamientoDTO> buscarPorPrecio(Double min, Double max) {
        return alojamientoMapper.toDTOList(alojamientoRepository.findByPrecioPorNocheBetweenAndEliminadoFalse(min, max));
    }

    public List<AlojamientoDTO> buscarPorAnfitrion(Long anfitrionId) {
        return alojamientoMapper.toDTOList(alojamientoRepository.findByAnfitrionId(anfitrionId));
    }

    public boolean eliminar(Long id) {
        return alojamientoRepository.findById(id).map(alojamiento -> {
            alojamiento.setEliminado(true);  // ← Soft delete
            alojamientoRepository.save(alojamiento);
            return true;
        }).orElse(false);
    }


}
