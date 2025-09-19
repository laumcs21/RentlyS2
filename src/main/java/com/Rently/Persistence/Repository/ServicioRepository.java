package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombre(String nombre);
}

