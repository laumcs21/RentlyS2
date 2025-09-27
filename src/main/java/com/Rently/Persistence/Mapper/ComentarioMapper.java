package com.Rently.Persistence.Mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.ComentarioDTO;
import com.Rently.Persistence.Entity.Comentario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ComentarioMapper {

    //  ENTITY → DTO 
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ComentarioDTO toDTO(Comentario comentario);

    List<ComentarioDTO> toDTOList(List<Comentario> comentarios);

    //  DTO → ENTITY (CREACIÓN) 
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Se asigna en el servicio
    @Mapping(target = "alojamiento", ignore = true) // Se asigna en el servicio
    @Mapping(target = "respuesta", ignore = true) // Solo anfitriones pueden responder
    @Mapping(target = "fecha", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "calificacion", source = "calificacion", qualifiedByName = "validarCalificacion")
    Comentario toEntity(ComentarioDTO dto);

    //  DTO → ENTITY (ACTUALIZACIÓN) 
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "respuesta", ignore = true) // Solo el anfitrión puede responder
    @Mapping(target = "calificacion", source = "calificacion", qualifiedByName = "validarCalificacion")
    void updateFromDTO(@MappingTarget Comentario comentario, ComentarioDTO dto);

    // MÉTODOS AUXILIARES 
   
        // Método para que el anfitrión responda un comentario
 
    default void responderComentario(Comentario comentario, String respuesta) {
        if (comentario != null && respuesta != null && !respuesta.trim().isEmpty()) {
            comentario.setRespuesta(respuesta.trim());
        }
    }
     
    @Named("validarCalificacion")
    default Integer validarCalificacion(Integer calificacion) {
        if (calificacion == null) return null;
        if (calificacion < 1) return 1;
        if (calificacion > 5) return 5;
        return calificacion;
    }
}
