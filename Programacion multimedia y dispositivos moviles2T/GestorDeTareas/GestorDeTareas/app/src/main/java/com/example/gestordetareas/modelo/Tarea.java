package com.example.gestordetareas.modelo;

import android.net.Uri;

public class Tarea {
    private final String titulo;
    private final String descripcion;
    private final String fecha;
    private final Uri imagenUri;
    private final int id; // El id es opcional, puedes hacerlo automático si lo prefieres

    // Constructor con id automático
    public Tarea(String titulo, String descripcion, String fecha, Uri imagenUri) {
        this.id = generateUniqueId();  // Método para generar un ID único
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUri = imagenUri;
    }

    // Constructor sin id
    public Tarea(String titulo, String descripcion, String fecha, Uri imagenUri, int id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUri = imagenUri;
        this.id = id;
    }

    // Getters y setters
    public int getId() {
        return id;
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

    public Uri getImagenUri() {
        return imagenUri;
    }



    // Método para generar un ID único si es necesario
    private static int generateUniqueId() {
        return (int) (System.currentTimeMillis() & 0xfffffff);
    }
}
