package bibliotecauniversitaria;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class VentanaBiblioteca extends JFrame {

    public VentanaBiblioteca() {
        configurarVentana();

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Libros", new PanelLibros());       // módulo del Integrante 1
        pestañas.addTab("Usuarios", new PanelUsuarios());   // módulo del Integrante 2
        pestañas.addTab("Préstamos", new PanelPrestamos()); // módulo del Integrante 3

        add(pestañas);
    }

    private void configurarVentana() {
        setTitle("Sistema de Biblioteca Universitaria");
        setSize(820, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
