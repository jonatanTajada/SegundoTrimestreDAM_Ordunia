package com.example.tablamultiplicar

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
        val botonMostrarTabla = findViewById<Button>(R.id.botonMostrarTabla)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonMostrarTabla.setOnClickListener {
            val numero = editarTextoNumero.text.toString().toIntOrNull()

            if (numero != null && numero in 1..10) {
                val tabla = StringBuilder()
                for (i in 1..12) {
                    tabla.append("$numero x $i = ${numero * i}\n")
                }
                textoResultado.text = tabla.toString()
            } else {
                textoResultado.text = "Error: Introduce un número válido entre 1 y 10."
            }
        }
    }
}
