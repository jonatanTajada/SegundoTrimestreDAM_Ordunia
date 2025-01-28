package com.example.recetasappproyectofinal.modelo;

public class Receta {
    private int id;
    private String titulo;
    private String ingredientes;
    private String instrucciones;
    private String imagenUri;
    private int categoriaId; // Se añade categoría

    // Constructor completo
    public Receta(int id, String titulo, String ingredientes, String instrucciones, String imagenUri, int categoriaId) {
        this.id = id;
        this.titulo = titulo;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.imagenUri = imagenUri;
        this.categoriaId = categoriaId;
    }

    // Constructor sin imagen (se usa `null` por defecto)
    public Receta(String titulo, String ingredientes, String instrucciones, int categoriaId) {
        this(0, titulo, ingredientes, instrucciones, null, categoriaId);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo != null ? titulo : "Sin título";
    }

    public String getIngredientes() {
        return ingredientes != null ? ingredientes : "Sin ingredientes";
    }

    public String getInstrucciones() {
        return instrucciones != null ? instrucciones : "Sin instrucciones";
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    // Método para verificar si la receta tiene una imagen válida
    public boolean tieneImagen() {
        return imagenUri != null && !imagenUri.isEmpty();
    }
}
