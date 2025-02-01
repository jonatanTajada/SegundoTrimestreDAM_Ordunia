package com.tareas.pendientes.model;

public class Tarea {
    private int id;
    private int usuarioId; // Identificador del usuario dueño de la tarea
    private String titulo;
    private String descripcion;
    private String fecha; // Fecha de la tarea en formato String (ej. "2024-02-01")
    private String imagenPath; // Ruta de la imagen en el dispositivo
    private boolean completada;

    // Constructor vacío
    public Tarea() {
    }

    // Constructor con parámetros
    public Tarea(int id, int usuarioId, String titulo, String descripcion, String fecha, String imagenPath, boolean completada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenPath = imagenPath;
        this.completada = completada;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
