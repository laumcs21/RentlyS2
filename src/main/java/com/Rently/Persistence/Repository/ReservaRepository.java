package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioIdOrderByFechaInicioDesc(Long usuarioId);
    List<Reserva> findByAlojamientoId(Long alojamientoId);
}

