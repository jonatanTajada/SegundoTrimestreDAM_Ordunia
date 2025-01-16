package com.example.adivinanumero

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var numeroAleatorio = 0
    private var puntaje = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferencias = getSharedPreferences("AdivinaNumeroPrefs", Context.MODE_PRIVATE)
        val editor = preferencias.edit()

        // Recuperar el puntaje guardado
        puntaje = preferencias.getInt("puntaje", 0)
        numeroAleatorio = generarNumeroAleatorio()

        // Referencias de las vistas
        val textoPuntaje = findViewById<TextView>(R.id.textoPuntaje)
        val campoNumero = findViewById<EditText>(R.id.campoNumero)
        val botonVerificar = findViewById<Button>(R.id.botonVerificar)
        val textoPista = findViewById<TextView>(R.id.textoPista)

        // Mostrar el puntaje actual
        textoPuntaje.text = puntaje.toString()

        botonVerificar.setOnClickListener {
            val numeroUsuario = campoNumero.text.toString().toIntOrNull()

            if (numeroUsuario == null) {
                textoPista.text = getString(R.string.introducir_numero_valido)
                return@setOnClickListener
            }

            when {
                numeroUsuario < numeroAleatorio -> {
                    textoPista.text = getString(R.string.numero_mayor)
                }
                numeroUsuario > numeroAleatorio -> {
                    textoPista.text = getString(R.string.numero_menor)
                }
                else -> {
                    puntaje++
                    textoPuntaje.text = puntaje.toString()

                    // Guardar el puntaje en SharedPreferences
                    editor.putInt("puntaje", puntaje)
                    editor.apply()

                    // Mensaje al acertar
                    textoPista.text = getString(R.string.numero_correcto)
                    campoNumero.text.clear()
                    numeroAleatorio = generarNumeroAleatorio()
                }
            }
        }
    }

    private fun generarNumeroAleatorio(): Int {
        return Random.nextInt(1, 51)
    }
}
