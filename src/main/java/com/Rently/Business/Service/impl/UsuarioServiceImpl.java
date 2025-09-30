package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Business.Service.UsuarioService;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz de servicio para Usuarios.
 * Contiene la lógica de negocio y orquesta las operaciones con la capa de persistencia.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaMapper personaMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PersonaMapper personaMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.personaMapper = personaMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO registerUser(UsuarioDTO usuarioDTO) {
        // 1. Lógica de negocio: Validación de email existente.
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("El email " + usuarioDTO.getEmail() + " ya está en uso.");
        }

        // 2. Mapeo de DTO a Entidad usando el método correcto del mapper.
        Usuario usuario = personaMapper.dtoToUsuario(usuarioDTO);

        // 3. Lógica de negocio: Procesamiento de datos sensibles.
        // Se encripta la contraseña antes de guardarla.
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));

        // 4. Persistencia en la base de datos.
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // 5. Mapeo de Entidad a DTO para la respuesta.
        return personaMapper.usuarioToDTO(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findUserById(Long id) {
        return usuarioRepository.findById(id)
                .map(personaMapper::usuarioToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllUsers() {
        // Se utiliza el método del mapper que convierte la lista de entidades a DTOs.
        return personaMapper.usuariosToDTO(usuarioRepository.findAll());
    }

    @Override
    public UsuarioDTO updateUserProfile(Long id, UsuarioDTO usuarioDTO) {
        // Se busca el usuario existente o se lanza una excepción si no se encuentra.
        Usuario existingUser = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id)); // TODO: Reemplazar con excepción personalizada.

        // Se utiliza el método de actualización del mapper para aplicar los cambios del DTO a la entidad.
        personaMapper.updateUsuarioFromDTO(existingUser, usuarioDTO);

        // Si se proporciona una nueva contraseña en el DTO, se encripta y actualiza.
        if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isEmpty()) {
            existingUser.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        }

        // Se guarda la entidad actualizada.
        Usuario updatedUsuario = usuarioRepository.save(existingUser);

        // Se retorna el DTO con los datos actualizados.
        return personaMapper.usuarioToDTO(updatedUsuario);
    }

    @Override
    public void deleteUser(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id); // TODO: Reemplazar con excepción personalizada.
        }
        usuarioRepository.deleteById(id);
    }
}
