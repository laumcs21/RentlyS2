package com.Rently;

import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.DTO.AdministradorDTO;
import com.Rently.Persistence.DAO.UsuarioDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.DAO.AdministradorDAO;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.Administrador;
import com.Rently.Persistence.Mapper.PersonaMapper;

import com.Rently.Persistence.Repository.AdministradorRepository;
import com.Rently.Persistence.Repository.AnfitrionRepository;
import com.Rently.Persistence.Repository.PersonaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class PersonaIntegrationTest {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private AnfitrionDAO anfitrionDAO;
    @Autowired
    private AdministradorDAO administradorDAO;
    @Autowired
    private AnfitrionRepository anfitrionRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    @Test
    void testUsuarioFullCycle() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Laura");
        dto.setEmail("laura@test.com");
        dto.setContrasena("clave1234");
        dto.setTelefono("123456");
        dto.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        // Guardar
        UsuarioDTO saved = usuarioDAO.crearUsuario(dto);

        Assertions.assertNotNull(saved.getId());

        // Recuperar
        UsuarioDTO loaded = usuarioDAO.buscarPorId(saved.getId()).orElse(null);
        Assertions.assertNotNull(loaded);

        // Comparar datos
        Assertions.assertEquals(dto.getNombre(), loaded.getNombre());
        Assertions.assertEquals(dto.getEmail(), loaded.getEmail());
    }



    @Test
    void testAnfitrionFullCycle() {

        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();

        // Crear entidad Anfitrion con email único
        String emailUnico = "cesarc.tulcane+" + System.currentTimeMillis() + "@uqvirtual.edu.co";

        Anfitrion entity = new Anfitrion(
                null,
                "Cesar Túlcan",
                emailUnico,
                "123456789",
                "secreta",
                LocalDate.of(1995, 5, 15),
                "Anfitriona con experiencia",
                "foto.jpg"
        );

        // Guardar usando directamente el DAO (evitamos doble save)
        AnfitrionDTO dtoSaved = anfitrionDAO.crearAnfitrion(personaMapper.anfitrionToDTO(entity));
        Assertions.assertNotNull(dtoSaved.getId());

        // Buscar por ID con el DAO
        AnfitrionDTO dtoFound = anfitrionDAO.buscarPorId(dtoSaved.getId()).orElse(null);
        Assertions.assertNotNull(dtoFound);

        // Validar datos
        Assertions.assertEquals(entity.getNombre(), dtoFound.getNombre());
        Assertions.assertEquals(entity.getEmail(), dtoFound.getEmail());
    }


    @Test
    void testAdministradorFullCycle() {

        administradorRepository.deleteAll();
        personaRepository.deleteAll();


        Administrador entity = new Administrador(
                null,
                "Laura Cárdenas",
                "lauram.cardenass@uqvirtual.edu.co",
                "987654321",
                "claveSegura",
                LocalDate.of(1990, 8, 20),
                "foto.jpg"
        );

        // Guardar usando directamente el DAO (evitamos doble save)
        AdministradorDTO dtoSaved = administradorDAO.crearAdministrador(personaMapper.adminToDTO(entity));
        Assertions.assertNotNull(dtoSaved.getId());

        // Buscar por ID con el DAO
        AdministradorDTO dtoFound = administradorDAO.buscarPorId(dtoSaved.getId()).orElse(null);
        Assertions.assertNotNull(dtoFound);

        // Validar datos
        Assertions.assertEquals(entity.getNombre(), dtoFound.getNombre());
        Assertions.assertEquals(entity.getEmail(), dtoFound.getEmail());
    }

}
