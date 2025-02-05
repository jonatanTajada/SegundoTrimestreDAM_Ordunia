package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Objects;

public class Contacto {

    private final StringProperty nombre;
    private final StringProperty correo;
    private final StringProperty telefono;
    private final StringProperty localidad;
    private String imagen;
    private String sitioWeb;
    private int id;

    /**
     * Constructor de la clase Contacto.
     */
    public Contacto(int id, String nombre, String correo, String telefono, String localidad, String imagen, String sitioWeb) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.correo = new SimpleStringProperty(correo);
        this.telefono = new SimpleStringProperty(telefono);
        this.localidad = new SimpleStringProperty(validarLocalidad(localidad));
        this.imagen = imagen;
        this.sitioWeb = sitioWeb;
    }

  
    //GETTERS Y SETTERS
    public StringProperty localidadProperty() {
        return localidad;
    }

 
    public String getLocalidad() {
        return localidad.get();
    }

    /**
     * Establece la localidad del contacto con validación.
     * 
     * @param localidad Nueva localidad.
     * @throws IllegalArgumentException Si la localidad no es válida.
     */
    public void setLocalidad(String localidad) {
        if (validarLocalidad(localidad) == null) {
            throw new IllegalArgumentException("La localidad ingresada no es válida.");
        }
        this.localidad.set(localidad);
    }

    /**
     * Valida que la localidad ingresada sea válida.
     * 
     * @param localidad Localidad a validar.
     * @return La localidad si es válida o null si no lo es.
     */
    private String validarLocalidad(String localidad) {
        if (localidad == null || localidad.trim().isEmpty()) {
            return null;
        }
        if (!localidad.matches("^[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]+$")) {
            return null;
        }
        return localidad;
    }

    /**
     * Obtiene la propiedad de nombre.
     * 
     * @return Propiedad de nombre.
     */
    public StringProperty nombreProperty() {
        return nombre;
    }

 
    public String getNombre() {
        return nombre.get();
    }

 
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

   
    public StringProperty correoProperty() {
        return correo;
    }

  
    public String getCorreo() {
        return correo.get();
    }

  
    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

  
    public StringProperty telefonoProperty() {
        return telefono;
    }

   
    public String getTelefono() {
        return telefono.get();
    }

  
    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

   
    public String getImagen() {
        return imagen;
    }

   
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getSitioWeb() {
        return sitioWeb;
    }

    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

   
    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", localidad='" + getLocalidad() + '\'' +
                ", imagen='" + imagen + '\'' +
                ", sitioWeb='" + sitioWeb + '\'' +
                '}';
    }

    /**
     * Compara dos objetos Contacto para determinar si son iguales.
     * 
     * @param obj Objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contacto contacto = (Contacto) obj;
        return id == contacto.id &&
                Objects.equals(getNombre(), contacto.getNombre()) &&
                Objects.equals(getCorreo(), contacto.getCorreo()) &&
                Objects.equals(getTelefono(), contacto.getTelefono()) &&
                Objects.equals(getLocalidad(), contacto.getLocalidad()) &&
                Objects.equals(imagen, contacto.imagen) &&
                Objects.equals(sitioWeb, contacto.sitioWeb);
    }

    /**
     * Genera un código hash único para cada contacto.
     * 
     * @return Código hash del contacto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, getNombre(), getCorreo(), getTelefono(), getLocalidad(), imagen, sitioWeb);
    }
}
