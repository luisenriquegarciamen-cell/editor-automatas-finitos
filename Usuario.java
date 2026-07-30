package bibliotecauniversitaria;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String carrera;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String carrera) {
        this.nombre = nombre;
        this.correo = correo;
        this.carrera = carrera;
    }

    public Usuario(int id, String nombre, String correo, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        // Permite mostrar el usuario de forma legible dentro de un JComboBox (lo usará el Integrante 3)
        return nombre;
    }
}
