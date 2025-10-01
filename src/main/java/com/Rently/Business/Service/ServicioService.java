package com.Rently.Business.Service;

import com.Rently.Business.DTO.ServicioDTO;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de servicios.
 */
public interface ServicioService {
    /**
     * Crea un nuevo servicio.
     *
     * @param servicioDTO el servicio a crear
     * @return el servicio creado
     */
    ServicioDTO create(ServicioDTO servicioDTO);

    /**
     * Devuelve una lista de todos los servicios.
     *
     * @return una lista de todos los servicios
     */
    List<ServicioDTO> findAll();

    /**
     * Busca un servicio por su ID.
     *
     * @param id el ID del servicio
     * @return el servicio si se encuentra, o null en caso contrario
     */
    ServicioDTO findById(Long id);

    /**
     * Actualiza un servicio existente.
     *
     * @param id          el ID del servicio
     * @param servicioDTO el servicio con la información actualizada
     * @return el servicio actualizado si se encuentra, o null en caso contrario
     */
    ServicioDTO update(Long id, ServicioDTO servicioDTO);

    /**
     * Elimina un servicio por su ID.
     *
     * @param id el ID del servicio
     * @return true si el servicio fue eliminado, false en caso contrario
     */
    boolean delete(Long id);
}
