package com.example.internacionalizacion

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias al ImageView y TextView
        val imageViewFlag = findViewById<ImageView>(R.id.imageViewFlag)
        val textViewMessage = findViewById<TextView>(R.id.textViewMessage)

        // Obtener el idioma actual del dispositivo
        val currentLocale = Locale.getDefault().language

        // Cambiar la imagen de la bandera según el idioma
        when (currentLocale) {
            "es" -> imageViewFlag.setImageResource(R.drawable.banderaespana)
            "en" -> imageViewFlag.setImageResource(R.drawable.banderauk)
            "fr" -> imageViewFlag.setImageResource(R.drawable.banderafrancia)
            else -> {
                // Idioma por defecto: Español
                imageViewFlag.setImageResource(R.drawable.banderaespana)
            }
        }
    }
}
 