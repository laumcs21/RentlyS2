package com.Rently.Business.Service;

import com.Rently.Business.DTO.ComentarioDTO;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de comentarios.
 */
public interface ComentarioService {
    /**
     * Crea un nuevo comentario.
     *
     * @param comentarioDTO el comentario a crear
     * @return el comentario creado
     */
    ComentarioDTO create(ComentarioDTO comentarioDTO);

    /**
     * Busca comentarios por el ID del alojamiento.
     *
     * @param alojamientoId el ID del alojamiento
     * @return una lista de comentarios del alojamiento especificado
     */
    List<ComentarioDTO> findByAlojamientoId(Long alojamientoId);

    /**
     * Actualiza un comentario existente.
     *
     * @param id            el ID del comentario
     * @param comentarioDTO el comentario con la información actualizada
     * @return el comentario actualizado
     */
    ComentarioDTO update(Long id, ComentarioDTO comentarioDTO);

    /**
     * Añade una respuesta a un comentario.
     *
     * @param id       el ID del comentario
     * @param response la respuesta a añadir
     * @return el comentario con la respuesta añadida
     */
    ComentarioDTO addResponse(Long id, String response);

    /**
     * Elimina un comentario por su ID.
     *
     * @param id el ID del comentario
     */
    void delete(Long id);
}
