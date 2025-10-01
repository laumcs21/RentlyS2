package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.TransaccionDTO;
import com.Rently.Business.Service.TransaccionService;
import com.Rently.Persistence.DAO.TransaccionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de transacciones.
 */
@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionDAO transaccionDAO;

    /**
     * Crea una nueva transacción.
     *
     * @param transaccionDTO la transacción a crear
     * @return la transacción creada
     */
    @Override
    public TransaccionDTO create(TransaccionDTO transaccionDTO) {
        return transaccionDAO.crear(transaccionDTO);
    }

    /**
     * Busca una transacción por su ID.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<TransaccionDTO> findById(Long id) {
        return transaccionDAO.buscarPorId(id);
    }

    /**
     * Devuelve una lista de todas las transacciones.
     *
     * @return una lista de todas las transacciones
     */
    @Override
    public List<TransaccionDTO> findAll() {
        return transaccionDAO.listarTodas();
    }

    /**
     * Busca transacciones por el ID de la reserva.
     *
     * @param reservaId el ID de la reserva
     * @return una lista de transacciones de la reserva especificada
     */
    @Override
    public List<TransaccionDTO> findByReservaId(Long reservaId) {
        return transaccionDAO.listarPorReserva(reservaId);
    }

    /**
     * Elimina una transacción por su ID.
     *
     * @param id el ID de la transacción
     */
    @Override
    public void delete(Long id) {
        transaccionDAO.eliminar(id);
    }

    /**
     * Aprueba una transacción.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción actualizada si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<TransaccionDTO> approve(Long id) {
        return transaccionDAO.aprobar(id);
    }

    /**
     * Rechaza una transacción.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción actualizada si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<TransaccionDTO> reject(Long id) {
        return transaccionDAO.rechazar(id);
    }
}
