package com.example.controlpiezas


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var totalPiezas = 0
    private var piezasEnRango = 0
    private var piezasMenos98 = 0
    private var piezasMas102 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoPeso = findViewById<EditText>(R.id.editarTextoPeso)
        val botonAgregarPeso = findViewById<Button>(R.id.botonAgregarPeso)
        val botonFinalizar = findViewById<Button>(R.id.botonFinalizar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarPeso.setOnClickListener {
            val peso = editarTextoPeso.text.toString().toFloatOrNull()
            if (peso != null && peso != 0f) {
                totalPiezas++
                when {
                    peso in 9.8..10.2 -> piezasEnRango++
                    peso < 9.8 -> piezasMenos98++
                    peso > 10.2 -> piezasMas102++
                }
                textoResultado.text = "Peso agregado: $peso Kg"
                editarTextoPeso.text.clear()
            } else if (peso == 0f) {
                textoResultado.text = "Introduce un peso diferente de 0 o pulsa Finalizar."
            } else {
                textoResultado.text = "Error: Introduce un peso válido."
            }
        }

        botonFinalizar.setOnClickListener {
            textoResultado.text = """
                Piezas en rango (9.8 - 10.2 Kg): $piezasEnRango
                Piezas con peso menor a 9.8 Kg: $piezasMenos98
                Piezas con peso mayor a 10.2 Kg: $piezasMas102
                Total de piezas procesadas: $totalPiezas
            """.trimIndent()
        }
    }
}
