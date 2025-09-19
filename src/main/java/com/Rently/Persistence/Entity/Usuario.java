package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario extends Persona {

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

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
}



