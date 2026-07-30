package bibliotecauniversitaria;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int stock;

    public Libro() {
    }

    public Libro(String titulo, String autor, String categoria, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stock = stock;
    }

    public Libro(int id, String titulo, String autor, String categoria, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        // Permite mostrar el libro de forma legible dentro de un JComboBox (lo usará el Integrante 3)
        return titulo;
    }
}
