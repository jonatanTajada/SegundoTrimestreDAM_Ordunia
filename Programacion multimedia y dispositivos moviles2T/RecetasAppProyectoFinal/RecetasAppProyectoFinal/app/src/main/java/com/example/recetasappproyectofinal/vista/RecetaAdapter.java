package com.example.recetasappproyectofinal.vista;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recetasappproyectofinal.R;
import com.example.recetasappproyectofinal.modelo.Receta;

import java.util.List;

public class RecetaAdapter extends BaseAdapter {

    private final Context context;
    private final List<Receta> listaRecetas;

    public RecetaAdapter(Context context, List<Receta> listaRecetas) {
        this.context = context;
        this.listaRecetas = listaRecetas;
    }

    @Override
    public int getCount() {
        return listaRecetas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRecetas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receta, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); // Guardamos la referencia
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Receta receta = listaRecetas.get(position);

        // Configurar el título
        holder.tvTitulo.setText(receta.getTitulo());

        // Configurar la imagen con validación
        if (receta.tieneImagen() && receta.getImagenUri() != null) {
            Uri imageUri = Uri.parse(receta.getImagenUri());
            holder.ivImagen.setImageURI(imageUri);
        } else {
            holder.ivImagen.setImageResource(R.drawable.foto_predeterminada);
        }

        return convertView;
    }

    // ViewHolder para mejorar rendimiento
    private static class ViewHolder {
        final ImageView ivImagen;
        final TextView tvTitulo;

        ViewHolder(View view) {
            ivImagen = view.findViewById(R.id.iv_imagen_item);
            tvTitulo = view.findViewById(R.id.tv_titulo_item);
        }
    }
}
