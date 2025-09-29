package com.Rently.Business.Service;

import com.Rently.Business.DTO.Auth.AuthRequest;
import com.Rently.Business.DTO.Auth.AuthResponse;
import com.Rently.Business.DTO.UsuarioDTO;

public interface AuthService {
    AuthResponse register(UsuarioDTO request);
    AuthResponse login(AuthRequest request);
}
