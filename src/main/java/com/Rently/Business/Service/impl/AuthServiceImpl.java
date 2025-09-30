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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(UsuarioDTO request) {
        usuarioService.registerUser(request);
        // After registration, fetch the user and generate a token
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado después del registro"));
        String token = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(token).build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // If authentication is successful, generate a token
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Should not happen if authentication is successful
        String token = jwtService.generateToken(usuario);
        return AuthResponse.builder().token(token).build();
    }
}
