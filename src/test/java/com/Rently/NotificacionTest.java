package com.Rently;

import com.Rently.Business.DTO.*;
import com.Rently.Persistence.DAO.*;
import com.Rently.Persistence.Entity.*;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Mapper.ReservaMapper;
import com.Rently.Persistence.Repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class NotificacionTest {


    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private ReservaDAO reservaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private NotificacionDAO notificacionDAO;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    @Autowired
    private ReservaMapper reservaMapper;


    @Test
    void testNotificacionFullCycle() {
        // Limpiar tablas
        alojamientoRepository.deleteAll();
        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();
        usuarioRepository.deleteAll();
        reservaRepository.deleteAll();

        // Crear anfitrión primero (es obligatorio para el alojamiento)
        Anfitrion anfitrion = new Anfitrion(
                null,
                "Carlos Ramírez",
                "carlos@gmail.com",
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

        AlojamientoDTO alojamientoSaved = alojamientoDAO.crearAlojamiento(alojamientoDTO);

        //Crear usuario primero (es necesario para crear la reserva)
        Usuario usuario = new Usuario(
                null,
                "Maria Ramírez",
                "Maria@gmail.com",
                "1234",
                "clave123",
                LocalDate.of(1990, 5, 10),
                "foto.jpg"
        );

        UsuarioDTO usuarioDTO = personaMapper.usuarioToDTO(usuario);
        UsuarioDTO usuarioSaved = usuarioDAO.crearUsuario(usuarioDTO);
        Assertions.assertNotNull(usuarioSaved.getId());

        //Crear reserva

        ReservaDTO reservaDTO = new ReservaDTO(null,
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2025, 5, 13),
                5,
                usuarioSaved.getId(),
                alojamientoSaved.getId());

        // Guardar reserva vía DAO
        ReservaDTO reservaSaved = reservaDAO.crear(reservaDTO);


        NotificacionDTO dto = new NotificacionDTO();
        dto.setUsuarioId(usuarioSaved.getId());
        dto.setReservaId(reservaSaved.getId());
        dto.setMensaje("Tu reserva fue confirmada");

        NotificacionDTO saved = notificacionDAO.crear(dto);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Tu reserva fue confirmada", saved.getMensaje());
        Assertions.assertFalse(saved.isLeida());

        // 4️⃣ Marcar notificación como leída
        notificacionDAO.marcarComoLeida(saved.getId());

        NotificacionDTO updated = notificacionDAO.buscarPorId(saved.getId()).get();
        Assertions.assertTrue(updated.isLeida());

        // 5️⃣ Consultar notificaciones no leídas de usuario
        List<NotificacionDTO> noLeidas = notificacionDAO.buscarNoLeidasPorUsuario(usuarioSaved.getId());
        Assertions.assertTrue(noLeidas.isEmpty()); // ya se marcó como leída
    }
}
