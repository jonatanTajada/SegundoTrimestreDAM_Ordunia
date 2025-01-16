package com.example.calendarioactividades

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del XML
        val editTextFecha = findViewById<EditText>(R.id.editTextFecha)
        val editTextActividades = findViewById<EditText>(R.id.editTextActividades)
        val buttonGrabar = findViewById<Button>(R.id.buttonGrabar)
        val buttonRecuperar = findViewById<Button>(R.id.buttonRecuperar)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

        // Botón para grabar actividades
        buttonGrabar.setOnClickListener {
            val fecha = editTextFecha.text.toString().replace("/", "_") // Reemplazar "/" por "_"
            val actividades = editTextActividades.text.toString()

            if (fecha.isNotBlank() && actividades.isNotBlank()) {
                try {
                    // Crear o abrir archivo en modo "append" para añadir texto
                    val archivo = File(filesDir, "$fecha.txt")
                    val fos = FileOutputStream(archivo, true) // true para "append"
                    fos.write("$actividades\n".toByteArray()) // Añadir nueva tarea con salto de línea
                    fos.close()

                    Toast.makeText(this, "Tarea añadida correctamente", Toast.LENGTH_SHORT).show()

                    // Limpiar campos después de grabar
                    editTextFecha.text.clear()
                    editTextActividades.text.clear()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al grabar la tarea", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para recuperar actividades
        buttonRecuperar.setOnClickListener {
            val fecha = editTextFecha.text.toString().replace("/", "_") // Reemplazar "/" por "_"

            if (fecha.isNotBlank()) {
                try {
                    // Leer el archivo correspondiente a la fecha ingresada
                    val archivo = File(filesDir, "$fecha.txt")
                    if (archivo.exists()) {
                        val fis = FileInputStream(archivo)
                        val contenido = fis.bufferedReader().use { it.readText() }
                        fis.close()

                        // Mostrar contenido en el TextView
                        textViewResultado.text = contenido
                    } else {
                        Toast.makeText(this, "No hay tareas para esta fecha", Toast.LENGTH_SHORT).show()
                        textViewResultado.text = "" // Limpiar el TextView si no hay tareas
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al recuperar las tareas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce una fecha", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
