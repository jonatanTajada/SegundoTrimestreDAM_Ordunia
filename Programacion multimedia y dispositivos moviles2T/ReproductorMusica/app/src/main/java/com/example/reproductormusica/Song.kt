package com.example.reproductormusica

class Song(
    val titulo: String,
    val artista: String,
    val anioPublicacion: Int,
    var reproducciones: Int
) {
    // Propiedad calculada para determinar si es popular
    val esPopular: Boolean
        get() = reproducciones >= 1000

    // Método para imprimir la descripción de la canción
    fun descripcion(): String {
        return "\"$titulo\", interpretada por $artista, se lanzó en $anioPublicacion."
    }
}
