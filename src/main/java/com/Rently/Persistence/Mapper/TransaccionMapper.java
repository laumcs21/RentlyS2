package com.Rently.Persistence.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.TransaccionDTO;
import com.Rently.Persistence.Entity.EstadoPago;
import com.Rently.Persistence.Entity.Transaccion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TransaccionMapper {
    
   
    // Transaccion Entity -> TransaccionDTO
    
    @Mapping(target = "reservaId", source = "reserva.id")
    TransaccionDTO toDTO(Transaccion transaccion);
    
  
    // TransaccionDTO -> Transaccion Entity (para crear)
   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true) // Se asigna en el servicio
    @Mapping(target = "estadoPago", expression = "java(com.Rently.Persistence.Entity.EstadoPago.PENDIENTE)") // Estado inicial
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Transaccion toEntity(TransaccionDTO dto);
    

    // Lista de Transaccion Entity -> Lista de TransaccionDTO
     
    List<TransaccionDTO> toDTOList(List<Transaccion> transacciones);
    
    //  MÉTODOS AUXILIARES 
    

    // Aprobar transacción

    default void aprobarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            transaccion.setEstadoPago(EstadoPago.APROBADO);
        }
    }

    // Rechazar transacción

    default void rechazarTransaccion(Transaccion transaccion) {
        if (transaccion != null) {
            transaccion.setEstadoPago(EstadoPago.RECHAZADO);
        }
    }
}
