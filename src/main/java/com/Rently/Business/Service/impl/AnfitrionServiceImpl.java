package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.Service.AnfitrionService;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de anfitriones.
 */
@Service
public class AnfitrionServiceImpl implements AnfitrionService {

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    /**
     * Crea un nuevo anfitrión.
     *
     * @param anfitrionDTO el anfitrión a crear
     * @return el anfitrión creado
     */
    @Override
    public AnfitrionDTO create(AnfitrionDTO anfitrionDTO) {
        return anfitrionDAO.crearAnfitrion(anfitrionDTO);
    }

    /**
     * Busca un anfitrión por su ID.
     *
     * @param id el ID del anfitrión
     * @return un optional que contiene el anfitrión si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AnfitrionDTO> findById(Long id) {
        return anfitrionDAO.buscarPorId(id);
    }

    /**
     * Busca un anfitrión por su email.
     *
     * @param email el email del anfitrión
     * @return un optional que contiene el anfitrión si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AnfitrionDTO> findByEmail(String email) {
        return anfitrionDAO.buscarPorEmail(email);
    }

    /**
     * Busca anfitriones por su nombre.
     *
     * @param name el nombre a buscar
     * @return una lista de anfitriones que coinciden con el nombre especificado
     */
    @Override
    public List<AnfitrionDTO> findByName(String name) {
        return anfitrionDAO.buscarPorNombre(name);
    }

    /**
     * Devuelve una lista de todos los anfitriones.
     *
     * @return una lista de todos los anfitriones
     */
    @Override
    public List<AnfitrionDTO> findAll() {
        return anfitrionDAO.listarTodos();
    }

    /**
     * Actualiza un anfitrión existente.
     *
     * @param id           el ID del anfitrión
     * @param anfitrionDTO el anfitrión con la información actualizada
     * @return un optional que contiene el anfitrión actualizado si se encuentra, o vacío en caso contrario
     */
    @Override
    public Optional<AnfitrionDTO> update(Long id, AnfitrionDTO anfitrionDTO) {
        return anfitrionDAO.actualizarAnfitrion(id, anfitrionDTO);
    }

    /**
     * Elimina un anfitrión por su ID.
     *
     * @param id el ID del anfitrión
     * @return true si el anfitrión fue eliminado, false en caso contrario
     */
    @Override
    public boolean delete(Long id) {
        return anfitrionDAO.eliminarAnfitrion(id);
    }
}
