package com.Rently.Business.Service;

import com.Rently.Business.DTO.Auth.AuthRequest;
import com.Rently.Business.DTO.Auth.AuthResponse;
import com.Rently.Business.DTO.UsuarioDTO;

/**
 * Interfaz de servicio para la autenticación.
 */
public interface AuthService {
    /**
     * Registra un nuevo usuario y genera un token de autenticación.
     *
     * @param request el DTO del usuario a registrar
     * @return una respuesta de autenticación con el token generado
     */
    AuthResponse register(UsuarioDTO request);

    /**
     * Autentica a un usuario y genera un token de autenticación.
     *
     * @param request la solicitud de autenticación con el email y la contraseña
     * @return una respuesta de autenticación con el token generado
     */
    AuthResponse login(AuthRequest request);
}
