package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Usuario saved = usuarioRepository.save(usuario);
        return personaMapper.usuarioToDTO(saved);
    }


    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(personaMapper::usuarioToDTO);
    }


    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(personaMapper::usuarioToDTO);
    }


    public List<UsuarioDTO> listarTodos() {
        return personaMapper.usuariosToDTO(usuarioRepository.findAll());
    }


    public Optional<UsuarioDTO> actualizarUsuario(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            personaMapper.updateUsuarioFromDTO(usuario, dto);
            Usuario updated = usuarioRepository.save(usuario);
            return personaMapper.usuarioToDTO(updated);
        });
    }


    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

