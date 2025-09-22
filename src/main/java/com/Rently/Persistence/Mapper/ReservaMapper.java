package com.Rently.Persistence.Mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.ReservaDTO;
import com.Rently.Persistence.Entity.EstadoReserva;
import com.Rently.Persistence.Entity.Reserva;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ReservaMapper {

    //  ENTITY → DTO 
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ReservaDTO toDTO(Reserva reserva);

    List<ReservaDTO> toDTOList(List<Reserva> reservas);

    //  DTO → ENTITY (CREACIÓN) 
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Se asigna en el servicio
    @Mapping(target = "alojamiento", ignore = true) // Se asigna en el servicio
    @Mapping(target = "estado", expression = "java(EstadoReserva.PENDIENTE)") // Estado inicial
    Reserva toEntity(ReservaDTO dto);

    //  DTO → ENTITY (ACTUALIZACIÓN) 
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    void updateFromDTO(@MappingTarget Reserva reserva, ReservaDTO dto);

    //  MÉTODOS AUXILIARES
    default void cambiarEstado(Reserva reserva, EstadoReserva nuevoEstado) {
        if (reserva != null) {
            reserva.setEstado(nuevoEstado);
        }
    }
}
