package com.example.reproductormusica

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editarTextoTitulo = findViewById<EditText>(R.id.editarTextoTitulo)
        val editarTextoArtista = findViewById<EditText>(R.id.editarTextoArtista)
        val editarTextoAnio = findViewById<EditText>(R.id.editarTextoAnio)
        val editarTextoReproducciones = findViewById<EditText>(R.id.editarTextoReproducciones)
        val botonMostrarInfo = findViewById<Button>(R.id.botonMostrarInfo)
        val textoResultado = findViewById<TextView>(R.id.textoResultado)

        botonMostrarInfo.setOnClickListener {
            val titulo = editarTextoTitulo.text.toString()
            val artista = editarTextoArtista.text.toString()
            val anio = editarTextoAnio.text.toString().toIntOrNull()
            val reproducciones = editarTextoReproducciones.text.toString().toIntOrNull()

            if (titulo.isNotEmpty() && artista.isNotEmpty() && anio != null && reproducciones != null) {
                val cancion = Song(titulo, artista, anio, reproducciones)
                val info = cancion.descripcion() + "\n" +
                        if (cancion.esPopular) "Es una canción popular." else "Es una canción poco popular."
                textoResultado.text = info
            } else {
                textoResultado.text = "Error: Completa todos los campos correctamente."
            }
        }
    }
}
