package com.example.gestordetareas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "gestor_tareas.db";
    private static final int VERSION_BD = 1;

    public BaseDatos(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "telefono TEXT, " +
                "ciudad TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS tareas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "descripcion TEXT, " +
                "fecha TEXT, " +
                "imagenUri TEXT, " +
                "usuario_id INTEGER, " +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS tareas");
        onCreate(db);
    }
}
