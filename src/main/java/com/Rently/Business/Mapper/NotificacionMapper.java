package com.Rently.Business.Mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.NotificacionDTO;
import com.Rently.Persistence.Entity.Notificacion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface NotificacionMapper {
    
    //  ENTITY → DTO 
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "reservaId", source = "reserva.id")
    @Mapping(target = "comentarioId", source = "comentario.id")
    NotificacionDTO toDTO(Notificacion notificacion);

    List<NotificacionDTO> toDTOList(List<Notificacion> notificaciones);
    
    //  DTO → ENTITY (CREACIÓN) 
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Se asigna en el servicio
    @Mapping(target = "reserva", ignore = true) // Se asigna en el servicio
    @Mapping(target = "comentario", ignore = true) // Se asigna en el servicio
    @Mapping(target = "leida", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Notificacion toEntity(NotificacionDTO dto);
    
    //  DTO → ENTITY (ACTUALIZACIÓN) 
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "comentario", ignore = true)
    @Mapping(target = "leida", ignore = true) // El estado se cambia con métodos auxiliares
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDTO(@MappingTarget Notificacion notificacion, NotificacionDTO dto);

    // MÉTODOS AUXILIARES
    
        // Marcar notificación como leída
     
    default void marcarComoLeida(Notificacion notificacion) {
        if (notificacion != null && !Boolean.TRUE.equals(notificacion.isLeida())) {
            notificacion.setLeida(true);
        }
    }

   
        // Marcar múltiples notificaciones como leídas
    
    default void marcarTodasComoLeidas(List<Notificacion> notificaciones) {
        if (notificaciones != null && !notificaciones.isEmpty()) {
            notificaciones.forEach(this::marcarComoLeida);
        }
    }
}
