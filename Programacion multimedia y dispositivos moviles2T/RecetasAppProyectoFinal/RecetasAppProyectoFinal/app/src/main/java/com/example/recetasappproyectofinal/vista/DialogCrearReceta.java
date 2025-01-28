package com.example.recetasappproyectofinal.vista;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.recetasappproyectofinal.R;

public class DialogCrearReceta extends DialogFragment {

    private RecetaDialogListener listener;

    public interface RecetaDialogListener {
        void onGuardarReceta(String titulo, String ingredientes, String instrucciones, String categoria);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecetaDialogListener) {
            listener = (RecetaDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " debe implementar RecetaDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_crear_receta, null);

        EditText etTitulo = view.findViewById(R.id.et_titulo_receta);
        EditText etIngredientes = view.findViewById(R.id.et_ingredientes);
        EditText etInstrucciones = view.findViewById(R.id.et_instrucciones);
        Spinner spCategorias = view.findViewById(R.id.sp_categorias);
        Button btnGuardar = view.findViewById(R.id.btn_guardar_receta);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);

        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String ingredientes = etIngredientes.getText().toString().trim();
            String instrucciones = etInstrucciones.getText().toString().trim();
            String categoria = spCategorias.getSelectedItem().toString();

            if (!titulo.isEmpty() && !ingredientes.isEmpty() && !instrucciones.isEmpty()) {
                listener.onGuardarReceta(titulo, ingredientes, instrucciones, categoria);
                dismiss();
            }
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }
}
