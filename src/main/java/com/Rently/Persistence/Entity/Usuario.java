package com.Rently.Persistence.Entity;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends Persona {

    public Usuario() {
        super();
    }

    public Usuario(Long id, String nombre, String email, String telefono, String contrasena, java.time.LocalDate fechaNacimiento, String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.USUARIO, fotoPerfil);
    }
}

