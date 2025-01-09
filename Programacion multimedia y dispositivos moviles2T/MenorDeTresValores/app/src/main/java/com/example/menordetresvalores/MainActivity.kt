package com.example.menordetresvalores

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonPrimeraLlamada = findViewById<Button>(R.id.botonPrimeraLlamada)
        val botonSegundaLlamada = findViewById<Button>(R.id.botonSegundaLlamada)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonPrimeraLlamada.setOnClickListener {
            solicitarValores { menor ->
                textoResultado.text = "Primera llamada: El menor es $menor"
            }
        }

        botonSegundaLlamada.setOnClickListener {
            solicitarValores { menor ->
                textoResultado.text = "Segunda llamada: El menor es $menor"
            }
        }
    }

    private fun solicitarValores(callback: (Int) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Solicitar tres valores")

        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL

        val input1 = android.widget.EditText(this)
        input1.hint = "Primer valor"
        input1.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(input1)

        val input2 = android.widget.EditText(this)
        input2.hint = "Segundo valor"
        input2.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(input2)

        val input3 = android.widget.EditText(this)
        input3.hint = "Tercer valor"
        input3.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(input3)

        builder.setView(layout)

        builder.setPositiveButton("Calcular") { _, _ ->
            val valor1 = input1.text.toString().toIntOrNull()
            val valor2 = input2.text.toString().toIntOrNull()
            val valor3 = input3.text.toString().toIntOrNull()

            if (valor1 != null && valor2 != null && valor3 != null) {
                val menor = minOf(valor1, valor2, valor3)
                callback(menor)
            } else {
                callback(Int.MAX_VALUE) // Valor muy grande en caso de error
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}
