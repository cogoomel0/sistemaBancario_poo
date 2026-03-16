import javax.swing.SwingUtilities; //Funções auxiliares do Swing

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaBancario sistema = new SistemaBancario();
            sistema.setVisible(true);
        });
    }
}
