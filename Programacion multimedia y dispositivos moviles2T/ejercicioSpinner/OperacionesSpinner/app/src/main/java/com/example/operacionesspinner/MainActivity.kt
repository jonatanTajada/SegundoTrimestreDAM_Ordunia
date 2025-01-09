package com.example.operacionesspinner

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numero1 = findViewById<EditText>(R.id.numero1)
        val numero2 = findViewById<EditText>(R.id.numero2)
        val spinnerOperaciones = findViewById<Spinner>(R.id.spinnerOperaciones)
        val botonOperar = findViewById<Button>(R.id.botonOperar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        // Configurar el spinner con el recurso string-array
        ArrayAdapter.createFromResource(
            this,
            R.array.operaciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOperaciones.adapter = adapter
        }

        botonOperar.setOnClickListener {
            val num1 = numero1.text.toString().toFloatOrNull()
            val num2 = numero2.text.toString().toFloatOrNull()
            val operacion = spinnerOperaciones.selectedItem.toString()

            if (num1 != null && num2 != null) {
                val resultado = when (operacion) {
                    "sumar" -> num1 + num2
                    "restar" -> num1 - num2
                    "multiplicar" -> num1 * num2
                    "dividir" -> if (num2 != 0f) num1 / num2 else "Error: División por cero"
                    else -> "Operación no válida"
                }
                textoResultado.text = "Resultado: $resultado"
            } else {
                textoResultado.text = "Por favor, ingresa números válidos."
            }
        }
    }
}
