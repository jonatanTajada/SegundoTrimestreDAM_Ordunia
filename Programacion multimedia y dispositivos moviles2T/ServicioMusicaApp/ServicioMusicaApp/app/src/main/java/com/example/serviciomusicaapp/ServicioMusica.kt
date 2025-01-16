package com.example.serviciomusicaapp


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class ServicioMusica : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        // Inicializamos el MediaPlayer con la canción de la carpeta raw
        mediaPlayer = MediaPlayer.create(this, R.raw.musica)
        mediaPlayer.isLooping = true // Reproducir en bucle
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.start() // Inicia la música
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release() // Liberamos recursos
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // No vinculamos el servicio
    }
}
