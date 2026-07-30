package bibliotecauniversitaria;

import javax.swing.SwingUtilities;

public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaBiblioteca ventana = new VentanaBiblioteca();
            ventana.setVisible(true);
        });
    }
}
