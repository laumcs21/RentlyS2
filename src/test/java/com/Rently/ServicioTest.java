package com.Rently;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.DTO.ServicioDTO;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.DAO.ServicioDAO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.TipoAlojamiento;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Mapper.ServicioMapper;
import com.Rently.Persistence.Repository.AlojamientoRepository;
import com.Rently.Persistence.Repository.AnfitrionRepository;
import com.Rently.Persistence.Repository.PersonaRepository;
import com.Rently.Persistence.Repository.ServicioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServicioTest {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ServicioDAO servicioDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private ServicioMapper servicioMapper;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    @Test
    void testServicioFullCycle() {

        // Limpiar tablas
        alojamientoRepository.deleteAll();
        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();
        servicioRepository.deleteAll();

        // 1️⃣ Crear servicios
        ServicioDTO wifi = new ServicioDTO();
        wifi.setNombre("Wifi");
        wifi.setDescripcion("Conexión rápida");

        ServicioDTO desayuno = new ServicioDTO();
        desayuno.setNombre("Desayuno");
        desayuno.setDescripcion("Incluido");

        ServicioDTO wifiSaved = servicioDAO.crearServicio(wifi);
        ServicioDTO desayunoSaved = servicioDAO.crearServicio(desayuno);

        // Crear anfitrión
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

        // 2️⃣ Crear alojamiento asociado al anfitrión
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

        // Asignar nombres de servicios al DTO
        List<Long> servicios = new ArrayList<>();
        servicios.add(wifiSaved.getId());
        servicios.add(desayunoSaved.getId());
        alojamientoDTO.setServiciosId(servicios);

        // Guardar alojamiento vía DAO
        AlojamientoDTO saved = alojamientoDAO.crearAlojamiento(alojamientoDTO);

        // 3️⃣ Verificar servicios asignados
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(2, saved.getServiciosId().size());
        Assertions.assertTrue(saved.getServiciosId().contains(wifiSaved.getId()));
        Assertions.assertTrue(saved.getServiciosId().contains(desayunoSaved.getId()));

        Alojamiento alojamiento = alojamientoRepository.findById(saved.getId()).get();
        System.out.println(alojamiento.getServicios()); // Esto sí funcionará

    }
}
