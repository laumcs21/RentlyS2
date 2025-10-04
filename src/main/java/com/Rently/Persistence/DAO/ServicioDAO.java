package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.ServicioDTO;
import com.Rently.Persistence.Entity.Servicio;
import com.Rently.Persistence.Mapper.ServicioMapper;
import com.Rently.Persistence.Repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioDAO {

    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    public ServicioDAO(ServicioRepository servicioRepository, ServicioMapper servicioMapper) {
        this.servicioRepository = servicioRepository;
        this.servicioMapper = servicioMapper;
    }

    // Crear servicio
    public ServicioDTO crearServicio(ServicioDTO dto) {
        Servicio servicio = servicioMapper.toEntity(dto);
        Servicio saved = servicioRepository.save(servicio);
        return servicioMapper.toDTO(saved);
    }

    // Obtener todos
    public List<ServicioDTO> obtenerServicios() {
        return servicioRepository.findAll()
                .stream()
                .map(servicioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public ServicioDTO obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id)
                .map(servicioMapper::toDTO)
                .orElse(null);
    }

    // Actualizar
    public ServicioDTO actualizarServicio(Long id, ServicioDTO dto) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicio.setNombre(dto.getNombre());
                    servicio.setDescripcion(dto.getDescripcion());
                    return servicioMapper.toDTO(servicioRepository.save(servicio));
                })
                .orElse(null);
    }

    // Eliminar
    public boolean eliminarServicio(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean estaAsociadoAAlgunAlojamiento(Long id) {
        return servicioRepository.existsInAlojamiento(id);
    }

}
