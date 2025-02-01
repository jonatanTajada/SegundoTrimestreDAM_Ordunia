package com.tareas.pendientes.model;

import com.tareas.pendientes.utils.Encriptador;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String passwordEncriptada;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(int id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        setPassword(password); // Se encripta automáticamente
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordEncriptada;
    }

    public void setPassword(String password) {
        this.passwordEncriptada = Encriptador.encriptar(password);
    }

    public boolean verificarPassword(String passwordIngresada) {
        return Encriptador.verificarPassword(passwordIngresada, this.passwordEncriptada);
    }
}
