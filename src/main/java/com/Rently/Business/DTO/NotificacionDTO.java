package com.Rently.Business.DTO;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private Long id;
    private Long usuarioId;
    private Long reservaId;
    private Long comentarioId;
    private String mensaje;
    private boolean leida;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotificacionDTO() { }

    public NotificacionDTO(Long id, Long usuarioId, Long reservaId, Long comentarioId,
                           String mensaje, boolean leida, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.reservaId = reservaId;
        this.comentarioId = comentarioId;
        this.mensaje = mensaje;
        this.leida = leida;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }

    public Long getComentarioId() { return comentarioId; }
    public void setComentarioId(Long comentarioId) { this.comentarioId = comentarioId; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", reservaId=" + reservaId +
                ", comentarioId=" + comentarioId +
                ", mensaje='" + mensaje + '\'' +
                ", leida=" + leida +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

