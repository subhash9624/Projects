package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private TextField display = new TextField();
    private double num1 = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #d9d9d9);");

        display.setFont(Font.font("Consolas", 28));
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(60);
        display.setStyle("-fx-background-color: white; -fx-border-color: gray;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        String[][] buttonText = {
            {"CE", "C", "⌫", "/"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"±", "0", ".", "="},
            {"√", "%", "", ""}
        };

        for (int row = 0; row < buttonText.length; row++) {
            for (int col = 0; col < buttonText[row].length; col++) {
                String text = buttonText[row][col];
                if (text.isEmpty()) continue; // Skip empty cells
                Button btn = createButton(text);
                grid.add(btn, col, row);
            }
        }

        root.getChildren().addAll(display, grid);

        Scene scene = new Scene(root, 350, 500);
        primaryStage.setTitle("Advanced Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(70, 60);
        btn.setFont(Font.font("Segoe UI", 18));
        btn.setStyle("-fx-background-radius: 10; -fx-background-color: #e6e6e6; -fx-border-color: #bfbfbf;");
        btn.setOnAction(e -> handleButtonClick(text));
        return btn;
    }

    private void handleButtonClick(String text) {
        try {
            switch (text) {
                case "C":
                    display.clear();
                    num1 = 0;
                    operator = "";
                    break;

                case "CE":
                    display.clear();
                    break;

                case "⌫":
                    if (!display.getText().isEmpty()) {
                        display.setText(display.getText().substring(0, display.getText().length() - 1));
                    }
                    break;

                case "±":
                    if (!display.getText().isEmpty()) {
                        double val = Double.parseDouble(display.getText());
                        display.setText(String.valueOf(-val));
                    }
                    break;

                case "√":
                    if (!display.getText().isEmpty()) {
                        double val = Double.parseDouble(display.getText());
                        if (val < 0) {
                            display.setText("Error");
                        } else {
                            display.setText(String.valueOf(Math.sqrt(val)));
                        }
                    }
                    break;

                case "%":
                    if (!display.getText().isEmpty()) {
                        double val = Double.parseDouble(display.getText());
                        display.setText(String.valueOf(val / 100));
                    }
                    break;

                case "+": case "-": case "*": case "/":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        operator = text;
                        startNewNumber = true;
                    }
                    break;

                case "=":
                    if (!display.getText().isEmpty()) {
                        double num2 = Double.parseDouble(display.getText());
                        double result = calculate(num1, num2, operator);
                        display.setText(String.valueOf(result));
                        startNewNumber = true;
                    }
                    break;

                default:
                    if (startNewNumber) {
                        display.setText(text.equals(".") ? "0." : text);
                        startNewNumber = false;
                    } else {
                        display.appendText(text);
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            display.setText("Invalid");
        } catch (ArithmeticException e) {
            display.setText("Error");
        }
    }

    private double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new ArithmeticException("Divide by Zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator");
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
