package com.tareas.pendientes.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tarea {
    private final int id;

    private int usuarioId;
    private String titulo;
    private String descripcion;
    private String fecha;
    private String imagen;
    private boolean completada;

    // Constructor
    public Tarea(int id, int usuarioId, String titulo, String descripcion, String fecha, String imagen, boolean completada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = (fecha != null && !fecha.isEmpty()) ? fecha : obtenerFechaActual();
        this.imagen = imagen;
        this.completada = completada;
    }

    // Método para obtener la fecha actual del sistema en formato "dd-MM-yyyy"
    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isCompletada() {
        return completada;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = (fecha != null && !fecha.isEmpty()) ? fecha : obtenerFechaActual(); // También en setter
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
