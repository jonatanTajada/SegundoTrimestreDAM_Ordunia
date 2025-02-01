package com.tareas.pendientes.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tareas_pendientes.db";
    private static final int DATABASE_VERSION = 2; // Aumentado para aplicar cambios en onUpgrade

    // Tabla Usuarios
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_USUARIO_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Tabla Tareas
    public static final String TABLE_TAREAS = "tareas";
    public static final String COLUMN_TAREA_ID = "id";
    public static final String COLUMN_USUARIO_ID_FK = "usuario_id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_IMAGEN = "imagen"; // Se mantiene correctamente
    public static final String COLUMN_COMPLETADA = "completada";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de Usuarios
        String createUsuariosTable = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ");";

        // Crear tabla de Tareas
        String createTareasTable = "CREATE TABLE " + TABLE_TAREAS + " (" +
                COLUMN_TAREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_TITULO + " TEXT NOT NULL, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_FECHA + " TEXT, " +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_COMPLETADA + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY (" + COLUMN_USUARIO_ID_FK + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_USUARIO_ID + ")" +
                ");";

        db.execSQL(createUsuariosTable);
        db.execSQL(createTareasTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) { // Verifica si la versión es anterior para agregar la columna de imagen
            try {
                db.execSQL("ALTER TABLE " + TABLE_TAREAS + " ADD COLUMN " + COLUMN_IMAGEN + " TEXT;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 📌 MÉTODOS PARA USUARIOS

    // Insertar un usuario
    public long insertarUsuario(String nombre, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        return db.insert(TABLE_USUARIOS, null, values);
    }

    // Obtener un usuario por email
    public Cursor obtenerUsuarioPorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
    }

    // 📌 MÉTODOS PARA TAREAS

    // Insertar una tarea
    public long insertarTarea(int usuarioId, String titulo, String descripcion, String fecha, String imagenPath, boolean completada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_ID_FK, usuarioId);
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_IMAGEN, imagenPath);
        values.put(COLUMN_COMPLETADA, completada ? 1 : 0);

        return db.insert(TABLE_TAREAS, null, values);
    }

    // Obtener todas las tareas de un usuario
    public Cursor obtenerTareasPorUsuario(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TAREAS + " WHERE " + COLUMN_USUARIO_ID_FK + " = ?", new String[]{String.valueOf(usuarioId)});
    }

    // Editar una tarea
    public boolean actualizarTarea(int tareaId, String nuevoTitulo, String nuevaFecha, String nuevaDescripcion, String nuevaImagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, nuevoTitulo);
        values.put(COLUMN_FECHA, nuevaFecha);
        values.put(COLUMN_DESCRIPCION, nuevaDescripcion);
        values.put(COLUMN_IMAGEN, nuevaImagen);

        int filasActualizadas = db.update(TABLE_TAREAS, values, COLUMN_TAREA_ID + " = ?", new String[]{String.valueOf(tareaId)});
        return filasActualizadas > 0;
    }

    // Eliminar una tarea
    public void eliminarTarea(int tareaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAREAS, COLUMN_TAREA_ID + " = ?", new String[]{String.valueOf(tareaId)});
    }

    // Eliminar todas las tareas de un usuario
    public void eliminarTodasLasTareas(int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAREAS, COLUMN_USUARIO_ID_FK + " = ?", new String[]{String.valueOf(usuarioId)});
    }
}
