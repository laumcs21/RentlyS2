package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByAlojamientoIdOrderByFechaDesc(Long alojamientoId);
}

