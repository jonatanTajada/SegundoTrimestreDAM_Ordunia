package com.example.sueldosempresa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val sueldos = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoNumeroEmpleados = findViewById<EditText>(R.id.editarTextoNumeroEmpleados)
        val editarTextoSueldos = findViewById<EditText>(R.id.editarTextoSueldos)
        val botonAgregarSueldo = findViewById<Button>(R.id.botonAgregarSueldo)
        val botonCalcular = findViewById<Button>(R.id.botonCalcular)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarSueldo.setOnClickListener {
            val sueldo = editarTextoSueldos.text.toString().toFloatOrNull()
            val totalEmpleados = editarTextoNumeroEmpleados.text.toString().toIntOrNull()

            if (sueldo != null && sueldo in 100.0..500.0 && totalEmpleados != null) {
                if (sueldos.size < totalEmpleados) {
                    sueldos.add(sueldo)
                    textoResultado.text = "Sueldo agregado: $sueldo"
                    editarTextoSueldos.text.clear()
                } else {
                    textoResultado.text = "Ya se ingresaron todos los sueldos."
                }
            } else {
                textoResultado.text = "Error: Introduce un sueldo válido entre 100 y 500 euros."
            }
        }

        botonCalcular.setOnClickListener {
            if (sueldos.isNotEmpty()) {
                val entre100y300 = sueldos.count { it in 100.0..300.0 }
                val masDe300 = sueldos.count { it > 300.0 }
                val totalSueldos = sueldos.sum()

                textoResultado.text = """
                    Empleados que cobran entre 100 y 300 euros: $entre100y300
                    Empleados que cobran más de 300 euros: $masDe300
                    Gasto total en sueldos: $totalSueldos euros
                """.trimIndent()
            } else {
                textoResultado.text = "No se han ingresado sueldos para calcular."
            }
        }
    }
}
