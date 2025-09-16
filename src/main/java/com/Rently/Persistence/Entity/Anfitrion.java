package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Anfitrion extends Persona {

    private String descripcion;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL)
    private List<Alojamiento> alojamientos = new ArrayList<>();

    public Anfitrion() {
        super();
    }

    public Anfitrion(Long id, String nombre, String email, String telefono, String contrasena, java.time.LocalDate fechaNacimiento, String descripcion, String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.ANFITRION, fotoPerfil);
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Alojamiento> getAlojamientos() { return alojamientos; }
    public void setAlojamientos(List<Alojamiento> alojamientos) { this.alojamientos = alojamientos; }
}

