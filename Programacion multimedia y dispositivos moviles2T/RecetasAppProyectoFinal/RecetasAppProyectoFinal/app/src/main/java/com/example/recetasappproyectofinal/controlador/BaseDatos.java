package com.example.recetasappproyectofinal.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recetasappproyectofinal.modelo.Categoria;
import com.example.recetasappproyectofinal.modelo.Receta;
import com.example.recetasappproyectofinal.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RecetasApp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "BaseDatos";

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, correo TEXT NOT NULL UNIQUE, contrasena TEXT NOT NULL)");
        db.execSQL("CREATE TABLE categorias (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recetas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "ingredientes TEXT NOT NULL, " +
                "instrucciones TEXT NOT NULL, " +
                "imagen TEXT, " +
                "categoria_id INTEGER NOT NULL, " +
                "FOREIGN KEY(categoria_id) REFERENCES categorias(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS recetas");
        onCreate(db);
    }

    // =========================== MÉTODOS CRUD USUARIOS ===========================

    public long insertarUsuario(String nombre, String correo, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("contrasena", contrasena);
        long resultado = db.insert("usuarios", null, values);
        db.close();
        return resultado;
    }

    public boolean verificarUsuario(String correo, String contrasena) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT 1 FROM usuarios WHERE correo = ? AND contrasena = ?", new String[]{correo, contrasena})) {
            return cursor.moveToFirst();
        }
    }

    public int obtenerIdUsuarioPorCorreo(String correo) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT id FROM usuarios WHERE correo = ?", new String[]{correo})) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
            return -1;
        }
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE correo = ?", new String[]{correo})) {
            if (cursor.moveToFirst()) {
                return new Usuario(
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        correo,
                        cursor.getString(cursor.getColumnIndexOrThrow("contrasena"))
                );
            }
            return null;
        }
    }

    public int actualizarUsuario(String correo, String nuevoNombre, String nuevaContrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoNombre);
        valores.put("contrasena", nuevaContrasena);
        int filasActualizadas = db.update("usuarios", valores, "correo = ?", new String[]{correo});
        db.close();
        return filasActualizadas;
    }

    // =========================== MÉTODOS CRUD CATEGORÍAS ===========================

    public long insertarCategoria(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        long resultado = db.insert("categorias", null, valores);
        db.close();
        return resultado;
    }

    public List<Categoria> obtenerCategorias() {
        List<Categoria> lista = new ArrayList<>();
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM categorias", null)) {
            while (cursor.moveToNext()) {
                lista.add(new Categoria(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                ));
            }
        }
        return lista;
    }

    public int obtenerIdCategoria(String nombreCategoria) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT id FROM categorias WHERE nombre = ?", new String[]{nombreCategoria})) {
            return cursor.moveToFirst() ? cursor.getInt(cursor.getColumnIndexOrThrow("id")) : -1;
        }
    }

    // =========================== MÉTODOS CRUD RECETAS ===========================

    public long insertarReceta(Receta receta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", receta.getTitulo());
        valores.put("ingredientes", receta.getIngredientes());
        valores.put("instrucciones", receta.getInstrucciones());
        valores.put("imagen", receta.getImagenUri());
        valores.put("categoria_id", receta.getCategoriaId());
        long resultado = db.insert("recetas", null, valores);
        db.close();
        return resultado;
    }

    public List<Receta> obtenerRecetasPorCategoria(int categoriaId) {
        List<Receta> listaRecetas = new ArrayList<>();
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM recetas WHERE categoria_id = ?", new String[]{String.valueOf(categoriaId)})) {
            while (cursor.moveToNext()) {
                listaRecetas.add(new Receta(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ingredientes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instrucciones")),
                        cursor.getString(cursor.getColumnIndexOrThrow("imagen")),
                        categoriaId
                ));
            }
        }
        return listaRecetas;
    }

    public Receta obtenerRecetaPorId(int recetaId) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM recetas WHERE id = ?", new String[]{String.valueOf(recetaId)})) {
            if (cursor.moveToFirst()) {
                return new Receta(
                        recetaId,
                        cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ingredientes")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instrucciones")),
                        cursor.getString(cursor.getColumnIndexOrThrow("imagen")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("categoria_id"))
                );
            }
            return null;
        }
    }

    public int actualizarReceta(int recetaId, String nuevoTitulo, String nuevosIngredientes, String nuevasInstrucciones, String nuevaImagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", nuevoTitulo);
        valores.put("ingredientes", nuevosIngredientes);
        valores.put("instrucciones", nuevasInstrucciones);
        valores.put("imagen", nuevaImagen);
        int filasActualizadas = db.update("recetas", valores, "id = ?", new String[]{String.valueOf(recetaId)});
        db.close();
        return filasActualizadas;
    }

    public int eliminarReceta(int recetaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasEliminadas = db.delete("recetas", "id = ?", new String[]{String.valueOf(recetaId)});
        db.close();
        return filasEliminadas;
    }
}
