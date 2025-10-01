package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.AdministradorDTO;
import com.Rently.Business.Service.AdministradorService;
import com.Rently.Persistence.DAO.AdministradorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de administradores.
 */
@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorDAO administradorDAO;

    /**
     * Crea un nuevo administrador.
     *
     * @param administradorDTO el administrador a crear
     * @return el administrador creado
     */
    @Override
    public AdministradorDTO create(AdministradorDTO administradorDTO) {
        return administradorDAO.crearAdministrador(administradorDTO);
    }

    /**
     * Busca un administrador por su ID.
     *
     * @param id el ID del administrador
     * @return un optional que contiene el administrador si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AdministradorDTO> findById(Long id) {
        return administradorDAO.buscarPorId(id);
    }

    /**
     * Busca un administrador por su email.
     *
     * @param email el email del administrador
     * @return un optional que contiene el administrador si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AdministradorDTO> findByEmail(String email) {
        return administradorDAO.buscarPorEmail(email);
    }

    /**
     * Devuelve una lista de todos los administradores.
     *
     * @return una lista de todos los administradores
     */
    @Override
    public List<AdministradorDTO> findAll() {
        return administradorDAO.listarTodos();
    }

    /**
     * Actualiza un administrador existente.
     *
     * @param id               el ID del administrador
     * @param administradorDTO el administrador con la información actualizada
     * @return un optional que contiene el administrador actualizado si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AdministradorDTO> update(Long id, AdministradorDTO administradorDTO) {
        return administradorDAO.actualizarAdministrador(id, administradorDTO);
    }

    /**
     * Elimina un administrador por su ID.
     *
     * @param id el ID del administrador
     * @return true si el administrador fue eliminado, false en caso contrario
     */
    @Override
    public boolean delete(Long id) {
        return administradorDAO.eliminarAdministrador(id);
    }
}
