package com.tareas.pendientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        void onMarcarCompletada(Tarea tarea);
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

        // 🎨 CAMBIO VISUAL SEGÚN ESTADO
        if (tarea.isCompletada()) {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTitulo.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.btnCompletada.setText(context.getString(R.string.marcar_pendiente));
        } else {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTitulo.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.btnCompletada.setText(context.getString(R.string.completar));
        }


        // 👁 Ver detalles
        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("IMAGEN", tarea.getImagen());
            context.startActivity(intent);
        });

        // ✏️ Editar tarea
        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(tarea));

        // 🗑 Eliminar tarea
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(tarea));

        // ✅/🔄 Marcar como completada o pendiente
        holder.btnCompletada.setOnClickListener(v -> {
            tarea.setCompletada(!tarea.isCompletada()); // Cambia el estado
            listener.onMarcarCompletada(tarea);
            notifyItemChanged(position); // 🔄 ACTUALIZA SOLO ESTE ÍTEM
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha;
        Button btnVerDetalles, btnEditar, btnEliminar, btnCompletada;

        public TareaViewHolder(View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnCompletada = itemView.findViewById(R.id.btnCompletada);
        }
    }
}
