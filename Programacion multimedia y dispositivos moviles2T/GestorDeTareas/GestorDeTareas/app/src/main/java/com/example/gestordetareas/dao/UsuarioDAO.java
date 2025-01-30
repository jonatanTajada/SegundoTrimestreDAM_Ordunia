package com.example.gestordetareas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gestordetareas.db.BaseDatos;
import com.example.gestordetareas.modelo.Usuario;
import com.example.gestordetareas.utils.EncriptacionUtils;

public class UsuarioDAO {

    private static final String TAG = "UsuarioDAO";
    private final BaseDatos dbHelper;

    public UsuarioDAO(Context context) {
        this.dbHelper = new BaseDatos(context);
    }

    // Método para registrar un usuario
    public boolean registrarUsuario(String nombre, String correo, String password, String telefono, String ciudad) {
        boolean exito = false;

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("correo", correo);
            values.put("password", EncriptacionUtils.encriptar(password)); // Encripta la contraseña
            values.put("telefono", telefono);
            values.put("ciudad", ciudad);

            long resultado = db.insert("usuarios", null, values);
            exito = resultado != -1;

            if (exito) {
                Log.d("UsuarioDAO", "Usuario registrado correctamente: " + correo);
            } else {
                Log.e("UsuarioDAO", "Error al registrar usuario: " + correo);
            }
        } catch (SQLException e) {
            Log.e("UsuarioDAO", "Error en SQLite", e);
        }

        return exito;
    }


    // Método para verificar el login
    public boolean verificarLogin(String correo, String password) {
        boolean valido = false;

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT password FROM usuarios WHERE correo = ?", new String[]{correo})) {

            if (cursor.moveToFirst()) {
                String passwordEncriptada = cursor.getString(0);
                String passwordIngresada = EncriptacionUtils.encriptar(password);

                Log.d("UsuarioDAO", "Correo ingresado: " + correo);
                Log.d("UsuarioDAO", "Password ingresada (encriptada): " + passwordIngresada);
                Log.d("UsuarioDAO", "Password almacenada: " + passwordEncriptada);

                valido = passwordEncriptada.equals(passwordIngresada); // Compara contraseñas encriptadas

                if (valido) {
                    Log.d("UsuarioDAO", "Inicio de sesión exitoso para: " + correo);
                } else {
                    Log.w("UsuarioDAO", "Contraseña incorrecta para: " + correo);
                }
            } else {
                Log.w("UsuarioDAO", "Usuario no encontrado: " + correo);
            }
        } catch (SQLException e) {
            Log.e("UsuarioDAO", "Error al verificar login", e);
        }

        return valido;
    }





    // Método para obtener un usuario por correo
    public Usuario obtenerUsuario(String correo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuario = null;

        try (Cursor cursor = db.rawQuery("SELECT nombre, correo, password, telefono, ciudad FROM usuarios WHERE correo=?", new String[]{correo})) {
            if (cursor.moveToFirst()) {
                usuario = new Usuario(
                        cursor.getString(0),  // nombre
                        cursor.getString(1),  // correo
                        cursor.getString(2),  // password
                        cursor.getString(3),  // telefono
                        cursor.getString(4)   // ciudad
                );
            }
        } catch (SQLException e) {
            Log.e("UsuarioDAO", "Error al obtener usuario", e);
        } finally {
            db.close();
        }

        return usuario;
    }

}
