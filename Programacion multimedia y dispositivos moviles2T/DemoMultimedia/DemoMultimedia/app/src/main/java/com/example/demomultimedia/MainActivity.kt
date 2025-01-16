package com.example.demomultimedia

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar MediaPlayer con el archivo de audio en res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.audioambiental)

        // Referencias a los botones
        val buttonPlay = findViewById<Button>(R.id.buttonPlay)
        val buttonPause = findViewById<Button>(R.id.buttonPause)
        val buttonStop = findViewById<Button>(R.id.buttonStop)

        // Configurar el botón Play
        buttonPlay.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }

        // Configurar el botón Pause
        buttonPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }

        // Configurar el botón Stop
        buttonStop.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare() // Reinicia el MediaPlayer para reproducir de nuevo
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos del MediaPlayer al cerrar la actividad
        mediaPlayer.release()
    }
}
