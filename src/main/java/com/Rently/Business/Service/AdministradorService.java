package com.Rently.Business.Service;

import com.Rently.Business.DTO.AdministradorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de administradores.
 */
public interface AdministradorService {
    /**
     * Crea un nuevo administrador.
     *
     * @param administradorDTO el administrador a crear
     * @return el administrador creado
     */
    AdministradorDTO create(AdministradorDTO administradorDTO);

    /**
     * Busca un administrador por su ID.
     *
     * @param id el ID del administrador
     * @return un optional que contiene el administrador si se encuentra, o vacío en caso contrario
     */
    Optional<AdministradorDTO> findById(Long id);

    /**
     * Busca un administrador por su email.
     *
     * @param email el email del administrador
     * @return un optional que contiene el administrador si se encuentra, o vacío en caso contrario
     */
    Optional<AdministradorDTO> findByEmail(String email);

    /**
     * Devuelve una lista de todos los administradores.
     *
     * @return una lista de todos los administradores
     */
    List<AdministradorDTO> findAll();

    /**
     * Actualiza un administrador existente.
     *
     * @param id               el ID del administrador
     * @param administradorDTO el administrador con la información actualizada
     * @return un optional que contiene el administrador actualizado si se encuentra, o vacío en caso contrario
     */
    Optional<AdministradorDTO> update(Long id, AdministradorDTO administradorDTO);

    /**
     * Elimina un administrador por su ID.
     *
     * @param id el ID del administrador
     * @return true si el administrador fue eliminado, false en caso contrario
     */
    boolean delete(Long id);
}
