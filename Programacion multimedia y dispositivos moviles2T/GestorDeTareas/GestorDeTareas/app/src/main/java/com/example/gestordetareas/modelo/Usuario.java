package com.example.gestordetareas.modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String ciudad;

    // Constructor vacío (obligatorio para SQLite)
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nombre, String correo, String password, String telefono, String ciudad) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    /*public Usuario(String nombre, String correo, String telefono, String ciudad) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }*/


    // 🔹 Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}
