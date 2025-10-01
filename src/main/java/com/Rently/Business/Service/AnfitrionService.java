package com.Rently.Business.Service;

import com.Rently.Business.DTO.AnfitrionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de anfitriones.
 */
public interface AnfitrionService {
    /**
     * Crea un nuevo anfitrión.
     *
     * @param anfitrionDTO el anfitrión a crear
     * @return el anfitrión creado
     */
    AnfitrionDTO create(AnfitrionDTO anfitrionDTO);

    /**
     * Busca un anfitrión por su ID.
     *
     * @param id el ID del anfitrión
     * @return un optional que contiene el anfitrión si se encuentra, o vacío en caso contrario
     */
    Optional<AnfitrionDTO> findById(Long id);

    /**
     * Busca un anfitrión por su email.
     *
     * @param email el email del anfitrión
     * @return un optional que contiene el anfitrión si se encuentra, o vacío en caso contrario
     */
    Optional<AnfitrionDTO> findByEmail(String email);

    /**
     * Busca anfitriones por su nombre.
     *
     * @param name el nombre a buscar
     * @return una lista de anfitriones que coinciden con el nombre especificado
     */
    List<AnfitrionDTO> findByName(String name);

    /**
     * Devuelve una lista de todos los anfitriones.
     *
     * @return una lista de todos los anfitriones
     */
    List<AnfitrionDTO> findAll();

    /**
     * Actualiza un anfitrión existente.
     *
     * @param id           el ID del anfitrión
     * @param anfitrionDTO el anfitrión con la información actualizada
     * @return un optional que contiene el anfitrión actualizado si se encuentra, o vacío en caso contrario
     */
    Optional<AnfitrionDTO> update(Long id, AnfitrionDTO anfitrionDTO);

    /**
     * Elimina un anfitrión por su ID.
     *
     * @param id el ID del anfitrión
     * @return true si el anfitrión fue eliminado, false en caso contrario
     */
    boolean delete(Long id);
}
