package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioDAO {

    private final UsuarioRepository usuarioRepository;
    private final PersonaMapper personaMapper;

    public UsuarioDAO(UsuarioRepository usuarioRepository, PersonaMapper personaMapper) {
        this.usuarioRepository = usuarioRepository;
        this.personaMapper = personaMapper;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario usuario = personaMapper.dtoToUsuario(dto);
        usuario.setActivo(true); // siempre activo al crearlo
        Usuario saved = usuarioRepository.save(usuario);
        return personaMapper.usuarioToDTO(saved);
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .filter(Usuario::isActivo) // solo devuelve si está activo
                .map(personaMapper::usuarioToDTO);
    }

    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .filter(Usuario::isActivo) // solo devuelve si está activo
                .map(personaMapper::usuarioToDTO);
    }

    public List<UsuarioDTO> listarTodos() {
        // Solo usuarios activos
        return usuarioRepository.findAll().stream()
                .filter(Usuario::isActivo)
                .map(personaMapper::usuarioToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> actualizarUsuario(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (!usuario.isActivo()) {
                return null; // no actualizar si está inactivo
            }
            personaMapper.updateUsuarioFromDTO(usuario, dto);
            Usuario updated = usuarioRepository.save(usuario);
            return personaMapper.usuarioToDTO(updated);
        });
    }

    public boolean eliminarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setActivo(false); // baja lógica
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }
}


