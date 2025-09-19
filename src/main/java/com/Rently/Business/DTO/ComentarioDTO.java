package com.Rently.Business.DTO;

import java.time.LocalDate;

public class ComentarioDTO {

    private Long id;
    private Integer calificacion;
    private String comentario;
    private String respuesta;
    private LocalDate fecha;
    private Long usuarioId;
    private Long alojamientoId;

    public ComentarioDTO() { }

    public ComentarioDTO(Long id, Integer calificacion, String comentario, String respuesta,
                         LocalDate fecha, Long usuarioId, Long alojamientoId) {
        this.id = id;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.alojamientoId = alojamientoId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getAlojamientoId() { return alojamientoId; }
    public void setAlojamientoId(Long alojamientoId) { this.alojamientoId = alojamientoId; }

    @Override
    public String toString() {
        return "ComentarioDTO{" +
                "id=" + id +
                ", calificacion=" + calificacion +
                ", comentario='" + comentario + '\'' +
                ", respuesta='" + respuesta + '\'' +
                ", fecha=" + fecha +
                ", usuarioId=" + usuarioId +
                ", alojamientoId=" + alojamientoId +
                '}';
    }
}



