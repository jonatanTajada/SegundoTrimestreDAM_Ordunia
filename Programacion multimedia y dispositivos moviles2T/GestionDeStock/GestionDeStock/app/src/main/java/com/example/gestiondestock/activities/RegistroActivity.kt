package com.example.gestiondestock.activities

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondestock.R
import com.example.gestiondestock.database.BaseDatosHelper

class RegistroActivity : AppCompatActivity() {

    private lateinit var baseDatosHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        baseDatosHelper = BaseDatosHelper(this)

        val campoUsuario = findViewById<EditText>(R.id.editTextUsuarioRegistro)
        val campoContrasena = findViewById<EditText>(R.id.editTextContrasenaRegistro)
        val botonRegistrar = findViewById<Button>(R.id.btnRegistrarUsuario)

        botonRegistrar.setOnClickListener {
            val usuario = campoUsuario.text.toString()
            val contrasena = campoContrasena.text.toString()

            if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                val db = baseDatosHelper.writableDatabase
                val cursor = db.rawQuery(
                    "SELECT * FROM usuarios WHERE nombre = ?",
                    arrayOf(usuario)
                )
                if (cursor.count > 0) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                    cursor.close()
                } else {
                    cursor.close()
                    val valores = ContentValues().apply {
                        put("nombre", usuario)
                        put("contrasena", contrasena)
                    }
                    db.insert("usuarios", null, valores)
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
