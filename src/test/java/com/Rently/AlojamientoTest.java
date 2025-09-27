package com.Rently;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.TipoAlojamiento;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.AlojamientoRepository;
import com.Rently.Persistence.Repository.AnfitrionRepository;
import com.Rently.Persistence.Repository.PersonaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AlojamientoTest {

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    @Test
    void testAlojamientoFullCycle() {
        // Limpiar tablas
        alojamientoRepository.deleteAll();
        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();

        // Crear anfitrión primero (es obligatorio para el alojamiento)
        String emailUnico = "anfitrion+" + System.currentTimeMillis() + "@test.com";
        Anfitrion anfitrion = new Anfitrion(
                null,
                "Carlos Ramírez",
                emailUnico,
                "123456789",
                "clave123",
                LocalDate.of(1990, 5, 10),
                "Anfitrión verificado",
                "foto.jpg"
        );
        AnfitrionDTO anfitrionDTO = personaMapper.anfitrionToDTO(anfitrion);
        AnfitrionDTO anfitrionSaved = anfitrionDAO.crearAnfitrion(anfitrionDTO);
        Assertions.assertNotNull(anfitrionSaved.getId());

        // Crear alojamiento asociado al anfitrión
        AlojamientoDTO alojamientoDTO = new AlojamientoDTO();
        alojamientoDTO.setTitulo("Casa en la playa");
        alojamientoDTO.setDescripcion("Hermosa casa frente al mar");
        alojamientoDTO.setCiudad("Cartagena");
        alojamientoDTO.setDireccion("Calle 123 #45-67");
        alojamientoDTO.setLatitud(10.3910);
        alojamientoDTO.setLongitud(-75.4794);
        alojamientoDTO.setPrecioPorNoche(350000.0);
        alojamientoDTO.setCapacidadMaxima(6);
        alojamientoDTO.setTipoAlojamiento(TipoAlojamiento.CASA);
        alojamientoDTO.setAnfitrionId(anfitrionSaved.getId());

        // Guardar alojamiento vía DAO
        AlojamientoDTO saved = alojamientoDAO.crearAlojamiento(alojamientoDTO);
        Assertions.assertNotNull(saved.getId());

        // Buscar por ID
        AlojamientoDTO found = alojamientoDAO.buscarPorId(saved.getId()).orElse(null);
        Assertions.assertNotNull(found);

        // Validar datos
        Assertions.assertEquals(alojamientoDTO.getTitulo(), found.getTitulo());
        Assertions.assertEquals(alojamientoDTO.getCiudad(), found.getCiudad());
        Assertions.assertEquals(alojamientoDTO.getPrecioPorNoche(), found.getPrecioPorNoche());
        Assertions.assertEquals(anfitrionSaved.getId(), found.getAnfitrionId());

        Alojamiento entity = alojamientoMapper.toEntity(alojamientoDTO);
        Assertions.assertEquals(alojamientoDTO.getTitulo(), entity.getTitulo());
    }
}
