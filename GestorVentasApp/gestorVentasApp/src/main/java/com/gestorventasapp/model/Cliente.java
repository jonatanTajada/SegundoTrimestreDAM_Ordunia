package com.gestorventasapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCliente;

   
    @Column(name = "nombre", nullable = false, length = 50)
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50, message = "El nombre no puede tener mas de 50 caracteres")
    private String nombre;

   
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El formato del email no es valido")
    private String email;

 
    @Column(name = "telefono", nullable = false, length = 9)
    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El telefono debe tener 9 digitos")
    private String telefono;
    
    
    // Constructor personalizado
    public Cliente(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }
    
    
}
