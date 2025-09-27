package com.Rently.Business.DTO;

import com.Rently.Persistence.Entity.TipoAlojamiento;

import java.time.LocalDateTime;
import java.util.List;

public class AlojamientoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private Double precioPorNoche;
    private Integer capacidadMaxima;
    private boolean eliminado;
    private Long anfitrionId;
    private TipoAlojamiento tipoAlojamiento;
    private List<String> imagenes;
    private List<Long> serviciosId;
    private List<ComentarioDTO> comentarios;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AlojamientoDTO() {}

    public AlojamientoDTO(Long id, String titulo, String descripcion, String ciudad, String direccion,
                                 Double latitud, Double longitud, Double precioPorNoche, Integer capacidadMaxima,
                                 boolean eliminado, Long anfitrionId, TipoAlojamiento tipoAlojamiento,
                                 List<String> imagenes, List<Long> serviciosID, List<ComentarioDTO> comentarios,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioPorNoche = precioPorNoche;
        this.capacidadMaxima = capacidadMaxima;
        this.eliminado = eliminado;
        this.anfitrionId = anfitrionId;
        this.tipoAlojamiento = tipoAlojamiento;
        this.imagenes = imagenes;
        this.serviciosId = serviciosId;
        this.comentarios = comentarios;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Double getPrecioPorNoche() { return precioPorNoche; }
    public void setPrecioPorNoche(Double precioPorNoche) { this.precioPorNoche = precioPorNoche; }

    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public Long getAnfitrionId() { return anfitrionId; }
    public void setAnfitrionId(Long anfitrionId) { this.anfitrionId = anfitrionId; }

    public TipoAlojamiento getTipoAlojamiento() { return tipoAlojamiento; }
    public void setTipoAlojamiento(TipoAlojamiento tipoAlojamientoId) { this.tipoAlojamiento = tipoAlojamientoId; }

    public List<String> getImagenes() { return imagenes; }
    public void setImagenes(List<String> imagenes) { this.imagenes = imagenes; }

    public List<Long> getServiciosId() { return serviciosId; }
    public void setServiciosId(List<Long> serviciosId) { this.serviciosId = serviciosId; }

    public List<ComentarioDTO> getComentarios() { return comentarios; }
    public void setComentarios(List<ComentarioDTO> comentarios) { this.comentarios = comentarios; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
