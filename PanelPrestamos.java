package bibliotecauniversitaria;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PanelPrestamos extends JPanel {

    private final JComboBox<Libro> comboLibros;
    private final JComboBox<Usuario> comboUsuarios;
    private final JTextField txtFechaPrestamo;
    private final JTextField txtFechaDevolucion;
    private final JComboBox<String> comboEstado;
    private final JButton btnGuardar;
    private final JButton btnListar;
    private final JTable tablaPrestamos;
    private final DefaultTableModel modelo;

    private final LibroDAO libroDAO;
    private final UsuarioDAO usuarioDAO;
    private final PrestamoDAO prestamoDAO;

    public PanelPrestamos() {
        libroDAO = new LibroDAO();
        usuarioDAO = new UsuarioDAO();
        prestamoDAO = new PrestamoDAO();

        comboLibros = new JComboBox<>();
        comboUsuarios = new JComboBox<>();
        txtFechaPrestamo = new JTextField(12);
        txtFechaDevolucion = new JTextField(12);
        comboEstado = new JComboBox<>(new String[]{"prestado", "devuelto"});
        btnGuardar = new JButton("Guardar");
        btnListar = new JButton("Listar");

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Libro", "Usuario", "F. Préstamo", "F. Devolución", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaPrestamos = new JTable(modelo);

        organizarComponentes();
        registrarEventos();
        cargarCombos();
        listarPrestamos();
    }

    private void organizarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        panelFormulario.add(new JLabel("Libro:"));
        panelFormulario.add(comboLibros);
        panelFormulario.add(new JLabel("Usuario:"));
        panelFormulario.add(comboUsuarios);
        panelFormulario.add(new JLabel("Fecha préstamo (aaaa-mm-dd):"));
        panelFormulario.add(txtFechaPrestamo);
        panelFormulario.add(new JLabel("Fecha devolución (opcional):"));
        panelFormulario.add(txtFechaDevolucion);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(comboEstado);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnListar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
    }

    private void registrarEventos() {
        btnGuardar.addActionListener(e -> guardarPrestamo());
        btnListar.addActionListener(e -> listarPrestamos());
    }

    // Llena los combos con los libros y usuarios ya registrados por los otros dos módulos
    private void cargarCombos() {
        comboLibros.removeAllItems();
        List<Libro> libros = libroDAO.listar();
        for (Libro libro : libros) {
            comboLibros.addItem(libro);
        }

        comboUsuarios.removeAllItems();
        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            comboUsuarios.addItem(usuario);
        }
    }

    private void guardarPrestamo() {
        Libro libroSeleccionado = (Libro) comboLibros.getSelectedItem();
        Usuario usuarioSeleccionado = (Usuario) comboUsuarios.getSelectedItem();
        String fechaPrestamo = txtFechaPrestamo.getText().trim();
        String fechaDevolucion = txtFechaDevolucion.getText().trim();
        String estado = (String) comboEstado.getSelectedItem();

        if (libroSeleccionado == null || usuarioSeleccionado == null || fechaPrestamo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un libro, un usuario y escriba la fecha de préstamo.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Prestamo prestamo = new Prestamo(
                libroSeleccionado.getId(),
                usuarioSeleccionado.getId(),
                fechaPrestamo,
                fechaDevolucion,
                estado
        );

        boolean registrado = prestamoDAO.registrar(prestamo);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Préstamo registrado.");
            limpiarCampos();
            listarPrestamos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible registrar el préstamo. Revise el formato de fecha (aaaa-mm-dd).",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarPrestamos() {
        modelo.setRowCount(0);
        List<Prestamo> prestamos = prestamoDAO.listar();
        for (Prestamo prestamo : prestamos) {
            modelo.addRow(new Object[]{
                    prestamo.getId(),
                    prestamo.getTituloLibro(),
                    prestamo.getNombreUsuario(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    prestamo.getEstado()
            });
        }
    }

    private void limpiarCampos() {
        txtFechaPrestamo.setText("");
        txtFechaDevolucion.setText("");
        cargarCombos();
    }
}
