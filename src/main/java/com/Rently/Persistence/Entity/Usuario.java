package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario extends Persona {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    // 🔹 Relación con reservas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Usuario() {
        super();
    }

    public Usuario(Long id,
                   String nombre,
                   String email,
                   String telefono,
                   String contrasena,
                   LocalDate fechaNacimiento,
                   String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.USUARIO, fotoPerfil);
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @PrePersist
    public void asignarRol() {
        if (getRol() == null) {
            setRol(Rol.USUARIO);
        }
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setUsuario(this); // mantener consistencia
    }

    public void removeReserva(Reserva reserva) {
        reservas.remove(reserva);
        reserva.setUsuario(null); // romper relación
    }
}




