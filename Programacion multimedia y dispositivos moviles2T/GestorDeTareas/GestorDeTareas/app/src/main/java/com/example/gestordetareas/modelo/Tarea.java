package com.example.gestordetareas.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "tareas")
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titulo")
    private String titulo;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "imagenUri")
    private String imagenUri;

    // Constructor vacío (Room lo necesita)
    public Tarea() {}

    // Constructor con parámetros
    public Tarea(String titulo, String descripcion, String fecha, String imagenUri) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUri = imagenUri;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getImagenUri() { return imagenUri; }
    public void setImagenUri(String imagenUri) { this.imagenUri = imagenUri; }
}
