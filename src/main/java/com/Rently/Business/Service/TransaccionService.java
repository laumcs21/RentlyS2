package com.Rently.Business.Service;

import com.Rently.Business.DTO.TransaccionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de transacciones.
 */
public interface TransaccionService {
    /**
     * Crea una nueva transacción.
     *
     * @param transaccionDTO la transacción a crear
     * @return la transacción creada
     */
    TransaccionDTO create(TransaccionDTO transaccionDTO);

    /**
     * Busca una transacción por su ID.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción si se encuentra, o vacío en caso contrario
     */
    Optional<TransaccionDTO> findById(Long id);

    /**
     * Devuelve una lista de todas las transacciones.
     *
     * @return una lista de todas las transacciones
     */
    List<TransaccionDTO> findAll();

    /**
     * Busca transacciones por el ID de la reserva.
     *
     * @param reservaId el ID de la reserva
     * @return una lista de transacciones de la reserva especificada
     */
    List<TransaccionDTO> findByReservaId(Long reservaId);

    /**
     * Elimina una transacción por su ID.
     *
     * @param id el ID de la transacción
     */
    void delete(Long id);

    /**
     * Aprueba una transacción.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción actualizada si se encuentra, o vacío en caso contrario
     */
    Optional<TransaccionDTO> approve(Long id);

    /**
     * Rechaza una transacción.
     *
     * @param id el ID de la transacción
     * @return un optional que contiene la transacción actualizada si se encuentra, o vacío en caso contrario
     */
    Optional<TransaccionDTO> reject(Long id);
}
