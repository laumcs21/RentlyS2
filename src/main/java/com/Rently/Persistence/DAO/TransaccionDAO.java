package com.Rently.Persistence.DAO;

import com.Rently.Business.DTO.TransaccionDTO;
import com.Rently.Persistence.Entity.Transaccion;
import com.Rently.Persistence.Mapper.TransaccionMapper;
import com.Rently.Persistence.Repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionDAO {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;

    public TransaccionDAO(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
    }

    public TransaccionDTO crear(TransaccionDTO dto) {
        Transaccion transaccion = transaccionMapper.toEntity(dto);
        Transaccion guardada = transaccionRepository.save(transaccion);
        return transaccionMapper.toDTO(guardada);
    }

    public Optional<TransaccionDTO> buscarPorId(Long id) {
        return transaccionRepository.findById(id).map(transaccionMapper::toDTO);
    }

    public List<TransaccionDTO> listarTodas() {
        return transaccionMapper.toDTOList(transaccionRepository.findAll());
    }

    public List<TransaccionDTO> listarPorReserva(Long reservaId) {
        return transaccionMapper.toDTOList(transaccionRepository.findByReservaId(reservaId));
    }

    public void eliminar(Long id) {
        transaccionRepository.deleteById(id);
    }

    public Optional<TransaccionDTO> aprobar(Long id) {
        return transaccionRepository.findById(id).map(t -> {
            transaccionMapper.aprobarTransaccion(t);
            Transaccion actualizada = transaccionRepository.save(t);
            return transaccionMapper.toDTO(actualizada);
        });
    }

    public Optional<TransaccionDTO> rechazar(Long id) {
        return transaccionRepository.findById(id).map(t -> {
            transaccionMapper.rechazarTransaccion(t);
            Transaccion actualizada = transaccionRepository.save(t);
            return transaccionMapper.toDTO(actualizada);
        });
    }
}

