package com.example.ejerciciogridview

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al GridView desde el XML
        val gridView = findViewById<GridView>(R.id.gridView)

        // Configuración del adaptador
        gridView.adapter = ImageAdapter(this)

        // Lista de nombres para las imágenes
        val items = arrayOf(
            "Icono",
            "Icono Interrogante",
            "Inter",
            "Interrogación",
            "Interrogante",
            "Interrogantee"
        )

        // Manejo del evento de clic en cada imagen
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Seleccionaste: ${items[position]}", Toast.LENGTH_SHORT).show()
        }
    }
}
