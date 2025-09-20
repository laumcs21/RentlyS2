package com.Rently.Business.Mapper;

import java.util.List;




import com.Rently.Business.DTO.AdministradorDTO;
import com.Rently.Business.DTO.AnfitrionDTO;
import com.Rently.Business.DTO.UsuarioDTO;
import com.Rently.Persistence.Entity.Administrador;
import com.Rently.Persistence.Entity.Anfitrion;
import com.Rently.Persistence.Entity.Usuario;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PersonaMapper {
    
    //  USUARIO MAPPINGS 
    
    
     // Usuario Entity -> UsuarioDTO
     
    @Mapping(target = "rol", source = "rol")
    UsuarioDTO usuarioToDTO(Usuario usuario);
    
  
    // UsuarioDTO -> Usuario Entity (para crear)
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true) // Se maneja por separado por seguridad
    @Mapping(target = "rol", constant = "USUARIO")
    Usuario dtoToUsuario(UsuarioDTO dto);
    
    
    // Lista de Usuario Entity -> Lista de UsuarioDTO

    List<UsuarioDTO> usuariosToDTO(List<Usuario> usuarios);
    

    // Actualizar Usuario existente con datos del DTO
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void updateUsuarioFromDTO(@MappingTarget Usuario usuario, UsuarioDTO dto);
    
    // ANFITRION MAPPINGS

    // Anfitrion Entity -> AnfitrionDTO

    @Mapping(target = "rol", source = "rol")
    AnfitrionDTO anfitrionToDTO(Anfitrion anfitrion);
    
    // AnfitrionDTO -> Anfitrion Entity (para crear)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "rol", constant = "ANFITRION")
    Anfitrion dtoToAnfitrion(AnfitrionDTO dto);
    
   
    // Lista de Anfitrion Entity -> Lista de AnfitrionDTO
  
    List<AnfitrionDTO> anfitrionesToDTO(List<Anfitrion> anfitriones);
    
    
    // Actualizar Anfitrion existente con datos del DTO
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void updateAnfitrionFromDTO(@MappingTarget Anfitrion anfitrion, AnfitrionDTO dto);
    
    // ADMINISTRADOR MAPPINGS 
    
  
    // Administrador Entity -> AdministradorDTO
    
    @Mapping(target = "rol", source = "rol")
    AdministradorDTO adminToDTO(Administrador admin);

    // AdministradorDTO -> Administrador Entity (para crear)

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "rol", constant = "ADMINISTRADOR")
    Administrador dtoToAdmin(AdministradorDTO dto);
    
    // Lista de Administrador Entity -> Lista de AdministradorDTO

    List<AdministradorDTO> adminsToDTO(List<Administrador> admins);

    // Actualizar Administrador existente con datos del DTO
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void updateAdminFromDTO(@MappingTarget Administrador admin, AdministradorDTO dto);
}
