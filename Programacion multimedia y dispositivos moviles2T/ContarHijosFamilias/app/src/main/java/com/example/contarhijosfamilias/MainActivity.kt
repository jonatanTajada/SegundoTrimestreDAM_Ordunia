package com.example.contarhijosfamilias

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var familiasCon0Hijos = 0
    private var familiasCon1Hijo = 0
    private var familiasCon2OMasHijos = 0
    private var totalFamilias = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoHijos = findViewById<EditText>(R.id.editarTextoHijos)
        val botonAgregarFamilia = findViewById<Button>(R.id.botonAgregarFamilia)
        val botonFinalizar = findViewById<Button>(R.id.botonFinalizar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarFamilia.setOnClickListener {
            val cantidadHijos = editarTextoHijos.text.toString().toIntOrNull()

            if (cantidadHijos != null && totalFamilias < 10) {
                totalFamilias++

                when {
                    cantidadHijos == 0 -> familiasCon0Hijos++
                    cantidadHijos == 1 -> familiasCon1Hijo++
                    cantidadHijos >= 2 -> familiasCon2OMasHijos++
                }

                textoResultado.text = "Familia registrada: $cantidadHijos hijos"
                editarTextoHijos.text.clear()

                if (totalFamilias == 10) {
                    textoResultado.text = "Se han registrado las 10 familias. Pulsa Finalizar."
                }
            } else {
                textoResultado.text = "Error: Introduce un número válido y verifica que no excedas las 10 familias."
            }
        }

        botonFinalizar.setOnClickListener {
            if (totalFamilias == 10) {
                textoResultado.text = """
                    Familias con 0 hijos: $familiasCon0Hijos
                    Familias con 1 hijo: $familiasCon1Hijo
                    Familias con 2 o más hijos: $familiasCon2OMasHijos
                """.trimIndent()
            } else {
                textoResultado.text = "Error: Aún no se han registrado las 10 familias."
            }
        }
    }
}
