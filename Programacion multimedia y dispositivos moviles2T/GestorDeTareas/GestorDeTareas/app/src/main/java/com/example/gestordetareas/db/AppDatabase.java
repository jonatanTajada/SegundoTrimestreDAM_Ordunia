import com.example.gestordetareas.dao.TareaDAO;

@Database(entities = {Usuario.class, Tarea.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instancia;

    public abstract UsuarioDAO usuarioDAO(); // ✅ Agregar este método
    public abstract TareaDAO tareaDAO();    // Asegurar que también esté presente

    public static AppDatabase getInstance(Context context) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "gestor_tareas.db"
                    ).build();
                }
            }
        }
        return instancia;
    }
}
