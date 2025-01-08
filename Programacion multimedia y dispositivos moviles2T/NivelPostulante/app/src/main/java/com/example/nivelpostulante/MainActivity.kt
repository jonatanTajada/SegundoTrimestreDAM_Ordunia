package com.example.nivelpostulante

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoTotalPreguntas = findViewById<EditText>(R.id.editarTextoTotalPreguntas)
        val editarTextoRespuestasCorrectas = findViewById<EditText>(R.id.editarTextoRespuestasCorrectas)
        val botonCalcularNivel = findViewById<Button>(R.id.botonCalcularNivel)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonCalcularNivel.setOnClickListener {
            val totalPreguntas = editarTextoTotalPreguntas.text.toString().toIntOrNull()
            val respuestasCorrectas = editarTextoRespuestasCorrectas.text.toString().toIntOrNull()

            if (totalPreguntas != null && respuestasCorrectas != null) {
                if (totalPreguntas > 0 && respuestasCorrectas <= totalPreguntas) {
                    val porcentaje = (respuestasCorrectas.toDouble() / totalPreguntas) * 100

                    val nivel = when {
                        porcentaje >= 90 -> "Nivel máximo: Porcentaje >= 90%."
                        porcentaje >= 75 -> "Nivel medio: Porcentaje >= 75% y < 90%."
                        porcentaje >= 50 -> "Nivel regular: Porcentaje >= 50% y < 75%."
                        else -> "Fuera de nivel: Porcentaje < 50%."
                    }

                    textoResultado.text = "Porcentaje: ${"%.2f".format(porcentaje)}%\n$nivel"
                } else {
                    textoResultado.text = "Error: Las respuestas correctas no pueden ser mayores que el total de preguntas."
                }
            } else {
                textoResultado.text = "Error: Verifica los valores ingresados."
            }
        }
    }
}
