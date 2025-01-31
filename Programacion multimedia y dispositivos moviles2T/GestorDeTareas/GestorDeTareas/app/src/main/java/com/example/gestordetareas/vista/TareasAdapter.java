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

    private final List<Tarea> listaTareas;
    private final ListaTareasActivity actividad;
    private static final String TAG = "TareasAdapter"; // Para depuración

    /**
     * Constructor del adaptador
     * @param actividad Actividad donde se muestra la lista de tareas
     * @param listaTareas Lista de tareas a mostrar en el RecyclerView
     */
    public TareasAdapter(ListaTareasActivity actividad, List<Tarea> listaTareas) {
        this.actividad = actividad;
        this.listaTareas = listaTareas;
        setHasStableIds(true); // Mejora el rendimiento del RecyclerView
    }

    /**
     * Asigna un identificador único basado en el ID de la tarea.
     * @param position Posición del ítem en la lista.
     * @return ID de la tarea.
     */
    @Override
    public long getItemId(int position) {
        return listaTareas.get(position).getId();
    }

    /**
     * Infla el layout de cada ítem de la lista y crea un ViewHolder.
     */
    @Override
    @NonNull
    public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TareaViewHolder(view);
    }

    /**
     * Asigna los datos de la tarea a cada vista dentro del ViewHolder.
     * Configura los eventos de los botones para eliminar, actualizar y ver detalles.
     */
    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);

        // Establecer el título de la tarea en la tarjeta
        holder.tvTarea.setText(tarea.getTitulo());

        // 🔥 Botón para eliminar la tarea de la lista
        holder.btnEliminarTarea.setOnClickListener(view -> {
            if (position >= 0 && position < listaTareas.size()) {
                actividad.eliminarTarea(position);
                notifyItemRemoved(position);
                Log.d(TAG, "Tarea eliminada en posición: " + position);
            }
        });

        // ✏️ Botón para actualizar la tarea (abre AgregarTareaActivity con los datos prellenados)
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

        // 📄 Botón para ver detalles de la tarea (abre DetalleTareaActivity)
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

    /**
     * Devuelve el número total de elementos en la lista de tareas.
     * @return Cantidad de tareas en la lista.
     */
    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    /**
     * ViewHolder que almacena las vistas de cada ítem de la lista.
     */
    public static class TareaViewHolder extends RecyclerView.ViewHolder {

        TextView tvTarea;
        Button btnEliminarTarea;
        Button btnActualizarTarea;
        Button btnVerDetalles;

        /**
         * Constructor del ViewHolder, enlaza las vistas de cada ítem.
         * @param itemView Vista del ítem inflada en onCreateViewHolder.
         */
        public TareaViewHolder(View itemView) {
            super(itemView);
            tvTarea = itemView.findViewById(R.id.tv_tarea);
            btnEliminarTarea = itemView.findViewById(R.id.btn_eliminar_tarea);
            btnActualizarTarea = itemView.findViewById(R.id.btn_actualizar_tarea);
            btnVerDetalles = itemView.findViewById(R.id.btn_ver_detalles);
        }
    }
}
