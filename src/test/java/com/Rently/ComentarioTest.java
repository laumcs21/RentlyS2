package com.Rently;

import com.Rently.Business.DTO.*;
import com.Rently.Persistence.DAO.*;
import com.Rently.Persistence.Entity.*;
import com.Rently.Persistence.Mapper.*;
import com.Rently.Persistence.Repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class ComentarioTest {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private ComentarioDAO comentarioDAO;

    @Autowired
    private PersonaMapper personaMapper;

    @Test
    void testComentarioFullCycle() {
        // Limpiar tabla
        comentarioRepository.deleteAll();
        alojamientoRepository.deleteAll();
        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();
        usuarioRepository.deleteAll();
        reservaRepository.deleteAll();

        // Crear anfitrión
        Anfitrion anfitrion = new Anfitrion(
                null,
                "Carlos Anfitrión",
                "anfitrion@test.com",
                "123456789",
                "clave123",
                LocalDate.of(1985, 1, 1),
                "Anfitrión top",
                "foto.jpg"
        );
        AnfitrionDTO anfitrionSaved = anfitrionDAO.crearAnfitrion(personaMapper.anfitrionToDTO(anfitrion));

        // Crear alojamiento
        AlojamientoDTO alojamientoDTO = new AlojamientoDTO();
        alojamientoDTO.setTitulo("Apartamento en Bogotá");
        alojamientoDTO.setDescripcion("Cómodo y bien ubicado");
        alojamientoDTO.setCiudad("Bogotá");
        alojamientoDTO.setDireccion("Cra 10 #20-30");
        alojamientoDTO.setLatitud(4.6097);
        alojamientoDTO.setLongitud(-74.0817);
        alojamientoDTO.setPrecioPorNoche(200000.0);
        alojamientoDTO.setCapacidadMaxima(4);
        alojamientoDTO.setTipoAlojamiento(TipoAlojamiento.APARTAMENTO);
        alojamientoDTO.setAnfitrionId(anfitrionSaved.getId());
        AlojamientoDTO alojamientoSaved = alojamientoDAO.crearAlojamiento(alojamientoDTO);

        // Crear usuario
        Usuario usuario = new Usuario(
                null,
                "Maria Usuario",
                "maria@test.com",
                "12345",
                "clave123",
                LocalDate.of(1995, 3, 15),
                "foto.jpg"
        );
        UsuarioDTO usuarioSaved = usuarioDAO.crearUsuario(personaMapper.usuarioToDTO(usuario));

        // Crear comentario DTO
        ComentarioDTO comentarioDTO = new ComentarioDTO(
                null,
                5,
                "Excelente alojamiento, volvería!",
                null,
                null,
                usuarioSaved.getId(),
                alojamientoSaved.getId()
        );

        ComentarioDTO responseDTO = comentarioDAO.crearComentario(comentarioDTO);

        Assertions.assertNotNull(responseDTO.getId());
        Assertions.assertEquals(5, responseDTO.getCalificacion());
        Assertions.assertEquals(usuarioSaved.getId(), responseDTO.getUsuarioId());
        Assertions.assertEquals(alojamientoSaved.getId(), responseDTO.getAlojamientoId());

        List<Comentario> comentarios = comentarioRepository.findByAlojamientoIdOrderByFechaDesc(alojamientoSaved.getId());
        Assertions.assertFalse(comentarios.isEmpty());

        ComentarioDTO updated = comentarioDAO.responderComentario(responseDTO.getId(),
                "Gracias por tu comentario, siempre bienvenida!");

        Assertions.assertEquals("Gracias por tu comentario, siempre bienvenida!", updated.getRespuesta());

    }
}

