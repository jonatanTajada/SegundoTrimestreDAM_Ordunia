package com.example.demomultimediavideo

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el VideoView
        videoView = findViewById(R.id.videoView)

        // Configurar el video desde la carpeta raw
        val videoUri = Uri.parse("android.resource://${packageName}/raw/videoprueba")
        videoView.setVideoURI(videoUri)

        // Referencias a los botones
        val buttonPlay = findViewById<Button>(R.id.buttonPlay)
        val buttonPause = findViewById<Button>(R.id.buttonPause)
        val buttonStop = findViewById<Button>(R.id.buttonStop)

        // Configurar el botón Play
        buttonPlay.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.start()
            }
        }

        // Configurar el botón Pause
        buttonPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }

        // Configurar el botón Stop
        buttonStop.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
                videoView.resume() // Reiniciar el video
            }
        }
    }
}
