package com.example.gestordetareas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder> {

    private List<String> tareas;

    public TareasAdapter(List<String> tareas) {
        this.tareas = tareas;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        holder.textoTarea.setText(tareas.get(position));
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView textoTarea;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            textoTarea = itemView.findViewById(R.id.texto_tarea);
        }
    }
}
