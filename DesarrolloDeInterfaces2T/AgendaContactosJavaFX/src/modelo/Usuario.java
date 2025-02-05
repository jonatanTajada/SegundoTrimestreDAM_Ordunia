package modelo;


public class Usuario {
    
    private int id;
    private String correo;
    private String password;

    /**
     * Constructor de la clase Usuario.
     */
    public Usuario(int id, String correo, String password) {
        this.id = id;
        this.correo = correo;
        this.password = password;
    }


    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

 
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getPassword() {
        return password;
    }

  
    public void setPassword(String password) {
        this.password = password;
    }
    
}
