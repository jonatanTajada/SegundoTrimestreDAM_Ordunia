package com.example.uf2_sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextEmail: EditText
    private lateinit var buttonConfirm: Button
    private lateinit var textViewMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización
        sharedPreferences = getSharedPreferences("NewsletterPrefs", Context.MODE_PRIVATE)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonConfirm = findViewById(R.id.buttonConfirm)
        textViewMessage = findViewById(R.id.textViewMessage)

        // Cargar el último correo guardado
        val lastEmail = sharedPreferences.getString("lastEmail", "")
        editTextEmail.setText(lastEmail)

        // Evento para guardar el correo
        buttonConfirm.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                // Guardar el correo en SharedPreferences
                sharedPreferences.edit().putString("lastEmail", email).apply()

                // Mostrar un Toast de confirmación (arriba)
                Toast.makeText(this, "Correo guardado: $email", Toast.LENGTH_LONG).apply {
                    setGravity(android.view.Gravity.TOP, 0, 150)
                }.show()

                // Actualizar el mensaje en el TextView
                textViewMessage.text = "Correo guardado: $email"
            } else {
                // Avisar si el campo está vacío
                Toast.makeText(this, "Por favor, introduce un correo", Toast.LENGTH_SHORT).apply {
                    setGravity(android.view.Gravity.TOP, 0, 150)
                }.show()

                // Mostrar un mensaje en el TextView
                textViewMessage.text = "Por favor, introduce un correo"
            }
        }
    }
}
