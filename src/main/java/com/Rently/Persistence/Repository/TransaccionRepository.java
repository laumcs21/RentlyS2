package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByReservaId(Long reservaId);
}

