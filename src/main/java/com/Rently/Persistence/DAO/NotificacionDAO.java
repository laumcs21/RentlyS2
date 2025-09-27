package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.NotificacionDTO;
import com.Rently.Persistence.Entity.Notificacion;
import com.Rently.Persistence.Entity.Reserva;
import com.Rently.Persistence.Entity.Usuario;
import com.Rently.Persistence.Mapper.NotificacionMapper;
import com.Rently.Persistence.Repository.NotificacionRepository;
import com.Rently.Persistence.Repository.ReservaRepository;
import com.Rently.Persistence.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionDAO {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaRepository reservaRepository;
    private final NotificacionMapper notificacionMapper;

    public NotificacionDAO(NotificacionRepository notificacionRepository, UsuarioRepository usuarioRepository, ReservaRepository reservaRepository, NotificacionMapper notificacionMapper) {
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.reservaRepository = reservaRepository;
        this.notificacionMapper = notificacionMapper;
    }

    public NotificacionDTO crear(NotificacionDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Reserva reserva = reservaRepository.findById(dto.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setReserva(reserva);
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setLeida(false);

        notificacionRepository.save(notificacion);
        return notificacionMapper.toDTO(notificacion);
    }

    public Optional<NotificacionDTO> buscarPorId(Long id) {
        return notificacionRepository.findById(id).map(notificacionMapper::toDTO);
    }

    public List<NotificacionDTO> listarTodas() {
        return notificacionMapper.toDTOList(notificacionRepository.findAll());
    }

    public List<NotificacionDTO> listarNoLeidasPorUsuario(Long usuarioId) {
        return notificacionMapper.toDTOList(
                notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId)
        );
    }

    public Optional<NotificacionDTO> actualizar(Long id, NotificacionDTO dto) {
        return notificacionRepository.findById(id).map(n -> {
            notificacionMapper.updateFromDTO(n, dto);
            Notificacion actualizada = notificacionRepository.save(n);
            return notificacionMapper.toDTO(actualizada);
        });
    }

    public void eliminar(Long id) {
        notificacionRepository.deleteById(id);
    }

    public Optional<NotificacionDTO> marcarComoLeida(Long id) {
        return notificacionRepository.findById(id).map(n -> {
            notificacionMapper.marcarComoLeida(n);
            Notificacion actualizada = notificacionRepository.save(n);
            return notificacionMapper.toDTO(actualizada);
        });
    }

    public void marcarTodasComoLeidas(Long usuarioId) {
        List<Notificacion> pendientes = notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
        notificacionMapper.marcarTodasComoLeidas(pendientes);
        notificacionRepository.saveAll(pendientes);
    }

    public List<NotificacionDTO> buscarNoLeidasPorUsuario(Long usuarioId) {
        List<Notificacion> pendientes = notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
        return notificacionMapper.toDTOList(pendientes);
    }
}
