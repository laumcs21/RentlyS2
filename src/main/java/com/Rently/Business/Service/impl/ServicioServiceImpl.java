package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.ServicioDTO;
import com.Rently.Business.Service.ServicioService;
import com.Rently.Persistence.DAO.ServicioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioDAO servicioDAO;

    @Override
    public ServicioDTO create(ServicioDTO servicioDTO) {
        validateServicio(servicioDTO);

        // Verificar duplicados por nombre
        List<ServicioDTO> existentes = servicioDAO.obtenerServicios();
        boolean existe = existentes.stream()
                .anyMatch(s -> s.getNombre().equalsIgnoreCase(servicioDTO.getNombre()));
        if (existe) {
            throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + servicioDTO.getNombre());
        }

        return servicioDAO.crearServicio(servicioDTO);
    }

    @Override
    public List<ServicioDTO> findAll() {
        return servicioDAO.obtenerServicios();
    }

    @Override
    public ServicioDTO findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de servicio inválido");
        }
        return servicioDAO.obtenerServicioPorId(id);
    }

    @Override
    public ServicioDTO update(Long id, ServicioDTO servicioDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        validateServicio(servicioDTO);

        // Validar duplicados en update (excepto el mismo servicio)
        List<ServicioDTO> existentes = servicioDAO.obtenerServicios();
        boolean existe = existentes.stream()
                .anyMatch(s -> !s.getId().equals(id) &&
                        s.getNombre().equalsIgnoreCase(servicioDTO.getNombre()));
        if (existe) {
            throw new IllegalArgumentException("Ya existe otro servicio con el nombre: " + servicioDTO.getNombre());
        }

        return servicioDAO.actualizarServicio(id, servicioDTO);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        // 🚨 Regla: evitar borrar si está asociado a alojamientos
        boolean enUso = servicioDAO.estaAsociadoAAlgunAlojamiento(id);
        if (enUso) {
            throw new IllegalStateException("No se puede eliminar el servicio, está asociado a alojamientos");
        }

        return servicioDAO.eliminarServicio(id);
    }

    // -------------------- Validaciones privadas --------------------
    private void validateServicio(ServicioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Servicio no puede ser nulo");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (dto.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres");
        }
        if (dto.getDescripcion() == null || dto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        if (dto.getDescripcion().length() > 500) {
            throw new IllegalArgumentException("La descripción no puede superar los 500 caracteres");
        }
    }
}

