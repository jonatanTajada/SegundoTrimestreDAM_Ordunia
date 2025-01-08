package com.example.notasalumnos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var notasMayorIgual7 = 0
    private var notasMenores7 = 0
    private var totalNotasIngresadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoNota = findViewById<EditText>(R.id.editarTextoNota)
        val botonAgregarNota = findViewById<Button>(R.id.botonAgregarNota)
        val botonFinalizar = findViewById<Button>(R.id.botonFinalizar)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonAgregarNota.setOnClickListener {
            val nota = editarTextoNota.text.toString().toFloatOrNull()
            if (nota != null && nota in 0.0..10.0) {
                totalNotasIngresadas++
                if (nota >= 7) {
                    notasMayorIgual7++
                } else {
                    notasMenores7++
                }
                textoResultado.text = "Nota agregada: $nota"
                editarTextoNota.text.clear()

                if (totalNotasIngresadas == 10) {
                    textoResultado.text = "Se han ingresado las 10 notas. Pulsa Finalizar."
                }
            } else {
                textoResultado.text = "Error: Introduce una nota válida entre 0 y 10."
            }
        }

        botonFinalizar.setOnClickListener {
            if (totalNotasIngresadas == 10) {
                textoResultado.text = """
                    Notas mayores o iguales a 7: $notasMayorIgual7
                    Notas menores a 7: $notasMenores7
                """.trimIndent()
            } else {
                textoResultado.text = "Error: Aún no se han ingresado las 10 notas."
            }
        }
    }
}
