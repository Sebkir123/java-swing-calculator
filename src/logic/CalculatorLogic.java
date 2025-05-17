package logic;

// Diese Klasse enthält die Rechenlogik für den Taschenrechner

public class CalculatorLogic {

    private double currentValue = 0;
    private double previousValue = 0;
    private String currentOperator = "";
    private boolean resetDisplay = false;

    // Speichert den aktuellen Operator und den bisherigen Wert
    public void setOperator(String operator, double currentDisplayValue) {
        previousValue = currentDisplayValue;
        currentOperator = operator;
        resetDisplay = true;
    }

    // Führt die gespeicherte Rechenoperation aus
    public double calculateResult(double currentDisplayValue) {
        switch (currentOperator) {
            case "+" -> currentValue = previousValue + currentDisplayValue;
            case "-" -> currentValue = previousValue - currentDisplayValue;
            case "*" -> currentValue = previousValue * currentDisplayValue;
            case "/" -> {
                if (currentDisplayValue == 0) return Double.NaN;
                currentValue = previousValue / currentDisplayValue;
            }
            default -> currentValue = currentDisplayValue;
        }

        resetDisplay = true;
        return currentValue;
    }

    // Berechnet die Quadratwurzel
    public double calculateSquareRoot(double value) {
        if (value < 0) return Double.NaN;
        return Math.sqrt(value);
    }

    // Berechnet das Quadrat
    public double calculateSquare(double value) {
        return value * value;
    }

    // Berechnet den Prozentwert auf Basis des vorherigen Werts
    public double calculatePercentage(double percentValue) {
        return previousValue * percentValue / 100.0;
    }

    // Setzt den Zustand zurück
    public void reset() {
        currentValue = 0;
        previousValue = 0;
        currentOperator = "";
        resetDisplay = false;
    }
}