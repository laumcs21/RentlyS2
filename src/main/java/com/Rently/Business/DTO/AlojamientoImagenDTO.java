package com.Rently.Business.DTO;

import java.time.LocalDateTime;

public class AlojamientoImagenDTO {

    private Long id;
    private String url;
    private int orden;
    private Long alojamientoId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AlojamientoImagenDTO() { }

    public AlojamientoImagenDTO(Long id, String url, int orden, Long alojamientoId,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.url = url;
        this.orden = orden;
        this.alojamientoId = alojamientoId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public Long getAlojamientoId() { return alojamientoId; }
    public void setAlojamientoId(Long alojamientoId) { this.alojamientoId = alojamientoId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "AlojamientoImagenDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", orden=" + orden +
                ", alojamientoId=" + alojamientoId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
