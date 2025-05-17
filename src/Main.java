import javax.swing.SwingUtilities;

import ui.CalculatorUI;

public class Main {
    public static void main(String[] args) {
        // Startet die GUI
        SwingUtilities.invokeLater(() -> new CalculatorUI());
    }
}