package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.DTO.ComentarioDTO;
import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Business.Service.ComentarioService;
import com.Rently.Business.Service.UsuarioService;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.DAO.ComentarioDAO;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de comentarios.
 * Aplica validaciones de negocio antes de delegar en el DAO.
 */
@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    /**
     * Crea un nuevo comentario.
     *
     * @param comentarioDTO el comentario a crear
     * @return el comentario creado
     * @throws IllegalArgumentException si faltan datos obligatorios
     */
    @Override
    public ComentarioDTO create(ComentarioDTO comentarioDTO) {
        if (comentarioDTO == null) {
            throw new IllegalArgumentException("El comentario no puede ser nulo.");
        }
        if (comentarioDTO.getAlojamientoId() == null || comentarioDTO.getAlojamientoId() <= 0) {
            throw new IllegalArgumentException("Debe especificarse un ID de alojamiento válido.");
        }
        if (comentarioDTO.getUsuarioId() == null || comentarioDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("Debe especificarse un ID de usuario válido.");
        }
        if (comentarioDTO.getComentario() == null || comentarioDTO.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto del comentario no puede estar vacío.");
        }
        if (comentarioDTO.getCalificacion() == null || comentarioDTO.getCalificacion() < 1 || comentarioDTO.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        return comentarioDAO.crearComentario(comentarioDTO);
    }

    /**
     * Busca comentarios por el ID del alojamiento.
     *
     * @param alojamientoId el ID del alojamiento
     * @return una lista de comentarios del alojamiento especificado
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public List<ComentarioDTO> findByAlojamientoId(Long alojamientoId) {
        if (alojamientoId == null || alojamientoId <= 0) {
            throw new IllegalArgumentException("El ID del alojamiento debe ser válido.");
        }

        List<ComentarioDTO> comentarios = comentarioDAO.obtenerComentariosPorAlojamiento(alojamientoId);
        if (comentarios == null || comentarios.isEmpty()) {
            throw new IllegalStateException("No se encontraron comentarios para el alojamiento especificado.");
        }

        return comentarios;
    }

    /**
     * Actualiza un comentario existente.
     *
     * @param id            el ID del comentario
     * @param comentarioDTO el comentario con la información actualizada
     * @return el comentario actualizado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public ComentarioDTO update(Long id, ComentarioDTO comentarioDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del comentario debe ser válido.");
        }
        if (comentarioDTO == null) {
            throw new IllegalArgumentException("El comentario actualizado no puede ser nulo.");
        }
        if (comentarioDTO.getComentario() != null && comentarioDTO.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto del comentario no puede estar vacío.");
        }
        if (comentarioDTO.getCalificacion() != null &&
                (comentarioDTO.getCalificacion() < 1 || comentarioDTO.getCalificacion() > 5)) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        return comentarioDAO.actualizarComentario(id, comentarioDTO);
    }

    /**
     * Añade una respuesta al comentario (solo permitido al anfitrión).
     *
     * @param id       el ID del comentario
     * @param response la respuesta a añadir
     * @return el comentario actualizado con la respuesta
     * @throws IllegalArgumentException si el ID o la respuesta son inválidos
     */
    @Override
    public ComentarioDTO addResponse(Long id, String response) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del comentario debe ser válido.");
        }
        if (response == null || response.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta no puede estar vacía.");
        }

        // Buscar comentario existente
        ComentarioDTO comentarioExistente = comentarioDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el comentario especificado."));

        // Obtener anfitrión del alojamiento
        AlojamientoDTO alojamientoDTO = alojamientoDAO.buscarPorId(comentarioExistente.getAlojamientoId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el alojamiento especificado."));

        Long anfitrionAlojamientoId = alojamientoDTO.getAnfitrionId();
        AnfitrionDTO anfitrionDelAlojamiento = anfitrionDAO.buscarPorId(anfitrionAlojamientoId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el anfitrión especificado."));


        String emailActual = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();

        UsuarioDTO usuarioActual = usuarioService.findUserByEmail(emailActual)
                .orElseThrow(() -> new IllegalStateException("No se encontró el usuario autenticado."));

        // Validar que el usuario autenticado sea el anfitrión del alojamiento
        if (!usuarioActual.getId().equals(anfitrionDelAlojamiento.getUsuario().getId())) {
            throw new IllegalStateException("Solo el anfitrión del alojamiento puede responder comentarios.");
        }

        // Agregar respuesta y guardar
        comentarioExistente.setRespuesta(response);
        return comentarioDAO.responderComentario(id, comentarioExistente);
    }


    /**
     * Elimina un comentario por su ID.
     *
     * @param id el ID del comentario
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido para eliminar el comentario.");
        }

        boolean existe = comentarioDAO.existeComentario(id);
        if (!existe) {
            throw new IllegalStateException("No existe un comentario con el ID especificado.");
        }

        comentarioDAO.eliminarComentario(id);
    }
}

