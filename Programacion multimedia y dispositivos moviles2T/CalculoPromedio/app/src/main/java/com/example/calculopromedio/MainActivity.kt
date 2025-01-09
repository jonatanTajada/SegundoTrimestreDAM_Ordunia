package com.example.calculopromedio

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoNota1 = findViewById<EditText>(R.id.editarTextoNota1)
        val editarTextoNota2 = findViewById<EditText>(R.id.editarTextoNota2)
        val editarTextoNota3 = findViewById<EditText>(R.id.editarTextoNota3)
        val botonCalcularPromedio = findViewById<Button>(R.id.botonCalcularPromedio)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonCalcularPromedio.setOnClickListener {
            val nota1 = editarTextoNota1.text.toString().toFloatOrNull()
            val nota2 = editarTextoNota2.text.toString().toFloatOrNull()
            val nota3 = editarTextoNota3.text.toString().toFloatOrNull()

            if (nota1 != null && nota2 != null && nota3 != null) {
                val promedio = (nota1 + nota2 + nota3) / 3

                val resultado = when {
                    promedio >= 7 -> "Promocionado"
                    promedio >= 4 -> "Regular"
                    else -> "Suspenso"
                }

                textoResultado.text = "Promedio: $promedio\nResultado: $resultado"
            } else {
                textoResultado.text = "Error: Introduce todas las notas correctamente."
            }
        }
    }
}
