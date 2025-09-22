package com.Rently.Persistence.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.Rently.Business.DTO.AlojamientoImagenDTO;
import com.Rently.Persistence.Entity.AlojamientoImagen;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AlojamientoImagenMapper {
    

     // AlojamientoImagen Entity -> AlojamientoImagenDTO
     
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    AlojamientoImagenDTO toDTO(AlojamientoImagen imagen);
    
  
     // AlojamientoImagenDTO -> AlojamientoImagen Entity (para crear)
     
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamiento", ignore = true) // Se asigna en el servicio
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orden", expression = "java(validarOrden(dto.getOrden()))")
    AlojamientoImagen toEntity(AlojamientoImagenDTO dto);
    
    
    // Lista de AlojamientoImagen Entity -> Lista de AlojamientoImagenDTO

    List<AlojamientoImagenDTO> toDTOList(List<AlojamientoImagen> imagenes);
    

    // Actualizar AlojamientoImagen existente con datos del DTO
  
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orden", expression = "java(validarOrden(dto.getOrden()))")
    void updateFromDTO(@MappingTarget AlojamientoImagen imagen, AlojamientoImagenDTO dto);
    
    // MÉTODOS AUXILIARES 
    

     // Validar orden de imagen (debe ser positivo)
    
    default Integer validarOrden(Integer orden) {
        return (orden != null && orden > 0) ? orden : 1;
    }


    // Ordenar imágenes por orden ascendente

    default List<AlojamientoImagen> ordenarImagenes(List<AlojamientoImagen> imagenes) {
        if (imagenes == null) return null;
        return imagenes.stream()
            .sorted((img1, img2) -> Integer.compare(validarOrden(img1.getOrden()), validarOrden(img2.getOrden())))
            .collect(Collectors.toList()); // compatible con Java 8+
    }
}
