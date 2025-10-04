package com.Rently.Business.Unit;

import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Business.Service.impl.UsuarioServiceImpl;
import com.Rently.Persistence.Entity.Rol;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - Unit Tests con Mockito")
class UsuarioServiceImplUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PersonaMapper personaMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioDTO validUsuarioDTO;
    private Usuario validUsuario;

    @BeforeEach
    void setUp() {
        validUsuarioDTO = new UsuarioDTO(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "3001234567",
                "Password123",
                LocalDate.of(1990, 1, 1),
                Rol.USUARIO,
                "perfil.jpg"
        );

        validUsuario = new Usuario();
        validUsuario.setId(1L);
        validUsuario.setNombre("Juan Pérez");
        validUsuario.setEmail("juan@example.com");
        validUsuario.setContrasena("encodedPass");
        validUsuario.setTelefono("3001234567");
        validUsuario.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        validUsuario.setRol(Rol.USUARIO);
    }

    // ==================== CREATE ====================

    @Test
    @DisplayName("CREATE - Usuario válido debe registrarse exitosamente")
    void registerUser_ValidData_ShouldReturnUsuarioDTO() {
        when(usuarioRepository.findByEmail(validUsuarioDTO.getEmail())).thenReturn(Optional.empty());
        when(personaMapper.dtoToUsuario(validUsuarioDTO)).thenReturn(validUsuario);
        when(passwordEncoder.encode(validUsuarioDTO.getContrasena())).thenReturn("encodedPass");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(validUsuario);
        when(personaMapper.usuarioToDTO(validUsuario)).thenReturn(validUsuarioDTO);

        UsuarioDTO result = usuarioService.registerUser(validUsuarioDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("juan@example.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("CREATE - Email duplicado debe lanzar IllegalStateException")
    void registerUser_DuplicateEmail_ShouldThrowException() {
        when(usuarioRepository.findByEmail(validUsuarioDTO.getEmail())).thenReturn(Optional.of(validUsuario));

        assertThatThrownBy(() -> usuarioService.registerUser(validUsuarioDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ya está en uso");
    }

    @Test
    @DisplayName("CREATE - Email inválido debe lanzar IllegalArgumentException")
    void registerUser_InvalidEmail_ShouldThrowException() {
        validUsuarioDTO.setEmail("correo-invalido");

        assertThatThrownBy(() -> usuarioService.registerUser(validUsuarioDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("formato del email no es válido");
    }

    @Test
    @DisplayName("CREATE - Contraseña débil debe lanzar IllegalArgumentException")
    void registerUser_WeakPassword_ShouldThrowException() {
        validUsuarioDTO.setContrasena("123");

        assertThatThrownBy(() -> usuarioService.registerUser(validUsuarioDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("contraseña debe tener al menos 8 caracteres");
    }

    @Test
    @DisplayName("CREATE - Teléfono inválido debe lanzar IllegalArgumentException")
    void registerUser_InvalidPhone_ShouldThrowException() {
        validUsuarioDTO.setTelefono("12AB");

        assertThatThrownBy(() -> usuarioService.registerUser(validUsuarioDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teléfono es obligatorio y debe contener");
    }

    // ==================== READ ====================

    @Test
    @DisplayName("READ - Usuario existente debe retornarse")
    void findUserById_ExistingUser_ShouldReturnDTO() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(validUsuario));
        when(personaMapper.usuarioToDTO(validUsuario)).thenReturn(validUsuarioDTO);

        Optional<UsuarioDTO> result = usuarioService.findUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    @DisplayName("READ - Todos los usuarios deben retornarse")
    void findAllUsers_ShouldReturnList() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(validUsuario));
        when(personaMapper.usuariosToDTO(anyList())).thenReturn(Arrays.asList(validUsuarioDTO));

        List<UsuarioDTO> result = usuarioService.findAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("juan@example.com");
    }

    // ==================== UPDATE ====================

    @Test
    @DisplayName("UPDATE - Usuario existente debe actualizarse")
    void updateUserProfile_ValidData_ShouldReturnUpdatedDTO() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(validUsuario));
        doAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            UsuarioDTO dto = invocation.getArgument(1);
            usuario.setNombre(dto.getNombre());
            return null;
        }).when(personaMapper).updateUsuarioFromDTO(any(Usuario.class), any(UsuarioDTO.class));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(validUsuario);
        when(personaMapper.usuarioToDTO(validUsuario)).thenReturn(validUsuarioDTO);

        validUsuarioDTO.setNombre("Nuevo Nombre");
        UsuarioDTO result = usuarioService.updateUserProfile(1L, validUsuarioDTO);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Nuevo Nombre");
    }

    @Test
    @DisplayName("UPDATE - Usuario inexistente debe lanzar RuntimeException")
    void updateUserProfile_NonExistent_ShouldThrowException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.updateUserProfile(99L, validUsuarioDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    // ==================== DELETE ====================

    @Test
    @DisplayName("DELETE - Usuario existente debe eliminarse")
    void deleteUser_ExistingId_ShouldDelete() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> usuarioService.deleteUser(1L)).doesNotThrowAnyException();

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE - Usuario inexistente debe lanzar RuntimeException")
    void deleteUser_NonExistentId_ShouldThrowException() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.deleteUser(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }
}


