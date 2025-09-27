package com.Rently.Persistence.Mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.ReservaDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.EstadoReserva;
import com.Rently.Persistence.Entity.Reserva;
import com.Rently.Persistence.Entity.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ReservaMapper {

    // ========== ENTITY → DTO ==========
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ReservaDTO toDTO(Reserva reserva);

    List<ReservaDTO> toDTOList(List<Reserva> reservas);

    // ========== DTO → ENTITY (CREACIÓN) ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", expression = "java(com.Rently.Persistence.Entity.EstadoReserva.PENDIENTE)")
    @Mapping(target = "usuario", source = "usuarioId")
    @Mapping(target = "alojamiento", source = "alojamientoId")
    Reserva toEntity(ReservaDTO dto);

    // ========== DTO → ENTITY (ACTUALIZACIÓN) ==========
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "usuarioId")
    @Mapping(target = "alojamiento", source = "alojamientoId")
    void updateFromDTO(@MappingTarget Reserva reserva, ReservaDTO dto);

    // ========== MÉTODOS AUXILIARES ==========
    default void cambiarEstado(Reserva reserva, EstadoReserva nuevoEstado) {
        if (reserva != null) {
            reserva.setEstado(nuevoEstado);
        }
    }

    // Mapear usuarioId → Usuario con solo el id
    default Usuario mapUsuario(Long usuarioId) {
        if (usuarioId == null) return null;
        Usuario u = new Usuario();
        u.setId(usuarioId);
        return u;
    }

    // Mapear alojamientoId → Alojamiento con solo el id
    default Alojamiento mapAlojamiento(Long alojamientoId) {
        if (alojamientoId == null) return null;
        Alojamiento a = new Alojamiento();
        a.setId(alojamientoId);
        return a;
    }
}

