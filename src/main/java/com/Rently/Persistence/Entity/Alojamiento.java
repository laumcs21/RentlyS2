package com.Rently.Persistence.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    @Column(length = 2000)
    private String descripcion;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private Double precioPorNoche;
    private Integer capacidadMaxima;

    @ElementCollection
    @CollectionTable(name = "alojamiento_servicios", joinColumns = @JoinColumn(name = "alojamiento_id"))
    @Column(name = "servicio")
    private List<String> servicios;


    private boolean eliminado = false;

    @ManyToOne
    private Anfitrion anfitrion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alojamiento")
    private TipoAlojamiento tipoAlojamiento;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    public Alojamiento() { }

    public Alojamiento(Long id, String titulo, String descripcion, String ciudad, String direccion, Double latitud, Double longitud, Double precioPorNoche, Integer capacidadMaxima, List<String> servicios, boolean eliminado, Anfitrion anfitrion, TipoAlojamiento tipoAlojamiento) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioPorNoche = precioPorNoche;
        this.capacidadMaxima = capacidadMaxima;
        this.servicios=servicios;
        this.eliminado = eliminado;
        this.anfitrion = anfitrion;
        this.tipoAlojamiento = tipoAlojamiento;
    }

    // Getters / Setters
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

    public Anfitrion getAnfitrion() { return anfitrion; }
    public void setAnfitrion(Anfitrion anfitrion) { this.anfitrion = anfitrion; }

    public TipoAlojamiento getTipoAlojamiento() { return tipoAlojamiento; }
    public void setTipoAlojamiento(TipoAlojamiento tipoAlojamiento) { this.tipoAlojamiento = tipoAlojamiento; }

    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }
}

