package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer calificacion;

    @Column(length = 1000)
    private String comentario;

    @Column(length = 1000)
    private String respuesta;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    public Comentario() { }

    public Comentario(Long id, Integer calificacion, String comentario, String respuesta,
                      LocalDate fecha, Usuario usuario, Alojamiento alojamiento) {

        assert calificacion <= 5 || calificacion > 0;

        this.id = id;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.respuesta = respuesta;
        this.fecha = fecha;
        this.usuario = usuario;
        this.alojamiento = alojamiento;
    }

    // 🔹 Getters y Setters
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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Alojamiento getAlojamiento() { return alojamiento; }
    public void setAlojamiento(Alojamiento alojamiento) { this.alojamiento = alojamiento; }


    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", calificacion=" + calificacion +
                ", comentario='" + comentario + '\'' +
                ", respuesta='" + respuesta + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comentario that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

