package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.AlojamientoImagenDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.AlojamientoImagen;
import com.Rently.Persistence.Mapper.AlojamientoImagenMapper;
import com.Rently.Persistence.Repository.AlojamientoImagenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlojamientoImagenDAO {

    private final AlojamientoImagenRepository repository;
    private final AlojamientoImagenMapper mapper;

    public AlojamientoImagenDAO(AlojamientoImagenRepository repository, AlojamientoImagenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AlojamientoImagenDTO crearImagen(AlojamientoImagenDTO dto, Alojamiento alojamiento) {
        AlojamientoImagen imagen = mapper.toEntity(dto);
        imagen.setAlojamiento(alojamiento);
        AlojamientoImagen saved = repository.save(imagen);
        return mapper.toDTO(saved);
    }

    public List<AlojamientoImagenDTO> obtenerImagenesPorAlojamiento(Long alojamientoId) {
        return repository.findByAlojamientoIdOrderByOrdenAsc(alojamientoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public AlojamientoImagenDTO actualizarImagen(Long id, AlojamientoImagenDTO dto) {
        AlojamientoImagen existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagen no encontrada con id " + id));
        mapper.updateFromDTO(existente, dto);
        AlojamientoImagen actualizado = repository.save(existente);
        return mapper.toDTO(actualizado);
    }

    public void eliminarImagen(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Imagen no encontrada con id " + id);
        }
        repository.deleteById(id);
    }
}
