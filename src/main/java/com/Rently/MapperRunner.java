package com.Rently;

import com.Rently.Business.DTO.*;
import com.Rently.Persistence.Entity.*;
import com.Rently.Persistence.Mapper.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MapperRunner {

    public static void main(String[] args) {
        SpringApplication.run(MapperRunner.class, args);
    }

    @Bean
    public CommandLineRunner runMappers(
            AlojamientoMapper alojamientoMapper,
            ReservaMapper reservaMapper,
            ComentarioMapper comentarioMapper,
            NotificacionMapper notificacionMapper,
            TransaccionMapper transaccionMapper,
            PersonaMapper personaMapper) {
        return args -> {
            // ALOJAMIENTO MAPPER
            Alojamiento alojamiento = new Alojamiento();
            alojamiento.setId(1L);
            alojamiento.setTitulo("Casa en la playa");

            AlojamientoDTO alojamientoDTO = alojamientoMapper.toDTO(alojamiento);
            System.out.println("AlojamientoDTO → " + alojamientoDTO);

            // RESERVA MAPPER 
            ReservaDTO reservaDTO = new ReservaDTO();
            reservaDTO.setUsuarioId(1L);
            reservaDTO.setAlojamientoId(1L);

            Reserva reserva = reservaMapper.toEntity(reservaDTO);
            System.out.println("Reserva entity creada → " + reserva);

            // COMENTARIO MAPPER 
            ComentarioDTO comentarioDTO = new ComentarioDTO();
            comentarioDTO.setUsuarioId(1L);
            comentarioDTO.setAlojamientoId(1L);
            comentarioDTO.setComentario("Muy buen alojamiento");
            comentarioDTO.setCalificacion(6); 

            Comentario comentario = comentarioMapper.toEntity(comentarioDTO);
            System.out.println("Comentario entity creado → " + comentario);

            //NOTIFICACION MAPPER 
            NotificacionDTO notiDTO = new NotificacionDTO();
            notiDTO.setUsuarioId(1L);
            notiDTO.setReservaId(1L);
            notiDTO.setComentarioId(1L);

            Notificacion noti = notificacionMapper.toEntity(notiDTO);
            System.out.println("Notificación entity creada → " + noti);

            // TRANSACCION MAPPER 
            TransaccionDTO transDTO = new TransaccionDTO();
            transDTO.setReservaId(1L);

            Transaccion transaccion = transaccionMapper.toEntity(transDTO);
            System.out.println("Transacción entity creada (PENDIENTE) → " + transaccion);
            transaccionMapper.aprobarTransaccion(transaccion);
            System.out.println("Transacción aprobada → " + transaccion);

            // PERSONA MAPPER 
            Usuario usuario = new Usuario();
            usuario.setNombre("Bryan");
            usuario.setEmail("bryan@mail.com");

            UsuarioDTO usuarioDTO = personaMapper.usuarioToDTO(usuario);
            System.out.println("UsuarioDTO → " + usuarioDTO);

            Anfitrion anfitrion = new Anfitrion();
            anfitrion.setNombre("Carlos");
            AnfitrionDTO anfitrionDTO = personaMapper.anfitrionToDTO(anfitrion);
            System.out.println("AnfitrionDTO → " + anfitrionDTO);

            Administrador admin = new Administrador();
            admin.setNombre("Laura");
            AdministradorDTO adminDTO = personaMapper.adminToDTO(admin);
            System.out.println("AdministradorDTO → " + adminDTO);
            System.out.println("\n Todos los mappers probados correctamente");
         
        };
    }
}
