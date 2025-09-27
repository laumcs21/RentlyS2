package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne
    private Reserva reserva;

    @ManyToOne
    private Comentario comentario;

    private String mensaje;
    private boolean leida = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Notificacion() {}

    public Notificacion(Long id, Usuario usuario, Reserva reserva, String mensaje, boolean leida) {
        this.id = id;
        this.usuario = usuario;
        this.reserva = reserva;
        this.comentario = comentario;
        this.mensaje = mensaje;
        this.leida = leida;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public Comentario getComentario() { return comentario; }
    public void setComentario(Comentario comentario) { this.comentario = comentario; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Notificacion{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", leida=" + leida +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                '}';
    }
}

