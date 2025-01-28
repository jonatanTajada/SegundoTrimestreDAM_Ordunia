package com.example.recetasappproyectofinal.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.modelo.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private final Context context; // Variable inicializada en el constructor
    private final List<Categoria> listaCategorias;
    private final OnCategoriaClickListener listener;

    // Constructor para inicializar context, lista y listener
    public CategoriaAdapter(Context context, List<Categoria> listaCategorias, OnCategoriaClickListener listener) {
        this.context = context;
        this.listaCategorias = listaCategorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño para cada ítem de la lista
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);
        holder.tvNombreCategoria.setText(categoria.getNombre());

        // Puedes agregar más estilos o lógica aquí si es necesario
        holder.itemView.setOnClickListener(v -> listener.onCategoriaClick(categoria));
    }


    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    // Clase ViewHolder para administrar las vistas de cada ítem
    static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCategoria;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCategoria = itemView.findViewById(R.id.tv_nombre_categoria);
        }
    }

    // Interfaz para manejar clics en categorías
    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }
}
