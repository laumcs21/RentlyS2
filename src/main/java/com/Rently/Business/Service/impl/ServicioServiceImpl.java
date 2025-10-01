package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.ServicioDTO;
import com.Rently.Business.Service.ServicioService;
import com.Rently.Persistence.DAO.ServicioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la gestión de servicios.
 */
@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioDAO servicioDAO;

    /**
     * Crea un nuevo servicio.
     *
     * @param servicioDTO el servicio a crear
     * @return el servicio creado
     */
    @Override
    public ServicioDTO create(ServicioDTO servicioDTO) {
        return servicioDAO.crearServicio(servicioDTO);
    }

    /**
     * Devuelve una lista de todos los servicios.
     *
     * @return una lista de todos los servicios
     */
    @Override
    public List<ServicioDTO> findAll() {
        return servicioDAO.obtenerServicios();
    }

    /**
     * Busca un servicio por su ID.
     *
     * @param id el ID del servicio
     * @return el servicio si se encuentra, o null en caso contrario
     */
    @Override
    public ServicioDTO findById(Long id) {
        return servicioDAO.obtenerServicioPorId(id);
    }

    /**
     * Actualiza un servicio existente.
     *
     * @param id          el ID del servicio
     * @param servicioDTO el servicio con la información actualizada
     * @return el servicio actualizado si se encuentra, o null en caso contrario
     */
    @Override
    public ServicioDTO update(Long id, ServicioDTO servicioDTO) {
        return servicioDAO.actualizarServicio(id, servicioDTO);
    }

    /**
     * Elimina un servicio por su ID.
     *
     * @param id el ID del servicio
     * @return true si el servicio fue eliminado, false en caso contrario
     */
    @Override
    public boolean delete(Long id) {
        return servicioDAO.eliminarServicio(id);
    }
}
