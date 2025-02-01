package com.tareas.pendientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tareas.pendientes.R;
import com.tareas.pendientes.model.Tarea;
import com.tareas.pendientes.view.DetalleTareaActivity;
import com.tareas.pendientes.view.EditarTareaActivity;
import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private final Context context;
    private final List<Tarea> listaTareas;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(Tarea tarea);
        void onEliminarClick(Tarea tarea);
    }

    public TareasAdapter(Context context, List<Tarea> listaTareas, OnItemClickListener listener) {
        this.context = context;
        this.listaTareas = listaTareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvFecha.setText(tarea.getFecha());

        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("IMAGEN", tarea.getImagenPath());  // ✅ USO DE getImagenPath()
            context.startActivity(intent);
        });

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("IMAGEN", tarea.getImagenPath());  // ✅ USO DE getImagenPath()
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(tarea));
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha;
        Button btnVerDetalles, btnEditar, btnEliminar;

        public TareaViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
