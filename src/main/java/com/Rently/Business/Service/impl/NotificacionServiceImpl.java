package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.NotificacionDTO;
import com.Rently.Business.Service.NotificacionService;
import com.Rently.Persistence.DAO.NotificacionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de notificaciones.
 */
@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionDAO notificacionDAO;

    /**
     * Crea una nueva notificación.
     *
     * @param notificacionDTO la notificación a crear
     * @return la notificación creada
     */
    @Override
    public NotificacionDTO create(NotificacionDTO notificacionDTO) {
        return notificacionDAO.crear(notificacionDTO);
    }

    /**
     * Busca una notificación por su ID.
     *
     * @param id el ID de la notificación
     * @return un optional que contiene la notificación si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<NotificacionDTO> findById(Long id) {
        return notificacionDAO.buscarPorId(id);
    }

    /**
     * Devuelve una lista de todas las notificaciones.
     *
     * @return una lista de todas las notificaciones
     */
    @Override
    public List<NotificacionDTO> findAll() {
        return notificacionDAO.listarTodas();
    }

    /**
     * Busca notificaciones no leídas por el ID del usuario.
     *
     * @param userId el ID del usuario
     * @return una lista de notificaciones no leídas del usuario especificado
     */
    @Override
    public List<NotificacionDTO> findUnreadByUserId(Long userId) {
        return notificacionDAO.listarNoLeidasPorUsuario(userId);
    }

    /**
     * Actualiza una notificación existente.
     *
     * @param id              el ID de la notificación
     * @param notificacionDTO la notificación con la información actualizada
     * @return un optional que contiene la notificación actualizada si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<NotificacionDTO> update(Long id, NotificacionDTO notificacionDTO) {
        return notificacionDAO.actualizar(id, notificacionDTO);
    }

    /**
     * Elimina una notificación por su ID.
     *
     * @param id el ID de la notificación
     */
    @Override
    public void delete(Long id) {
        notificacionDAO.eliminar(id);
    }

    /**
     * Marca una notificación como leída.
     *
     * @param id el ID de la notificación
     * @return un optional que contiene la notificación actualizada si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<NotificacionDTO> markAsRead(Long id) {
        return notificacionDAO.marcarComoLeida(id);
    }

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     *
     * @param userId el ID del usuario
     */
    @Override
    public void markAllAsRead(Long userId) {
        notificacionDAO.marcarTodasComoLeidas(userId);
    }

    /**
     * Busca notificaciones no leídas por el ID del usuario.
     *
     * @param userId el ID del usuario
     * @return una lista de notificaciones no leídas del usuario especificado
     */
    @Override
    public List<NotificacionDTO> findUnreadNotificationsByUserId(Long userId) {
        return notificacionDAO.buscarNoLeidasPorUsuario(userId);
    }
}
