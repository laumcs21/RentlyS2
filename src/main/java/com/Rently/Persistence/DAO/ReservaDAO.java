package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.ReservaDTO;
import com.Rently.Persistence.Entity.Reserva;
import com.Rently.Persistence.Entity.EstadoReserva;
import com.Rently.Persistence.Mapper.ReservaMapper;
import com.Rently.Persistence.Repository.ReservaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ReservaDAO {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaDAO(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    public ReservaDTO crear(ReservaDTO dto) {
        Reserva reserva = reservaMapper.toEntity(dto);
        Reserva guardada = reservaRepository.save(reserva);
        return reservaMapper.toDTO(guardada);
    }

    public Optional<ReservaDTO> obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .map(reservaMapper::toDTO);
    }

    public List<ReservaDTO> listarTodas() {
        return reservaMapper.toDTOList(reservaRepository.findAll());
    }

    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        return reservaMapper.toDTOList(reservaRepository.findByUsuarioIdOrderByFechaInicioDesc(usuarioId));
    }

    public List<ReservaDTO> listarPorAlojamiento(Long alojamientoId) {
        return reservaMapper.toDTOList(reservaRepository.findByAlojamientoId(alojamientoId));
    }

    public Optional<ReservaDTO> actualizar(Long id, ReservaDTO dto) {
        return reservaRepository.findById(id).map(reserva -> {
            reservaMapper.updateFromDTO(reserva, dto);
            Reserva actualizada = reservaRepository.save(reserva);
            return reservaMapper.toDTO(actualizada);
        });
    }

    public boolean cambiarEstado(Long id, EstadoReserva nuevoEstado) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEstado(nuevoEstado);
            reservaRepository.save(reserva);
            return true;
        }).orElse(false);
    }

    public boolean eliminar(Long id) {
        return reservaRepository.findById(id).map(reserva -> {
            reservaRepository.delete(reserva);
            return true;
        }).orElse(false);
    }
}

