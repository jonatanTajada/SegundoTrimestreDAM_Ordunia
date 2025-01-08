package com.example.cantidaddecifras

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoNumero = findViewById<EditText>(R.id.editarTextoNumero)
        val botonVerificar = findViewById<Button>(R.id.botonVerificar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonVerificar.setOnClickListener {
            val entrada = editarTextoNumero.text.toString()

            if (entrada.isNotEmpty()) {
                val numero = entrada.toIntOrNull()

                if (numero != null && numero > 0) {
                    val cantidadCifras = entrada.length

                    val mensajeResultado = when (cantidadCifras) {
                        1 -> "El número tiene 1 cifra."
                        2 -> "El número tiene 2 cifras."
                        3 -> "El número tiene 3 cifras."
                        else -> "Error: El número tiene más de 3 cifras."
                    }

                    textoResultado.text = mensajeResultado
                } else {
                    textoResultado.text = "Error: Introduce un número válido (positivo)."
                }
            } else {
                textoResultado.text = "Error: El campo está vacío."
            }
        }
    }
}
