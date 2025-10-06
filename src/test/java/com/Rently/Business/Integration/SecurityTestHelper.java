package com.Rently.Business.Integration;

import com.Rently.Business.DTO.AdministradorDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.DTO.UsuarioDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Helper para simular autenticación en tests de integración.
 * Permite establecer un usuario autenticado sin necesidad de JWT real.
 */
@Component
public class SecurityTestHelper {

    /**
     * Establece un usuario autenticado en el SecurityContext
     * @param usuarioDTO el usuario que se autenticará
     */
    public void authenticateUser(UsuarioDTO usuarioDTO) {
        authenticatePersona(usuarioDTO.getEmail(), usuarioDTO.getContrasena(), usuarioDTO.getRol());
    }

    /**
     * Establece un administrador autenticado en el SecurityContext
     * @param adminDTO el administrador que se autenticará
     */
    public void authenticateUser(AdministradorDTO adminDTO) {
        authenticatePersona(adminDTO.getEmail(), adminDTO.getContrasena(), adminDTO.getRol());
    }

    /**
     * Establece un anfitrión autenticado en el SecurityContext
     * @param anfitrionDTO el anfitrión que se autenticará
     */
    public void authenticateUser(AnfitrionDTO anfitrionDTO) {
        authenticatePersona(anfitrionDTO.getEmail(), anfitrionDTO.getContrasena(), anfitrionDTO.getRol());
    }

    /**
     * Método interno para autenticar cualquier tipo de persona
     */
    private void authenticatePersona(String email, String password, com.Rently.Persistence.Entity.Rol rol) {
        // Si el password es null, usar uno dummy (no se valida en tests)
        String pwd = (password != null) ? password : "dummyPasswordForTests";

        UserDetails userDetails = User.builder()
                .username(email)
                .password(pwd)
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + rol.name())
                ))
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    /**
     * Limpia la autenticación del SecurityContext
     */
    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }


}