package com.example.funcionesmatematicas

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCuadrado = findViewById<Button>(R.id.botonCuadrado)
        val botonProducto = findViewById<Button>(R.id.botonProducto)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonCuadrado.setOnClickListener {
            calcularCuadrado { resultado ->
                textoResultado.text = "El cuadrado es: $resultado"
            }
        }

        botonProducto.setOnClickListener {
            calcularProducto { resultado ->
                textoResultado.text = "El producto es: $resultado"
            }
        }
    }

    private fun calcularCuadrado(callback: (Int) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Calcular Cuadrado")

        val input = android.widget.EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Calcular") { _, _ ->
            val numero = input.text.toString().toIntOrNull()
            if (numero != null) {
                callback(numero * numero)
            } else {
                callback(0)
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun calcularProducto(callback: (Int) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Calcular Producto")

        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL

        val input1 = android.widget.EditText(this)
        input1.hint = "Primer número"
        input1.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(input1)

        val input2 = android.widget.EditText(this)
        input2.hint = "Segundo número"
        input2.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        layout.addView(input2)

        builder.setView(layout)

        builder.setPositiveButton("Calcular") { _, _ ->
            val numero1 = input1.text.toString().toIntOrNull()
            val numero2 = input2.text.toString().toIntOrNull()

            if (numero1 != null && numero2 != null) {
                callback(numero1 * numero2)
            } else {
                callback(0)
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}
