package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.ComentarioDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Comentario;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.ComentarioMapper;
import com.Rently.Persistence.Repository.ComentarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComentarioDAO {

    private final ComentarioRepository repository;
    private final ComentarioMapper mapper;

    public ComentarioDAO(ComentarioRepository repository, ComentarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ComentarioDTO crearComentario(ComentarioDTO dto, Usuario usuario, Alojamiento alojamiento) {
        Comentario comentario = mapper.toEntity(dto);
        comentario.setUsuario(usuario);
        comentario.setAlojamiento(alojamiento);
        Comentario saved = repository.save(comentario);
        return mapper.toDTO(saved);
    }

    public List<ComentarioDTO> obtenerComentariosPorAlojamiento(Long alojamientoId) {
        return repository.findByAlojamientoIdOrderByFechaDesc(alojamientoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ComentarioDTO actualizarComentario(Long id, ComentarioDTO dto) {
        Comentario existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con id " + id));
        mapper.updateFromDTO(existente, dto);
        Comentario actualizado = repository.save(existente);
        return mapper.toDTO(actualizado);
    }

    public ComentarioDTO responderComentario(Long id, String respuesta) {
        Comentario existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado con id " + id));
        mapper.responderComentario(existente, respuesta);
        Comentario actualizado = repository.save(existente);
        return mapper.toDTO(actualizado);
    }

    public void eliminarComentario(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Comentario no encontrado con id " + id);
        }
        repository.deleteById(id);
    }
}
