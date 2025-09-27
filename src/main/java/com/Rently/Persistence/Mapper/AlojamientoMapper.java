package com.Rently.Persistence.Mapper;

import com.Rently.Business.DTO.AlojamientoDTO;
import com.Rently.Persistence.Entity.Alojamiento;
import com.Rently.Persistence.Entity.AlojamientoImagen;
import com.Rently.Persistence.Entity.Servicio;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ComentarioMapper.class}
)
public interface AlojamientoMapper {

    // ================= TO DTO =================
    @Mapping(target = "anfitrionId", source = "anfitrion.id")
    @Mapping(target = "imagenes", source = "imagenes", qualifiedByName = "imagenesEntityToUrls")
    @Mapping(target = "serviciosId", source = "servicios", qualifiedByName = "serviciosEntityToIds")
    AlojamientoDTO toDTO(Alojamiento alojamiento);

    List<AlojamientoDTO> toDTOList(List<Alojamiento> alojamientos);

    // ================= TO ENTITY =================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true) // se setea en el DAO
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "servicios", ignore = true) // se setea en el DAO usando los IDs
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Alojamiento toEntity(AlojamientoDTO dto);

    // ================= UPDATE =================
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "servicios", ignore = true) // se setea en el DAO usando los IDs
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDTO(@MappingTarget Alojamiento alojamiento, AlojamientoDTO dto);

    // ================= MÉTODOS AUXILIARES =================
    @Named("imagenesEntityToUrls")
    default List<String> imagenesEntityToUrls(List<AlojamientoImagen> imagenes) {
        return (imagenes == null) ? List.of() :
                imagenes.stream()
                        .sorted((img1, img2) -> Integer.compare(img1.getOrden(), img2.getOrden()))
                        .map(AlojamientoImagen::getUrl)
                        .toList();
    }

    @Named("serviciosEntityToIds")
    default List<Long> serviciosEntityToIds(List<Servicio> servicios) {
        return (servicios == null) ? List.of() :
                servicios.stream()
                        .map(Servicio::getId)
                        .toList();
    }
}
