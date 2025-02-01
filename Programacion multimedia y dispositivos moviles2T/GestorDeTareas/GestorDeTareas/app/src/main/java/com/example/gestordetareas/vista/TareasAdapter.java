package com.example.gestordetareas.vista;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestordetareas.R;
import com.example.gestordetareas.modelo.Tarea;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private final ListaTareasActivity actividad;
    private final List<Tarea> listaTareas;
    private static final String TAG = "TareasAdapter"; // 🔍 Para depuración

    public TareasAdapter(ListaTareasActivity actividad, List<Tarea> listaTareas) {
        this.actividad = actividad;
        this.listaTareas = listaTareas;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return listaTareas.get(position).getId();
    }

    @Override
    @NonNull
    public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.tvTarea.setText(tarea.getTitulo());

        // 🔥 Botón para eliminar tarea (corrigiendo error en actividad)
        // 🔥 Botón para eliminar la tarea de la lista
        holder.btnEliminarTarea.setOnClickListener(view -> {
            if (position >= 0 && position < listaTareas.size()) {
                actividad.eliminarTarea(position); // 🔹 Ahora este método existe en ListaTareasActivity
                notifyItemRemoved(position);
                Log.d(TAG, "Tarea eliminada en posición: " + position);
            }
        });


        // ✏️ Botón para actualizar tarea
        holder.btnActualizarTarea.setOnClickListener(view -> {
            Intent intent = new Intent(actividad, AgregarTareaActivity.class);
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("IMAGEN_URI", tarea.getImagenUri() != null ? tarea.getImagenUri().toString() : "");
            intent.putExtra("TAREA_ID", tarea.getId());

            Log.d(TAG, "Abriendo AgregarTareaActivity para actualizar tarea: " + tarea.getTitulo());
            actividad.startActivity(intent);
        });

        // 📄 Botón para ver detalles
        holder.btnVerDetalles.setOnClickListener(view -> {
            Intent intent = new Intent(actividad, DetalleTareaActivity.class);
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("IMAGEN_URI", tarea.getImagenUri() != null ? tarea.getImagenUri().toString() : "");

            Log.d(TAG, "Abriendo DetalleTareaActivity para tarea: " + tarea.getTitulo());
            actividad.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTarea;
        Button btnEliminarTarea, btnActualizarTarea, btnVerDetalles;

        public TareaViewHolder(View itemView) {
            super(itemView);
            tvTarea = itemView.findViewById(R.id.tv_tarea);
            btnEliminarTarea = itemView.findViewById(R.id.btn_eliminar_tarea);
            btnActualizarTarea = itemView.findViewById(R.id.btn_actualizar_tarea);
            btnVerDetalles = itemView.findViewById(R.id.btn_ver_detalles);
        }
    }
}
