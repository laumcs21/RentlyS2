package com.Rently.Persistence.Mapper;

import com.Rently.Business.DTO.ServicioDTO;
import com.Rently.Persistence.Entity.Servicio;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ServicioMapper {

    // ENTITY → DTO
    ServicioDTO toDTO(Servicio servicio);
    List<ServicioDTO> toDTOList(List<Servicio> servicios);

    // DTO → ENTITY (CREACIÓN)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true) // se maneja en AlojamientoDAO si aplica
    Servicio toEntity(ServicioDTO dto);

    // DTO → ENTITY (ACTUALIZACIÓN)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    void updateFromDTO(@MappingTarget Servicio servicio, ServicioDTO dto);
}

