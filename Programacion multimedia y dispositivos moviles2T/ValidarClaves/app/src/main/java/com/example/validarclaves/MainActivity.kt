package com.example.validarclaves

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoClave1 = findViewById<EditText>(R.id.editarTextoClave1)
        val editarTextoClave2 = findViewById<EditText>(R.id.editarTextoClave2)
        val botonValidarClaves = findViewById<Button>(R.id.botonValidarClaves)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonValidarClaves.setOnClickListener {
            val clave1 = editarTextoClave1.text.toString()
            val clave2 = editarTextoClave2.text.toString()

            val resultado = validarClaves(clave1, clave2)
            textoResultado.text = resultado
        }
    }

    private fun validarClaves(clave1: String, clave2: String): String {
        return if (clave1 == clave2) {
            "Las claves son iguales"
        } else {
            "Las claves son distintas"
        }
    }
}
