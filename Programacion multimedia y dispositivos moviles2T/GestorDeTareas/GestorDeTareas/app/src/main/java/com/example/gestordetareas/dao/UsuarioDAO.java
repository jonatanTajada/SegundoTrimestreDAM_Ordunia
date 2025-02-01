import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String correo;
    private String password;
    private String telefono;
    private String ciudad;

    // Constructor vacío necesario para Room
    public Usuario() {}

    public Usuario(String nombre, String correo, String password, String telefono, String ciudad) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    // Getters y Setters...
}
