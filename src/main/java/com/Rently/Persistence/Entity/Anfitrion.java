package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "anfitrion")
public class Anfitrion extends Persona {

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "anfitrion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alojamiento> alojamientos = new ArrayList<>();

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Anfitrion() {
        super();
    }

    public Anfitrion(Long id,
                     String nombre,
                     String email,
                     String telefono,
                     String contrasena,
                     LocalDate fechaNacimiento,
                     String descripcion,
                     String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, Rol.ANFITRION, fotoPerfil);
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Alojamiento> getAlojamientos() { return alojamientos; }
    public void setAlojamientos(List<Alojamiento> alojamientos) { this.alojamientos = alojamientos; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}



