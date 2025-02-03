package com.tareas.pendientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tareas.pendientes.R;
import com.tareas.pendientes.controller.DatabaseHelper;
import com.tareas.pendientes.model.Tarea;
import com.tareas.pendientes.view.DetalleTareaActivity;
import com.tareas.pendientes.view.EditarTareaActivity;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private final Context context;
    private final List<Tarea> listaTareas;
    private final OnItemClickListener listener;
    private final DatabaseHelper dbHelper; // ✅ Base de datos para actualizar tareas

    // Interfaz para manejar eventos de los botones
    public interface OnItemClickListener {
        void onEditarClick(Tarea tarea);
        void onEliminarClick(Tarea tarea);
        void onMarcarCompletada(Tarea tarea);
    }

    // Constructor del adaptador
    public TareasAdapter(Context context, List<Tarea> listaTareas, OnItemClickListener listener) {
        this.context = context;
        this.listaTareas = listaTareas;
        this.listener = listener;
        this.dbHelper = new DatabaseHelper(context);
    }

    // Método para inflar el layout de cada elemento de la lista
    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    // Método para asignar valores a los elementos de la vista
    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvFecha.setText(tarea.getFecha());

        //  Cambia la apariencia si la tarea está completada
        if (tarea.isCompletada()) {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTitulo.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.btnCompletada.setText(context.getString(R.string.marcar_pendiente));
        } else {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTitulo.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.btnCompletada.setText(context.getString(R.string.completar));
        }

        //  Abrir pantalla de detalles de la tarea
        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("IMAGEN", tarea.getImagen());
            context.startActivity(intent);
        });

        //  Abrir pantalla de edición de la tarea
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarTareaActivity.class);
            intent.putExtra("ID_TAREA", tarea.getId());
            intent.putExtra("TITULO", tarea.getTitulo());
            intent.putExtra("FECHA", tarea.getFecha());
            intent.putExtra("DESCRIPCION", tarea.getDescripcion());
            intent.putExtra("IMAGEN", tarea.getImagen());
            context.startActivity(intent);
        });

        // Mostrar cuadro de diálogo para confirmar eliminación
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.confirmar_eliminar))
                    .setMessage(context.getString(R.string.mensaje_confirmar_eliminar))
                    .setPositiveButton(context.getString(R.string.si), (dialog, which) -> {
                        listener.onEliminarClick(tarea);
                    })
                    .setNegativeButton(context.getString(R.string.no), null)
                    .show();
        });

        //  Alternar entre tarea completada o pendiente
        holder.btnCompletada.setOnClickListener(v -> {
            tarea.setCompletada(!tarea.isCompletada());
            dbHelper.actualizarEstadoTarea(tarea.getId(), tarea.isCompletada());

            notifyItemChanged(holder.getAdapterPosition()); // Actualiza el estado en la interfaz
        });
    }

    // Método para obtener el número total de tareas
    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    // Clase interna para gestionar los elementos de la vista
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
