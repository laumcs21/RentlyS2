package com.Rently.Business.DTO;

import com.Rently.Persistence.Entity.Rol;

import java.time.LocalDate;

public class AnfitrionDTO extends PersonaDTO{
    public AnfitrionDTO() { super(); }

    public AnfitrionDTO(Long id, String nombre, String email, String telefono,
                        String contrasena, LocalDate fechaNacimiento,
                        Rol rol, String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, rol, fotoPerfil);
    }
}
