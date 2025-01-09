package com.example.ordenartresnumeros

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoNumero1 = findViewById<EditText>(R.id.editarTextoNumero1)
        val editarTextoNumero2 = findViewById<EditText>(R.id.editarTextoNumero2)
        val editarTextoNumero3 = findViewById<EditText>(R.id.editarTextoNumero3)
        val botonOrdenar = findViewById<Button>(R.id.botonOrdenar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonOrdenar.setOnClickListener {
            val numero1 = editarTextoNumero1.text.toString().toIntOrNull()
            val numero2 = editarTextoNumero2.text.toString().toIntOrNull()
            val numero3 = editarTextoNumero3.text.toString().toIntOrNull()

            if (numero1 != null && numero2 != null && numero3 != null) {
                val resultado = ordenarNumeros(numero1, numero2, numero3)
                textoResultado.text = "Números ordenados: $resultado"
            } else {
                textoResultado.text = "Error: Introduce números válidos."
            }
        }
    }

    private fun ordenarNumeros(num1: Int, num2: Int, num3: Int): String {
        val listaOrdenada = listOf(num1, num2, num3).sorted()
        return listaOrdenada.joinToString(", ")
    }
}
