package ui;

import logic.CalculatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorUI extends JFrame {

    private static final Font DISPLAY_FONT = new Font("Arial", Font.BOLD, 32);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 18);

    private final JTextField display;
    private final CalculatorLogic logic;

    public CalculatorUI() {
        super("Taschenrechner");

        logic = new CalculatorLogic();
        display = createDisplay();

        setupWindow();
        setupLayout();

        setVisible(true);
    }

    // Grundeinstellungen für das Fenster
    private void setupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
    }

    // Layout-Struktur
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    // Anzeige-Feld
    private JTextField createDisplay() {
        JTextField field = new JTextField("0");
        field.setEditable(false);
        field.setFont(DISPLAY_FONT);
        field.setHorizontalAlignment(SwingConstants.RIGHT);
        field.setBackground(Color.LIGHT_GRAY);
        field.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return field;
    }

    // Button-Raster
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "CE", "√", "x²"
        };

        for (String label : buttons) {
            panel.add(createButton(label));
        }

        return panel;
    }

    // Einzelbutton erstellen
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(getButtonColor(label));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addActionListener(this::handleButtonClick);
        return button;
    }

    // Farbwahl je nach Buttontyp
    private Color getButtonColor(String label) {
        return switch (label) {
            case "+", "-", "*", "/", "=" -> new Color(255, 204, 128); // Orange
            case "C", "CE" -> new Color(255, 153, 153);               // Rot
            case "√", "x²", "%" -> new Color(204, 229, 255);          // Hellblau
            default -> Color.WHITE;                                   // Standard für Zahlen
        };
    }

    // Button-Verhalten
    private void handleButtonClick(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();
        String currentText = display.getText();

        try {
            switch (command) {
                case "C" -> {
                    logic.reset();
                    display.setText("0");
                }
                case "CE" -> {
                    if (!currentText.isEmpty() && !currentText.equals("0")) {
                        display.setText(currentText.substring(0, currentText.length() - 1));
                    }
                }
                case "=" -> {
                    double lastValue = Double.parseDouble(getLastNumber(currentText));
                    double result = logic.calculateResult(lastValue);
                    display.setText(formatResult(result));
                }
                case "+", "-", "*", "/" -> {
                    logic.setOperator(command, Double.parseDouble(getLastNumber(currentText)));
                    display.setText(currentText + command);
                }
                case "√" -> {
                    double value = Double.parseDouble(getLastNumber(currentText));
                    double result = logic.calculateSquareRoot(value);
                    display.setText(formatResult(result));
                }
                case "x²" -> {
                    double value = Double.parseDouble(getLastNumber(currentText));
                    double result = logic.calculateSquare(value);
                    display.setText(formatResult(result));
                }
                case "%" -> {
                    double value = Double.parseDouble(getLastNumber(currentText));
                    double result = logic.calculatePercentage(value);
                    display.setText(formatResult(result));
                }
                case "." -> {
                    if (!getLastNumber(currentText).contains(".")) {
                        display.setText(currentText + ".");
                    }
                }
                default -> {
                    if (currentText.equals("0")) {
                        display.setText(command);
                    } else {
                        display.setText(currentText + command);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            display.setText("Fehler");
        }
    }

    // Letzten Zahlenwert aus Eingabe extrahieren
    private String getLastNumber(String input) {
        String[] parts = input.split("[+\\-*/]");
        return parts.length > 0 ? parts[parts.length - 1] : input;
    }

    // Formatiertes Ergebnis
    private String formatResult(double result) {
        if (Double.isNaN(result)) return "Fehler";
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%.6f", result).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
}