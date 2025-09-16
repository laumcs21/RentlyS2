package com.Rently.Persistence.Entity;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Persona {

    private String rolSistema;

    public Administrador() {
        super();
    }

    public Administrador(Long id, String nombre, String email, String telefono, String contrasena, java.time.LocalDate fechaNacimiento, String rolSistema, String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.ADMINISTRADOR, fotoPerfil);
        this.rolSistema = rolSistema;
    }

    public String getRolSistema() { return rolSistema; }
    public void setRolSistema(String rolSistema) { this.rolSistema = rolSistema; }
}

