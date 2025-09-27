package com.Rently;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AlojamientoImagenDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import com.Rently.Persistence.DAO.AlojamientoImagenDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.TipoAlojamiento;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Mapper.PersonaMapper;
import com.Rently.Persistence.Repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlojamientoImagenIntegrationTest {

    @Autowired
    private AlojamientoImagenDAO imagenDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Test
    void flujoCompletoAlojamientoImagen() {

        // Limpiar dependencias en orden
        notificacionRepository.deleteAll();
        reservaRepository.deleteAll();
        alojamientoRepository.deleteAll();
        anfitrionRepository.deleteAll();
        personaRepository.deleteAll();

        // Crear anfitrión primero
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
        AlojamientoDTO alojamientoSaved = alojamientoDAO.crearAlojamiento(alojamientoDTO);

        // 🔑 Recuperar la entidad administrada desde el repositorio
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoSaved.getId())
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        // 2. Crear y guardar una imagen
        AlojamientoImagenDTO dto = new AlojamientoImagenDTO();
        dto.setUrl("http://img1.jpg");
        dto.setOrden(1);

        AlojamientoImagenDTO imagenSaved = imagenDAO.crearImagen(dto, alojamiento);

        assertNotNull(imagenSaved.getId());
        assertEquals("http://img1.jpg", imagenSaved.getUrl());
        assertEquals(1, imagenSaved.getOrden());

        // 3. Consultar imágenes del alojamiento
        List<AlojamientoImagenDTO> imagenes = imagenDAO.obtenerImagenesPorAlojamiento(alojamiento.getId());
        assertEquals(1, imagenes.size());
        assertEquals("http://img1.jpg", imagenes.get(0).getUrl());

        // 4. Actualizar imagen
        imagenSaved.setUrl("http://img1-updated.jpg");
        imagenSaved.setOrden(2);

        AlojamientoImagenDTO updated = imagenDAO.actualizarImagen(imagenSaved.getId(), imagenSaved);

        assertNotNull(updated);
        assertEquals("http://img1-updated.jpg", updated.getUrl());
        assertEquals(2, updated.getOrden());

        // 5. Eliminar imagen
        boolean eliminado = imagenDAO.eliminarImagen(imagenSaved.getId());
        assertTrue(eliminado);

        // 6. Verificar que ya no existe
        AlojamientoImagenDTO buscada = imagenDAO.obtenerImagenPorId(imagenSaved.getId());
        assertNull(buscada);
    }
}
