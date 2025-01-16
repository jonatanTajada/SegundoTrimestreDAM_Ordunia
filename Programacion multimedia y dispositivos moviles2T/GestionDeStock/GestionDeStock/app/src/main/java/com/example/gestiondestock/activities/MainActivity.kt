package com.example.gestiondestock.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondestock.R
import com.example.gestiondestock.database.BaseDatosHelper

class MainActivity : AppCompatActivity() {

    private lateinit var baseDatosHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseDatosHelper = BaseDatosHelper(this)

        val campoUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val campoContrasena = findViewById<EditText>(R.id.editTextPassword)
        val botonAcceder = findViewById<Button>(R.id.btnAcceder)
        val botonRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // Acción del botón Acceder
        botonAcceder.setOnClickListener {
            val usuario = campoUsuario.text.toString()
            val contrasena = campoContrasena.text.toString()

            if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                val db = baseDatosHelper.readableDatabase
                val cursor = db.rawQuery(
                    "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?",
                    arrayOf(usuario, contrasena)
                )
                if (cursor.moveToFirst()) {
                    Toast.makeText(this, "Bienvenido, $usuario", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, StockActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                cursor.close()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Acción del botón Registrar
        botonRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}
