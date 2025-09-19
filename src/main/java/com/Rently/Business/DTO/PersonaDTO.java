package com.Rently.Business.DTO;

import com.Rently.Persistence.Entity.Rol;
import java.time.LocalDate;

public abstract class PersonaDTO {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Rol rol;
    private String fotoPerfil;

    public PersonaDTO() {}

    public PersonaDTO(Long id, String nombre, String email, String telefono,
                      LocalDate fechaNacimiento, Rol rol, String fotoPerfil) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    @Override
    public String toString() {
        return "PersonaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", rol=" + rol +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                '}';
    }
}
