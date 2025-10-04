package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombre(String nombre);
    @Query("SELECT COUNT(a) > 0 FROM Alojamiento a JOIN a.servicios s WHERE s.id = :servicioId")
    boolean existsInAlojamiento(@Param("servicioId") Long servicioId);
}

