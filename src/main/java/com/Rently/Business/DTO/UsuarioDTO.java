package com.Rently.Business.DTO;

import com.Rently.Persistence.Entity.Rol;

import java.time.LocalDate;

public class UsuarioDTO extends PersonaDTO{
    public UsuarioDTO() { super(); }

    public UsuarioDTO(Long id, String nombre, String email, String telefono,
                      String contrasena, LocalDate fechaNacimiento,
                      Rol rol, String fotoPerfil) {
        super(id, nombre, email, telefono, contrasena, fechaNacimiento, rol, fotoPerfil);
    }
}
