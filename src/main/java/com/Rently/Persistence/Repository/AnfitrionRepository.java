package com.Rently.Persistence.Repository;

import com.Rently.Persistence.Entity.Anfitrion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface AnfitrionRepository extends JpaRepository<Anfitrion, Long> {
    Optional<Anfitrion> findByEmail(String email);
    List<Anfitrion> findByNombreContainingIgnoreCase(String nombre);
}

