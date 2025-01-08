package com.example.analizarvalores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var valoresNegativos = 0
    private var valoresPositivos = 0
    private var multiplosDe15 = 0
    private var sumaPares = 0
    private var totalValoresIngresados = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoValor = findViewById<EditText>(R.id.editarTextoValor)
        val botonAgregarValor = findViewById<Button>(R.id.botonAgregarValor)
        val botonFinalizar = findViewById<Button>(R.id.botonFinalizar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarValor.setOnClickListener {
            val valor = editarTextoValor.text.toString().toIntOrNull()

            if (valor != null) {
                totalValoresIngresados++

                // Clasificar como negativo o positivo
                if (valor < 0) {
                    valoresNegativos++
                } else if (valor > 0) { // Aseguramos que 0 no se cuente como positivo
                    valoresPositivos++
                }

                // Verificar múltiplos de 15
                if (valor % 15 == 0) {
                    multiplosDe15++
                }

                // Acumular valores pares
                if (valor % 2 == 0) {
                    sumaPares += valor
                }

                // Mostrar el último valor agregado
                textoResultado.text = "Valor agregado: $valor"
                editarTextoValor.text.clear()

                // Comprobar si se alcanzaron los 10 valores
                if (totalValoresIngresados == 10) {
                    textoResultado.text = "Se han ingresado los 10 valores. Pulsa Finalizar."
                }
            } else {
                textoResultado.text = "Error: Introduce un valor válido."
            }
        }

        botonFinalizar.setOnClickListener {
            if (totalValoresIngresados == 10) {
                textoResultado.text = """
            Valores negativos: $valoresNegativos
            Valores positivos: $valoresPositivos
            Múltiplos de 15: $multiplosDe15
            Suma de valores pares: $sumaPares
        """.trimIndent()
            } else {
                textoResultado.text = "Error: Aún no se han ingresado los 10 valores."
            }
        }




    }
}
