package com.example.ejerciciodatepicker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al EditText
        val editTextDate = findViewById<EditText>(R.id.editTextDate)

        // Configurar el evento onClick para mostrar el DatePickerDialog
        editTextDate.setOnClickListener {
            showDatePickerDialog(editTextDate)
        }
    }

    // Método para mostrar el DatePickerDialog
    private fun showDatePickerDialog(editText: EditText) {
        // Obtén la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crear y mostrar el DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Formatear la fecha seleccionada (YYYY-MM-DD)
                val formattedDate = "$selectedYear-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDay)}"
                // Mostrar la fecha seleccionada en el EditText
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
