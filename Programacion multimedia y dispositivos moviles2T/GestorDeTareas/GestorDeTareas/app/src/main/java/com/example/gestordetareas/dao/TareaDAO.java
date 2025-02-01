package com.example.gestordetareas.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.gestordetareas.modelo.Tarea;

import java.util.List;

@Dao
public interface TareaDAO {

    @Insert
    long insertarTarea(Tarea tarea);

    @Query("SELECT * FROM tareas ORDER BY id DESC")
    List<Tarea> obtenerTodasLasTareas();

    @Delete
    void eliminarTarea(Tarea tarea);

    @Update
    void actualizarTarea(Tarea tarea);
}
