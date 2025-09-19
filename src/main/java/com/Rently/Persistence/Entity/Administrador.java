package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "administrador")
public class Administrador extends Persona {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Administrador() {
        super();
    }

    public Administrador(Long id,
                         String nombre,
                         String email,
                         String telefono,
                         String contrasena,
                         LocalDate fechaNacimiento,
                         String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.ADMINISTRADOR, fotoPerfil);
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}



