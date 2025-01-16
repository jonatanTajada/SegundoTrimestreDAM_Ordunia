package com.example.gestiondestock.activities

import android.content.ContentValues
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestiondestock.R
import com.example.gestiondestock.database.BaseDatosHelper
import android.content.Intent


class StockActivity : AppCompatActivity() {

    private lateinit var baseDatosHelper: BaseDatosHelper

    override fun onCreate(estadoGuardado: Bundle?) {
        super.onCreate(estadoGuardado)
        setContentView(R.layout.activity_stock)

        // Inicializar la base de datos
        baseDatosHelper = BaseDatosHelper(this)

        // Referencias a los elementos del XML
        val campoCodigo = findViewById<EditText>(R.id.editTextCodigo)
        val campoNombre = findViewById<EditText>(R.id.editTextNombre)
        val campoPrecio = findViewById<EditText>(R.id.editTextPrecio)
        val botonCrearProducto = findViewById<Button>(R.id.buttonCrearProducto)
        val botonBuscarCodigo = findViewById<Button>(R.id.buttonBuscarCodigo)
        val botonBuscarNombre = findViewById<Button>(R.id.buttonBuscarNombre)
        val botonEliminarCodigo = findViewById<Button>(R.id.buttonEliminarCodigo)
        val botonActualizarProducto = findViewById<Button>(R.id.buttonActualizarProducto)
        val botonMostrarTodos = findViewById<Button>(R.id.buttonMostrarTodos)

        // Acción del botón Crear Producto
        botonCrearProducto.setOnClickListener {
            val codigo = campoCodigo.text.toString().toIntOrNull()
            val nombre = campoNombre.text.toString()
            val precio = campoPrecio.text.toString().toDoubleOrNull()

            if (codigo != null && precio != null && nombre.isNotBlank()) {
                val baseDatos = baseDatosHelper.writableDatabase

                // Comprobar si ya existe el código o el nombre
                val cursor = baseDatos.rawQuery(
                    "SELECT * FROM productos WHERE codigo = ? OR nombre = ?",
                    arrayOf(codigo.toString(), nombre)
                )

                if (cursor.count > 0) {
                    Toast.makeText(this, "Código o nombre ya existente", Toast.LENGTH_SHORT).show()
                    cursor.close()
                } else {
                    cursor.close()
                    baseDatos.execSQL(
                        "INSERT INTO productos (codigo, nombre, precio) VALUES (?, ?, ?)",
                        arrayOf(codigo, nombre, precio)
                    )
                    Toast.makeText(this, "Producto creado correctamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos(campoCodigo, campoNombre, campoPrecio)
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonCrearProducto)
        }

        // Acción del botón Buscar Producto por Código
        botonBuscarCodigo.setOnClickListener {
            val codigo = campoCodigo.text.toString().toIntOrNull()

            if (codigo != null) {
                val baseDatos = baseDatosHelper.readableDatabase
                val cursor = baseDatos.rawQuery(
                    "SELECT * FROM productos WHERE codigo = ?",
                    arrayOf(codigo.toString())
                )

                if (cursor.moveToFirst()) {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))

                    campoNombre.setText(nombre)
                    campoPrecio.setText(precio.toString())
                    Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show()
                }
                cursor.close()
            } else {
                Toast.makeText(this, "Por favor, introduce un código válido", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonBuscarCodigo)
        }

        // Botón Buscar por Nombre
        botonBuscarNombre.setOnClickListener {
            val nombre = campoNombre.text.toString()

            if (nombre.isNotBlank()) {
                val baseDatos = baseDatosHelper.readableDatabase
                val cursor = baseDatos.rawQuery(
                    "SELECT * FROM productos WHERE nombre LIKE ?",
                    arrayOf("%$nombre%")
                )

                if (cursor.moveToFirst()) {
                    val codigo = cursor.getInt(cursor.getColumnIndexOrThrow("codigo"))
                    val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))

                    campoCodigo.setText(codigo.toString())
                    campoPrecio.setText(precio.toString())
                    Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show()
                }
                cursor.close()
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonBuscarNombre)
        }

        // Botón Eliminar por Código
        botonEliminarCodigo.setOnClickListener {
            val codigo = campoCodigo.text.toString().toIntOrNull()

            if (codigo != null) {
                val baseDatos = baseDatosHelper.writableDatabase
                val filasAfectadas = baseDatos.delete("productos", "codigo = ?", arrayOf(codigo.toString()))

                if (filasAfectadas > 0) {
                    Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                    limpiarCampos(campoCodigo, campoNombre, campoPrecio)
                } else {
                    Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce un código válido", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonEliminarCodigo)
        }

        // Botón Actualizar Producto
        botonActualizarProducto.setOnClickListener {
            val codigo = campoCodigo.text.toString().toIntOrNull()
            val nombre = campoNombre.text.toString()
            val precio = campoPrecio.text.toString().toDoubleOrNull()

            if (codigo != null && precio != null && nombre.isNotBlank()) {
                val baseDatos = baseDatosHelper.writableDatabase
                val valores = ContentValues().apply {
                    put("nombre", nombre)
                    put("precio", precio)
                }

                val filasAfectadas = baseDatos.update("productos", valores, "codigo = ?", arrayOf(codigo.toString()))

                if (filasAfectadas > 0) {
                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                    limpiarCampos(campoCodigo, campoNombre, campoPrecio)
                } else {
                    Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonActualizarProducto)
        }

        // Botón Mostrar Todos
        botonMostrarTodos.setOnClickListener {
            val baseDatos = baseDatosHelper.readableDatabase
            val cursor = baseDatos.rawQuery("SELECT * FROM productos", null)

            if (cursor.count > 0) {
                val builder = StringBuilder()
                while (cursor.moveToNext()) {
                    val codigo = cursor.getInt(cursor.getColumnIndexOrThrow("codigo"))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))

                    builder.append("Código: $codigo\nNombre: $nombre\nPrecio: $precio\n\n")
                }
                cursor.close()

                val dialogo = android.app.AlertDialog.Builder(this)
                    .setTitle("Todos los Productos")
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .create()
                dialogo.show()
            } else {
                Toast.makeText(this, "No hay productos registrados", Toast.LENGTH_SHORT).show()
            }
            ocultarTeclado(botonMostrarTodos)
        }

        // Botón Salir
        val buttonSalir = findViewById<Button>(R.id.buttonSalir)

        buttonSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }

    }

    // Método para limpiar campos
    private fun limpiarCampos(vararg campos: EditText) {
        campos.forEach { it.text.clear() }
    }

    // Método para ocultar el teclado
    private fun ocultarTeclado(boton: Button) {
        val metodoEntrada = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        metodoEntrada.hideSoftInputFromWindow(boton.windowToken, 0)
    }
}
