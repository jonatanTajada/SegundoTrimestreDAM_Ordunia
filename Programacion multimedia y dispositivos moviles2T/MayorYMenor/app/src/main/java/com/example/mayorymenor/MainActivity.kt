package com.example.mayorymenor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextNumero1 = findViewById<EditText>(R.id.editTextNumero1)
        val editTextNumero2 = findViewById<EditText>(R.id.editTextNumero2)
        val editTextNumero3 = findViewById<EditText>(R.id.editTextNumero3)
        val botonCalcular = findViewById<Button>(R.id.botonCalcular)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonCalcular.setOnClickListener {
            val numero1 = editTextNumero1.text.toString().toIntOrNull()
            val numero2 = editTextNumero2.text.toString().toIntOrNull()
            val numero3 = editTextNumero3.text.toString().toIntOrNull()

            if (numero1 != null && numero2 != null && numero3 != null) {
                // Calcular el mayor usando expresiones if
                val mayor = if (numero1 >= numero2 && numero1 >= numero3) {
                    numero1
                } else if (numero2 >= numero1 && numero2 >= numero3) {
                    numero2
                } else {
                    numero3
                }

                // Calcular el menor usando expresiones if
                val menor = if (numero1 <= numero2 && numero1 <= numero3) {
                    numero1
                } else if (numero2 <= numero1 && numero2 <= numero3) {
                    numero2
                } else {
                    numero3
                }

                // Mostrar el resultado
                textoResultado.text = "Mayor: $mayor\nMenor: $menor"
            } else {
                // Mostrar error si los valores ingresados no son válidos
                textoResultado.text = "Error: Verifica los valores ingresados."
            }
        }
    }
}
