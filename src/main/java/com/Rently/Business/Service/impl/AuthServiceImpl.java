package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.Auth.AuthRequest;
import com.Rently.Business.DTO.Auth.AuthResponse;
import com.Rently.Business.Service.AuthService;
import com.Rently.Business.Service.UsuarioService;
import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Configuration.Security.JwtService;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de autenticación.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario y genera un token de autenticación.
     *
     * @param request el DTO del usuario a registrar
     * @return una respuesta de autenticación con el token generado
     */
    @Override
    public AuthResponse register(UsuarioDTO request) {
        usuarioService.registerUser(request);
        // Después del registro, busca el usuario y genera un token
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado después del registro"));
        String token = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(token).build();
    }

    /**
     * Autentica a un usuario y genera un token de autenticación.
     *
     * @param request la solicitud de autenticación con el email y la contraseña
     * @return una respuesta de autenticación con el token generado
     */
    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // Si la autenticación es exitosa, genera un token
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(); // No debería ocurrir si la autenticación es exitosa
        String token = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(token).build();
    }
}
