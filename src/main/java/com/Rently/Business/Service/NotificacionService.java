package com.Rently.Business.Service;

import com.Rently.Business.DTO.NotificacionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de notificaciones.
 */
public interface NotificacionService {
    /**
     * Crea una nueva notificación.
     *
     * @param notificacionDTO la notificación a crear
     * @return la notificación creada
     */
    NotificacionDTO create(NotificacionDTO notificacionDTO);

    /**
     * Busca una notificación por su ID.
     *
     * @param id el ID de la notificación
     * @return un optional que contiene la notificación si se encuentra, o vacío en caso contrario
     */
    Optional<NotificacionDTO> findById(Long id);

    /**
     * Devuelve una lista de todas las notificaciones.
     *
     * @return una lista de todas las notificaciones
     */
    List<NotificacionDTO> findAll();

    /**
     * Busca notificaciones no leídas por el ID del usuario.
     *
     * @param userId el ID del usuario
     * @return una lista de notificaciones no leídas del usuario especificado
     */
    List<NotificacionDTO> findUnreadByUserId(Long userId);

    /**
     * Actualiza una notificación existente.
     *
     * @param id              el ID de la notificación
     * @param notificacionDTO la notificación con la información actualizada
     * @return un optional que contiene la notificación actualizada si se encuentra, o vacío en caso contrario
     */
    Optional<NotificacionDTO> update(Long id, NotificacionDTO notificacionDTO);

    /**
     * Elimina una notificación por su ID.
     *
     * @param id el ID de la notificación
     */
    void delete(Long id);

    /**
     * Marca una notificación como leída.
     *
     * @param id el ID de la notificación
     * @return un optional que contiene la notificación actualizada si se encuentra, o vacío en caso contrario
     */
    Optional<NotificacionDTO> markAsRead(Long id);

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     *
     * @param userId el ID del usuario
     */
    void markAllAsRead(Long userId);

    /**
     * Busca notificaciones no leídas por el ID del usuario.
     *
     * @param userId el ID del usuario
     * @return una lista de notificaciones no leídas del usuario especificado
     */
    List<NotificacionDTO> findUnreadNotificationsByUserId(Long userId);
}
