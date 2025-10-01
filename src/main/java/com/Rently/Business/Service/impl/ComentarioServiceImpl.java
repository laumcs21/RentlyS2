package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.ComentarioDTO;
import com.Rently.Business.Service.ComentarioService;
import com.Rently.Persistence.DAO.ComentarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la gestión de comentarios.
 */
@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    /**
     * Crea un nuevo comentario.
     *
     * @param comentarioDTO el comentario a crear
     * @return el comentario creado
     */
    @Override
    public ComentarioDTO create(ComentarioDTO comentarioDTO) {
        return comentarioDAO.crearComentario(comentarioDTO);
    }

    /**
     * Busca comentarios por el ID del alojamiento.
     *
     * @param alojamientoId el ID del alojamiento
     * @return una lista de comentarios del alojamiento especificado
     */
    @Override
    public List<ComentarioDTO> findByAlojamientoId(Long alojamientoId) {
        return comentarioDAO.obtenerComentariosPorAlojamiento(alojamientoId);
    }

    /**
     * Actualiza un comentario existente.
     *
     * @param id            el ID del comentario
     * @param comentarioDTO el comentario con la información actualizada
     * @return el comentario actualizado
     */
    @Override
    public ComentarioDTO update(Long id, ComentarioDTO comentarioDTO) {
        return comentarioDAO.actualizarComentario(id, comentarioDTO);
    }

    /**
     * Añade una respuesta a un comentario.
     *
     * @param id       el ID del comentario
     * @param response la respuesta a añadir
     * @return el comentario con la respuesta añadida
     */
    @Override
    public ComentarioDTO addResponse(Long id, String response) {
        return comentarioDAO.responderComentario(id, response);
    }

    /**
     * Elimina un comentario por su ID.
     *
     * @param id el ID del comentario
     */
    @Override
    public void delete(Long id) {
        comentarioDAO.eliminarComentario(id);
    }
}
