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

public class PanelUsuarios extends JPanel {

    private final JTextField txtNombre;
    private final JTextField txtCorreo;
    private final JTextField txtCarrera;
    private final JButton btnGuardar;
    private final JButton btnListar;
    private final JTable tablaUsuarios;
    private final DefaultTableModel modelo;
    private final UsuarioDAO usuarioDAO;

    public PanelUsuarios() {
        usuarioDAO = new UsuarioDAO();

        txtNombre = new JTextField(18);
        txtCorreo = new JTextField(18);
        txtCarrera = new JTextField(18);
        btnGuardar = new JButton("Guardar");
        btnListar = new JButton("Listar");

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Carrera"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modelo);

        organizarComponentes();
        registrarEventos();
        listarUsuarios();
    }

    private void organizarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Carrera:"));
        panelFormulario.add(txtCarrera);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnListar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
    }

    private void registrarEventos() {
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnListar.addActionListener(e -> listarUsuarios());
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String carrera = txtCarrera.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || carrera.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario(nombre, correo, carrera);
        boolean registrado = usuarioDAO.registrar(usuario);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Usuario registrado.");
            limpiarCampos();
            listarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "No fue posible registrar el usuario.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            modelo.addRow(new Object[]{
                    usuario.getId(), usuario.getNombre(),
                    usuario.getCorreo(), usuario.getCarrera()
            });
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtCarrera.setText("");
        txtNombre.requestFocus();
    }
}
