/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculator;

/**
 *
 * @author Cajegas Angelo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String lastOperator;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        currentInput = new StringBuilder();
        result = 0.0;
        lastOperator = "";

        display = new JTextField(10);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            buttonPanel.add(button);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(display, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "=":
                calculate();
                break;
            case "C":
                clear();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperator(command);
                break;
            default:
                currentInput.append(command);
                display.setText(currentInput.toString());
                break;
        }
    }

    private void handleOperator(String operator) {
        if (currentInput.length() > 0) {
            if (lastOperator.length() > 0) {
                calculate();
            }
            lastOperator = operator;
            currentInput.append(" ").append(operator).append(" ");
            display.setText(currentInput.toString());
        }
    }

    private void calculate() {
    try {
        result = evaluateExpression(currentInput.toString());
        String resultText = Double.toString(result);
        if (resultText.endsWith(".0")) {
            resultText = resultText.substring(0, resultText.length() - 2); // Remove the trailing ".0"
        }
        display.setText(resultText);
        currentInput.setLength(0);
        currentInput.append(Double.toString(result));
        lastOperator = "";
    } catch (Exception ex) {
        display.setText("Error");
    }
}

    private void clear() {
        currentInput.setLength(0);
        lastOperator = "";
        display.setText("");
    }

    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        double operand1 = Double.parseDouble(tokens[0]);
        double operand2 = Double.parseDouble(tokens[2]);
        String operator = tokens[1];

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
