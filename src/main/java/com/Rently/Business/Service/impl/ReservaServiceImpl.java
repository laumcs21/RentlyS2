package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.ReservaDTO;
import com.Rently.Business.Service.ReservaService;
import com.Rently.Persistence.DAO.ReservaDAO;
import com.Rently.Persistence.Entity.EstadoReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de reservas.
 */
@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaDAO reservaDAO;

    /**
     * Crea una nueva reserva.
     *
     * @param reservaDTO la reserva a crear
     * @return la reserva creada
     */
    @Override
    public ReservaDTO create(ReservaDTO reservaDTO) {
        return reservaDAO.crear(reservaDTO);
    }

    /**
     * Busca una reserva por su ID.
     *
     * @param id el ID de la reserva
     * @return un optional que contiene la reserva si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<ReservaDTO> findById(Long id) {
        return reservaDAO.buscarPorId(id);
    }

    /**
     * Devuelve una lista de todas las reservas.
     *
     * @return una lista de todas las reservas
     */
    @Override
    public List<ReservaDTO> findAll() {
        return reservaDAO.listarTodas();
    }

    /**
     * Busca reservas por el ID del usuario.
     *
     * @param userId el ID del usuario
     * @return una lista de reservas del usuario especificado
     */
    @Override
    public List<ReservaDTO> findByUserId(Long userId) {
        return reservaDAO.listarPorUsuario(userId);
    }

    /**
     * Busca reservas por el ID del alojamiento.
     *
     * @param alojamientoId el ID del alojamiento
     * @return una lista de reservas del alojamiento especificado
     */
    @Override
    public List<ReservaDTO> findByAlojamientoId(Long alojamientoId) {
        return reservaDAO.listarPorAlojamiento(alojamientoId);
    }

    /**
     * Actualiza una reserva existente.
     *
     * @param id         el ID de la reserva
     * @param reservaDTO la reserva con la información actualizada
     * @return un optional que contiene la reserva actualizada si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<ReservaDTO> update(Long id, ReservaDTO reservaDTO) {
        return reservaDAO.actualizar(id, reservaDTO);
    }

    /**
     * Actualiza el estado de una reserva.
     *
     * @param id         el ID de la reserva
     * @param newState el nuevo estado de la reserva
     * @return true si el estado fue actualizado, false en caso contrario
     */
    @Override
    public boolean updateState(Long id, EstadoReserva newState) {
        return reservaDAO.cambiarEstado(id, newState);
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id el ID de la reserva
     * @return true si la reserva fue eliminada, false en caso contrario
     */
    @Override
    public boolean delete(Long id) {
        return reservaDAO.eliminar(id);
    }
}
