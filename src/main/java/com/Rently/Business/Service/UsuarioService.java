package com.Rently.Business.Service;

import com.Rently.Business.DTO.UsuarioDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz para la capa de servicio que gestiona los Usuarios.
 * Define las operaciones de negocio que se pueden realizar con los datos de un Usuario,
 * abstrayendo la lógica de negocio y persistencia subyacente.
 */
public interface UsuarioService {

    /**
     * Registra un nuevo usuario en el sistema.
     * Implica validación (ej. verificar si el email ya existe), mapeo de datos,
     * y la persistencia del nuevo usuario.
     *
     * @param usuarioDTO El DTO con los datos del nuevo usuario.
     * @return El DTO del usuario creado.
     */
    UsuarioDTO registerUser(UsuarioDTO usuarioDTO);

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el DTO del usuario si se encuentra, o vacío si no.
     */
    Optional<UsuarioDTO> findUserById(Long id);

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Una lista con los DTO de todos los usuarios.
     */
    List<UsuarioDTO> findAllUsers();

    /**
     * Actualiza el perfil de un usuario existente.
     *
     * @param id El ID del usuario a actualizar.
     * @param usuarioDTO El DTO con la información actualizada.
     * @return El DTO del usuario actualizado.
     */
    UsuarioDTO updateUserProfile(Long id, UsuarioDTO usuarioDTO);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    void deleteUser(Long id);
}
