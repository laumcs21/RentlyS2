package com.Rently.Business.Mapper;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Business.DTO.AlojamientoDetalleDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.AlojamientoImagen;
import com.Rently.Persistence.Entity.Servicio;
import com.Rently.Persistence.Entity.TipoAlojamiento;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ComentarioMapper.class}
)
public interface AlojamientoMapper {

    @Mapping(target = "anfitrionId", source = "anfitrion.id")
    @Mapping(target = "tipoAlojamientoId", source = "tipoAlojamiento", qualifiedByName = "tipoAlojamientoToId")
    AlojamientoDTO toDTO(Alojamiento alojamiento);

    List<AlojamientoDTO> toDTOList(List<Alojamiento> alojamientos);


    @Mapping(target = "anfitrionId", source = "anfitrion.id")
    @Mapping(target = "tipoAlojamientoId", source = "tipoAlojamiento", qualifiedByName = "tipoAlojamientoToId")
    @Mapping(target = "imagenes", source = "imagenes", qualifiedByName = "imagenesEntityToUrls")
    @Mapping(target = "servicios", source = "servicios", qualifiedByName = "serviciosEntityToNames")
    @Mapping(target = "comentarios", source = "comentarios")
    AlojamientoDetalleDTO toDetalleDTO(Alojamiento alojamiento);

    List<AlojamientoDetalleDTO> toDetalleDTOList(List<Alojamiento> alojamientos);

    // ================= ENTITY CREATION =================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "descripcion", ignore = true)
    @Mapping(target = "latitud", ignore = true)
    @Mapping(target = "longitud", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "tipoAlojamiento", source = "tipoAlojamientoId", qualifiedByName = "idToTipoAlojamiento")
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Alojamiento toEntity(AlojamientoDTO dto);

    // ================= ENTITY UPDATE =================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "tipoAlojamiento", source = "tipoAlojamientoId", qualifiedByName = "idToTipoAlojamiento")
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDTO(@MappingTarget Alojamiento alojamiento, AlojamientoDTO dto);

    // MÉTODOS AUXILIARES
    @Named("tipoAlojamientoToId")
    default Long tipoAlojamientoToId(TipoAlojamiento tipoAlojamiento) {
        return (tipoAlojamiento != null) ? (long) tipoAlojamiento.ordinal() + 1 : null;
    }

    @Named("idToTipoAlojamiento")
    default TipoAlojamiento idToTipoAlojamiento(Long id) {
        if (id == null) return null;
        TipoAlojamiento[] valores = TipoAlojamiento.values();
        int index = (int) (id - 1);
        return (index >= 0 && index < valores.length) ? valores[index] : null;
    }

    @Named("imagenesEntityToUrls")
    default List<String> imagenesEntityToUrls(List<AlojamientoImagen> imagenes) {
        return (imagenes == null) ? List.of() :
            imagenes.stream()
                .sorted((img1, img2) -> Integer.compare(img1.getOrden(), img2.getOrden()))
                .map(AlojamientoImagen::getUrl)
                .collect(Collectors.toList());
    }

    @Named("serviciosEntityToNames")
    default List<String> serviciosEntityToNames(List<Servicio> servicios) {
        return (servicios == null) ? List.of() :
            servicios.stream()
                .map(Servicio::getNombre)
                .collect(Collectors.toList());
    }
}
