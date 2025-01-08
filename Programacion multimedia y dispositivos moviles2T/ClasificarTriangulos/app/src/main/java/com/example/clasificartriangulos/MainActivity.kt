package com.example.clasificartriangulos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var cantidadEquilateros = 0
    private var cantidadIsosceles = 0
    private var cantidadEscalenos = 0
    private var totalTriangulos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoLado1 = findViewById<EditText>(R.id.editarTextoLado1)
        val editarTextoLado2 = findViewById<EditText>(R.id.editarTextoLado2)
        val editarTextoLado3 = findViewById<EditText>(R.id.editarTextoLado3)
        val botonClasificar = findViewById<Button>(R.id.botonClasificar)
        val botonFinalizar = findViewById<Button>(R.id.botonFinalizar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonClasificar.setOnClickListener {
            val lado1 = editarTextoLado1.text.toString().toFloatOrNull()
            val lado2 = editarTextoLado2.text.toString().toFloatOrNull()
            val lado3 = editarTextoLado3.text.toString().toFloatOrNull()

            if (lado1 != null && lado2 != null && lado3 != null) {
                totalTriangulos++
                when {
                    lado1 == lado2 && lado2 == lado3 -> {
                        cantidadEquilateros++
                        textoResultado.text = "Triángulo Equilátero"
                    }
                    lado1 == lado2 || lado2 == lado3 || lado1 == lado3 -> {
                        cantidadIsosceles++
                        textoResultado.text = "Triángulo Isósceles"
                    }
                    else -> {
                        cantidadEscalenos++
                        textoResultado.text = "Triángulo Escaleno"
                    }
                }
                editarTextoLado1.text.clear()
                editarTextoLado2.text.clear()
                editarTextoLado3.text.clear()
            } else {
                textoResultado.text = "Error: Introduce valores válidos para los lados."
            }
        }

        botonFinalizar.setOnClickListener {
            textoResultado.text = """
                Triángulos Equiláteros: $cantidadEquilateros
                Triángulos Isósceles: $cantidadIsosceles
                Triángulos Escalenos: $cantidadEscalenos
                Total de Triángulos: $totalTriangulos
            """.trimIndent()
        }
    }
}
