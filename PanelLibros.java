package bibliotecauniversitaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PanelLibros extends JPanel {

    private final JTextField txtTitulo;
    private final JTextField txtAutor;
    private final JTextField txtCategoria;
    private final JTextField txtStock;
    private final JButton btnGuardar;
    private final JButton btnListar;
    private final JTable tablaLibros;
    private final DefaultTableModel modelo;
    private final LibroDAO libroDAO;

    public PanelLibros() {
        libroDAO = new LibroDAO();

        txtTitulo = new JTextField(18);
        txtAutor = new JTextField(18);
        txtCategoria = new JTextField(18);
        txtStock = new JTextField(18);
        btnGuardar = new JButton("Guardar");
        btnListar = new JButton("Listar");

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Categoría", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaLibros = new JTable(modelo);

        organizarComponentes();
        registrarEventos();
        listarLibros();
    }

    private void organizarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnListar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);
    }

    private void registrarEventos() {
        btnGuardar.addActionListener(e -> guardarLibro());
        btnListar.addActionListener(e -> listarLibros());
    }

    private void guardarLibro() {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String textoStock = txtStock.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || textoStock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(textoStock);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Libro libro = new Libro(titulo, autor, categoria, stock);
        boolean registrado = libroDAO.registrar(libro);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Libro registrado.");
            limpiarCampos();
            listarLibros();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible registrar el libro.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarLibros() {
        modelo.setRowCount(0);
        List<Libro> libros = libroDAO.listar();
        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                    libro.getId(), libro.getTitulo(), libro.getAutor(),
                    libro.getCategoria(), libro.getStock()
            });
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtStock.setText("");
        txtTitulo.requestFocus();
    }
}
