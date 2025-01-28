package com.example.recetasappproyectofinal.vista;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends Fragment implements DialogCrearCategoria.OnCategoriaCreadaListener {

    private BaseDatos dbHelper;
    private List<Categoria> listaCategorias;
    private CategoriaAdapter adapter;
    private TextView tvNoCategorias;
    private String correoUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_categorias, container, false);

        dbHelper = new BaseDatos(requireContext());

        // Obtener correo del usuario desde SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        correoUsuario = prefs.getString("correoUsuario", null);

        if (correoUsuario == null) {
            Log.e("CategoriasFragment", "Error: No se encontró el usuario en SharedPreferences.");
            return view;
        }

        // Configurar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_categorias);
        tvNoCategorias = view.findViewById(R.id.tv_no_categorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        listaCategorias = new ArrayList<>();
        adapter = new CategoriaAdapter(requireContext(), listaCategorias, categoria -> {
            // Acción al hacer clic en una categoría (puedes implementar navegación aquí)
        });

        recyclerView.setAdapter(adapter);

        // Botón para agregar una nueva categoría
        Button btnAgregarCategoria = view.findViewById(R.id.btn_agregar_categoria);
        btnAgregarCategoria.setOnClickListener(v -> mostrarDialogoAgregarCategoria());

        // Cargar categorías existentes
        cargarCategorias();

        return view;
    }

    private void cargarCategorias() {
        listaCategorias.clear();
        int usuarioId = dbHelper.obtenerIdUsuarioPorCorreo(correoUsuario);

        if (usuarioId != -1) {
            listaCategorias.addAll(dbHelper.obtenerCategoriasComoLista(usuarioId));

            if (!listaCategorias.isEmpty()) {
                adapter.notifyItemRangeInserted(0, listaCategorias.size()); // Actualizar solo los nuevos elementos
            } else {
                tvNoCategorias.setVisibility(View.VISIBLE);
            }
        }
    }

    private void mostrarDialogoAgregarCategoria() {
        DialogCrearCategoria dialog = new DialogCrearCategoria();
        dialog.show(getParentFragmentManager(), "DialogCrearCategoria");
    }

    @Override
    public void onCategoriaCreada() {
        cargarCategorias(); // Recargar la lista de categorías después de crear una nueva
    }
}
