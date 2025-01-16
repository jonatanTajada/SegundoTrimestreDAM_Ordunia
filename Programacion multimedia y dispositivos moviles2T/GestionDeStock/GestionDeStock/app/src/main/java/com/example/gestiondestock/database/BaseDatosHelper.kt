package com.example.gestiondestock.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosHelper(contexto: Context) : SQLiteOpenHelper(contexto, "gestion_stock.db", null, 1) {

    override fun onCreate(baseDatos: SQLiteDatabase?) {
        // Crear tabla de usuarios
        val crearTablaUsuarios = """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                contrasena TEXT NOT NULL
            )
        """
        baseDatos?.execSQL(crearTablaUsuarios)

        // Crear tabla de productos
        val crearTablaProductos = """
            CREATE TABLE productos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo INTEGER NOT NULL UNIQUE,
                nombre TEXT NOT NULL UNIQUE,
                precio REAL NOT NULL
            )
        """
        baseDatos?.execSQL(crearTablaProductos)

        // Insertar un usuario por defecto
        val insertarUsuarioPorDefecto = """
            INSERT INTO usuarios (nombre, contrasena) VALUES ('admin', '1234')
        """
        baseDatos?.execSQL(insertarUsuarioPorDefecto)
    }

    override fun onUpgrade(baseDatos: SQLiteDatabase?, versionAntigua: Int, versionNueva: Int) {
        baseDatos?.execSQL("DROP TABLE IF EXISTS usuarios")
        baseDatos?.execSQL("DROP TABLE IF EXISTS productos")
        onCreate(baseDatos)
    }
}
