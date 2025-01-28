package com.example.recetasappproyectofinal.vista;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.controlador.BaseDatos;
import com.example.recetasappproyectofinal.modelo.Receta;

import java.util.ArrayList;
import java.util.List;

public class RecetasFragment extends Fragment {

    private List<Receta> listaRecetas;
    private ArrayAdapter<String> adapter;
    private BaseDatos dbHelper;
    private int categoriaId; // ID de la categoría seleccionada

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recetas, container, false);


        dbHelper = new BaseDatos(requireContext());
        listaRecetas = new ArrayList<>();
        ListView lvRecetas = view.findViewById(R.id.lv_recetas);
        Button btnCrearReceta = view.findViewById(R.id.btn_crear_receta);

        // Obtener el ID de la categoría seleccionada (pasado desde CategoriasFragment)
        if (getArguments() != null) {
            categoriaId = getArguments().getInt("categoriaId", -1);
        }

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        lvRecetas.setAdapter(adapter);

        // Cargar recetas de la base de datos
        cargarRecetas();

        // Acción para ver detalles de una receta
        lvRecetas.setOnItemClickListener((parent, view1, position, id) -> {
            Receta recetaSeleccionada = listaRecetas.get(position);
            DetallesRecetaFragment detallesRecetaFragment = new DetallesRecetaFragment();

            Bundle args = new Bundle();
            args.putString("titulo", recetaSeleccionada.getTitulo());
            args.putString("ingredientes", recetaSeleccionada.getIngredientes());
            args.putString("instrucciones", recetaSeleccionada.getInstrucciones());
            args.putString("imagen", recetaSeleccionada.getImagenUri());
            detallesRecetaFragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detallesRecetaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Acción para crear una nueva receta
        btnCrearReceta.setOnClickListener(v -> {
            CrearRecetaFragment crearRecetaFragment = new CrearRecetaFragment();
            Bundle args = new Bundle();
            args.putInt("categoriaId", categoriaId); // Pasar la categoría seleccionada
            crearRecetaFragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, crearRecetaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void cargarRecetas() {
        listaRecetas.clear();

        if (categoriaId == -1) {
            Toast.makeText(requireContext(), "Error: Categoría no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = requireContext().getSharedPreferences("SesionUsuario", requireContext().MODE_PRIVATE);
        int usuarioId = dbHelper.obtenerIdUsuarioPorCorreo(prefs.getString("correoUsuario", null));

        if (usuarioId == -1) {
            Toast.makeText(requireContext(), "Error: Usuario no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        listaRecetas = dbHelper.obtenerRecetasPorCategoria(categoriaId);
        List<String> nombresRecetas = new ArrayList<>();

        for (Receta receta : listaRecetas) {
            nombresRecetas.add(receta.getTitulo());
        }

        adapter.clear();
        adapter.addAll(nombresRecetas);
        adapter.notifyDataSetChanged();
    }
}
