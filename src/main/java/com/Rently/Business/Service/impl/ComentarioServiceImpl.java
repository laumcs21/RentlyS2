package com.Rently.Business.Service.impl;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.ComentarioDTO;
import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Business.Service.ComentarioService;
import com.Rently.Business.Service.UsuarioService;
import com.Rently.Configuration.Security.JwtService;
import com.Rently.Persistence.DAO.AlojamientoDAO;
import com.Rently.Persistence.DAO.AnfitrionDAO;
import com.Rently.Persistence.DAO.ComentarioDAO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.Comentario;
import com.Rently.Persistence.Entity.Rol;
import com.Rently.Persistence.Mapper.AlojamientoMapper;
import com.Rently.Persistence.Repository.ComentarioRepository;
import com.Rently.Persistence.Repository.AnfitrionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de comentarios.
 * Aplica validaciones de negocio antes de delegar en el DAO.
 */
@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    @Autowired
    private AnfitrionDAO anfitrionDAO;

    @Autowired
    private AlojamientoDAO alojamientoDAO;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlojamientoMapper alojamientoMapper;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private AnfitrionRepository anfitrionRepository;

    private final JwtService jwtService;

    @Autowired
    public ComentarioServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    /**
     * Crea un nuevo comentario.
     *
     * @param comentarioDTO el comentario a crear
     * @return el comentario creado
     * @throws IllegalArgumentException si faltan datos obligatorios
     */
    @Override
    public ComentarioDTO create(ComentarioDTO comentarioDTO) {
        if (comentarioDTO == null) {
            throw new IllegalArgumentException("El comentario no puede ser nulo.");
        }
        if (comentarioDTO.getAlojamientoId() == null || comentarioDTO.getAlojamientoId() <= 0) {
            throw new IllegalArgumentException("Debe especificarse un ID de alojamiento válido.");
        }
        if (comentarioDTO.getUsuarioId() == null || comentarioDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("Debe especificarse un ID de usuario válido.");
        }
        if (comentarioDTO.getComentario() == null || comentarioDTO.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto del comentario no puede estar vacío.");
        }
        if (comentarioDTO.getCalificacion() == null || comentarioDTO.getCalificacion() < 1 || comentarioDTO.getCalificacion() > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        return comentarioDAO.crearComentario(comentarioDTO);
    }

    /**
     * Busca comentarios por el ID del alojamiento.
     *
     * @param alojamientoId el ID del alojamiento
     * @return una lista de comentarios del alojamiento especificado
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public List<ComentarioDTO> findByAlojamientoId(Long alojamientoId) {
        if (alojamientoId == null || alojamientoId <= 0) {
            throw new IllegalArgumentException("El ID del alojamiento debe ser válido.");
        }

        List<ComentarioDTO> comentarios = comentarioDAO.obtenerComentariosPorAlojamiento(alojamientoId);
        if (comentarios == null || comentarios.isEmpty()) {
            throw new IllegalStateException("No se encontraron comentarios para el alojamiento especificado.");
        }

        return comentarios;
    }

    /**
     * Actualiza un comentario existente.
     *
     * @param id            el ID del comentario
     * @param comentarioDTO el comentario con la información actualizada
     * @return el comentario actualizado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    @Override
    public ComentarioDTO update(Long id, ComentarioDTO comentarioDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del comentario debe ser válido.");
        }
        if (comentarioDTO == null) {
            throw new IllegalArgumentException("El comentario actualizado no puede ser nulo.");
        }
        if (comentarioDTO.getComentario() != null && comentarioDTO.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El texto del comentario no puede estar vacío.");
        }
        if (comentarioDTO.getCalificacion() != null &&
                (comentarioDTO.getCalificacion() < 1 || comentarioDTO.getCalificacion() > 5)) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

        return comentarioDAO.actualizarComentario(id, comentarioDTO);
    }

    /**
     * Añade una respuesta al comentario (solo permitido al anfitrión).
     *
     * @param comentarioId el ID del comentario
     * @param respuesta    la respuesta a añadir
     * @return el comentario actualizado con la respuesta
     * @throws IllegalArgumentException si el ID o la respuesta son inválidos
     */
    @Override
    public ComentarioDTO addResponse(Long comentarioId, String respuesta, String token) {
        if (comentarioId == null || comentarioId <= 0) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido de comentario.");
        }

        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta no puede estar vacía.");
        }

        // 1️⃣ Extraer el email del anfitrión autenticado desde el JWT
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));

        // 2️⃣ Verificar que sea un anfitrión válido
        Anfitrion anfitrionAutenticado = anfitrionRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("El usuario autenticado no es un anfitrión válido."));

        // 3️⃣ Buscar el comentario
        ComentarioDTO comentarioDTO = comentarioDAO.buscarPorId(comentarioId)
                .orElseThrow(() -> new IllegalStateException("Comentario no encontrado."));

        // 4️⃣ Validar que el comentario aún no tenga respuesta
        if (comentarioDTO.getRespuesta() != null && !comentarioDTO.getRespuesta().isEmpty()) {
            throw new IllegalStateException("No es posible responder un comentario que ya tiene respuesta.");
        }

        // 5️⃣ Verificar que el comentario pertenece a un alojamiento con anfitrión
        AlojamientoDTO alojamiento = alojamientoDAO.buscarPorId(comentarioDTO.getAlojamientoId())
                .orElseThrow(() -> new IllegalStateException("El comentario no está asociado a un alojamiento válido."));

        Long anfitrionId = alojamiento.getAnfitrionId();
        if (anfitrionId == null) {
            throw new IllegalStateException("El alojamiento no tiene anfitrión asociado.");
        }

        // 6️⃣ Validar que el anfitrión autenticado sea el dueño del alojamiento
        if (!anfitrionId.equals(anfitrionAutenticado.getId())) {
            throw new SecurityException("Solo el anfitrión del alojamiento puede responder comentarios.");
        }

        // 7️⃣ Guardar la respuesta
        ComentarioDTO comentarioActualizado = comentarioDAO.responderComentario(comentarioId, respuesta);

        return comentarioActualizado;
    }




    /**
     * Elimina un comentario por su ID.
     *
     * @param id el ID del comentario
     * @throws IllegalArgumentException si el ID es inválido
     */
    @Override
    public void delete(Long id, String token) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido para eliminar el comentario.");
        }

        // Buscar el comentario
        ComentarioDTO comentario = comentarioDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalStateException("Comentario no encontrado."));

        // Obtener el email del usuario autenticado desde el token JWT
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));

        // Buscar el usuario autenticado
        UsuarioDTO usuarioAutenticado = usuarioService.findUserByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado."));

        Long usuarioId = usuarioAutenticado.getId();
        Long idUsuarioComentario = comentario.getUsuarioId();

        // Verificar si el usuario autenticado es el dueño del comentario o un administrador
        boolean esAdmin = usuarioAutenticado.getRol().equals(Rol.ADMINISTRADOR);

        if (!usuarioId.equals(idUsuarioComentario) && !esAdmin) {
            throw new SecurityException("No tiene permisos para eliminar este comentario.");
        }

        // Eliminar el comentario
        comentarioDAO.eliminarComentario(id);
    }

}


