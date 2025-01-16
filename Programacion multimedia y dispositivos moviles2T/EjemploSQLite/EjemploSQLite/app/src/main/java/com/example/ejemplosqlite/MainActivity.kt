package com.example.ejemplosqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var baseDatos: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar la base de datos
        baseDatos = BaseDatos(this)

        // Referencias a los elementos del layout
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val buttonInsertar = findViewById<Button>(R.id.buttonInsertar)
        val buttonMostrar = findViewById<Button>(R.id.buttonMostrar)
        val textViewDatos = findViewById<TextView>(R.id.textViewDatos)

        // Acción del botón Insertar
        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()

            if (nombre.isNotEmpty() && correo.isNotEmpty()) {
                val db = baseDatos.writableDatabase
                db.execSQL("INSERT INTO usuarios (nombre, correo) VALUES ('$nombre', '$correo')")
                editTextNombre.text.clear()
                editTextCorreo.text.clear()
                textViewDatos.text = "Usuario agregado con éxito"
            } else {
                textViewDatos.text = "Por favor, completa todos los campos"
            }
        }

        // Acción del botón Mostrar
        buttonMostrar.setOnClickListener {
            val db = baseDatos.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM usuarios", null)

            val usuariosLista = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                    usuariosLista.append("Nombre: $nombre, Correo: $correo\n")
                } while (cursor.moveToNext())
            } else {
                usuariosLista.append("No hay usuarios registrados.")
            }
            cursor.close()

            textViewDatos.text = usuariosLista.toString()
        }
    }
}
