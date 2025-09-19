package com.Rently.Business.DTO;

import com.Rently.Persistence.Entity.EstadoPago;
import java.time.LocalDateTime;

public class TransaccionDTO {

    private Long id;
    private Long reservaId;   // en vez de toda la entidad Reserva
    private Double monto;
    private String moneda;
    private String metodoPago;
    private EstadoPago estadoPago;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TransaccionDTO() {}

    public TransaccionDTO(Long id, Long reservaId, Double monto, String moneda,
                          String metodoPago, EstadoPago estadoPago,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.reservaId = reservaId;
        this.monto = monto;
        this.moneda = moneda;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "TransaccionDTO{" +
                "id=" + id +
                ", reservaId=" + reservaId +
                ", monto=" + monto +
                ", moneda='" + moneda + '\'' +
                ", metodoPago='" + metodoPago + '\'' +
                ", estadoPago=" + estadoPago +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
