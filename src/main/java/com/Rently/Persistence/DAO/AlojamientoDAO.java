package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AlojamientoDetalleDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Repository.AlojamientoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AlojamientoDAO {

    private final AlojamientoRepository alojamientoRepository;
    private final AlojamientoMapper alojamientoMapper;

    public AlojamientoDAO(AlojamientoRepository alojamientoRepository, AlojamientoMapper alojamientoMapper) {
        this.alojamientoRepository = alojamientoRepository;
        this.alojamientoMapper = alojamientoMapper;
    }

    public AlojamientoDTO crear(AlojamientoDTO dto) {
        Alojamiento alojamiento = alojamientoMapper.toEntity(dto);
        Alojamiento guardado = alojamientoRepository.save(alojamiento);
        return alojamientoMapper.toDTO(guardado);
    }

    public Optional<AlojamientoDetalleDTO> obtenerPorId(Long id) {
        return alojamientoRepository.findById(id)
                .map(alojamientoMapper::toDetalleDTO);
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

    public Optional<AlojamientoDTO> actualizar(Long id, AlojamientoDTO dto) {
        return alojamientoRepository.findById(id).map(alojamiento -> {
            alojamientoMapper.updateFromDTO(alojamiento, dto);
            Alojamiento actualizado = alojamientoRepository.save(alojamiento);
            return alojamientoMapper.toDTO(actualizado);
        });
    }

    public boolean eliminar(Long id) {
        return alojamientoRepository.findById(id).map(alojamiento -> {
            alojamiento.setEliminado(true);
            alojamientoRepository.save(alojamiento);
            return true;
        }).orElse(false);
    }
}

