package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.Service.AlojamientoService;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de alojamientos.
 */
@Service
public class AlojamientoServiceImpl implements AlojamientoService {

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    /**
     * Crea un nuevo alojamiento.
     *
     * @param alojamientoDTO el alojamiento a crear
     * @return el alojamiento creado
     */
    @Override
    public AlojamientoDTO create(AlojamientoDTO alojamientoDTO) {
        return alojamientoDAO.crearAlojamiento(alojamientoDTO);
    }

    /**
     * Busca un alojamiento por su ID.
     *
     * @param id el ID del alojamiento
     * @return un optional que contiene el alojamiento si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AlojamientoDTO> findById(Long id) {
        return alojamientoDAO.buscarPorId(id);
    }

    /**
     * Devuelve una lista de todos los alojamientos.
     *
     * @return una lista de todos los alojamientos
     */
    @Override
    public List<AlojamientoDTO> findAll() {
        return alojamientoDAO.listarTodos();
    }

    /**
     * Devuelve una lista de todos los alojamientos activos.
     *
     * @return una lista de todos los alojamientos activos
     */
    @Override
    public List<AlojamientoDTO> findActive() {
        return alojamientoDAO.listarActivos();
    }

    /**
     * Busca alojamientos por ciudad.
     *
     * @param city la ciudad por la que buscar
     * @return una lista de alojamientos en la ciudad especificada
     */
    @Override
    public List<AlojamientoDTO> findByCity(String city) {
        return alojamientoDAO.buscarPorCiudad(city);
    }

    /**
     * Busca alojamientos por un rango de precios.
     *
     * @param min el precio mínimo
     * @param max el precio máximo
     * @return una lista de alojamientos dentro del rango de precios especificado
     */
    @Override
    public List<AlojamientoDTO> findByPrice(Double min, Double max) {
        return alojamientoDAO.buscarPorPrecio(min, max);
    }

    /**
     * Busca alojamientos por el ID del anfitrión.
     *
     * @param hostId el ID del anfitrión
     * @return una lista de alojamientos del anfitrión especificado
     */
    @Override
    public List<AlojamientoDTO> findByHost(Long hostId) {
        return alojamientoDAO.buscarPorAnfitrion(hostId);
    }

    /**
     * Actualiza un alojamiento existente.
     *
     * @param id             el ID del alojamiento
     * @param alojamientoDTO el alojamiento con la información actualizada
     * @return un optional que contiene el alojamiento actualizado si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AlojamientoDTO> update(Long id, AlojamientoDTO alojamientoDTO) {
        return alojamientoDAO.actualizar(id, alojamientoDTO);
    }

    /**
     * Elimina un alojamiento por su ID.
     *
     * @param id el ID del alojamiento
     * @return true si el alojamiento fue eliminado, false en caso contrario
     */
    @Override
    public boolean delete(Long id) {
        return alojamientoDAO.eliminar(id);
    }
}
