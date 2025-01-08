package com.example.compararlistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val lista1 = mutableListOf<Float>()
    private val lista2 = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoValor = findViewById<EditText>(R.id.editarTextoValor)
        val botonAgregarLista1 = findViewById<Button>(R.id.botonAgregarLista1)
        val botonAgregarLista2 = findViewById<Button>(R.id.botonAgregarLista2)
        val botonComparar = findViewById<Button>(R.id.botonComparar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarLista1.setOnClickListener {
            val valor = editarTextoValor.text.toString().toFloatOrNull()
            if (valor != null && lista1.size < 5) {
                lista1.add(valor)
                textoResultado.text = "Valor agregado a Lista 1: $valor"
                editarTextoValor.text.clear()
            } else if (lista1.size >= 5) {
                textoResultado.text = "Lista 1 ya tiene 5 valores."
            } else {
                textoResultado.text = "Error: Introduce un valor válido."
            }
        }

        botonAgregarLista2.setOnClickListener {
            val valor = editarTextoValor.text.toString().toFloatOrNull()
            if (valor != null && lista2.size < 5) {
                lista2.add(valor)
                textoResultado.text = "Valor agregado a Lista 2: $valor"
                editarTextoValor.text.clear()
            } else if (lista2.size >= 5) {
                textoResultado.text = "Lista 2 ya tiene 5 valores."
            } else {
                textoResultado.text = "Error: Introduce un valor válido."
            }
        }

        botonComparar.setOnClickListener {
            if (lista1.size == 5 && lista2.size == 5) {
                val sumaLista1 = lista1.sum()
                val sumaLista2 = lista2.sum()

                val resultado = when {
                    sumaLista1 > sumaLista2 -> "Lista 1 mayor"
                    sumaLista2 > sumaLista1 -> "Lista 2 mayor"
                    else -> "Listas iguales"
                }

                textoResultado.text = """
                    Suma Lista 1: $sumaLista1
                    Suma Lista 2: $sumaLista2
                    Resultado: $resultado
                """.trimIndent()
            } else {
                textoResultado.text = "Ambas listas deben tener 5 valores para comparar."
            }
        }
    }
}
