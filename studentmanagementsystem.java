package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class studentmanagementsystem extends Application {

    // UI input fields   
    private final TextField nameField = new TextField();
    private final TextField rollField = new TextField();
    private final TextField courseField = new TextField();
    private final TextField semField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField phoneField = new TextField();

    @Override
    public void start(Stage stage) {
        GridPane grid = createForm();

        Button insertBtn = new Button("Insert");
        grid.add(insertBtn, 1, 6);
        insertBtn.setOnAction(e -> insertData());

        Scene scene = new Scene(grid, 400, 350);
        stage.setTitle("Student Management - Insert");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0); grid.add(nameField, 1, 0);
        grid.add(new Label("Roll Number:"), 0, 1); grid.add(rollField, 1, 1);
        grid.add(new Label("Course:"), 0, 2); grid.add(courseField, 1, 2);
        grid.add(new Label("Semester:"), 0, 3); grid.add(semField, 1, 3);
        grid.add(new Label("Email:"), 0, 4); grid.add(emailField, 1, 4);
        grid.add(new Label("Phone:"), 0, 5); grid.add(phoneField, 1, 5);

        return grid;
    }

    private void insertData() {
        String name = nameField.getText();
        String roll_number = rollField.getText();
        String course = courseField.getText();
        String semester = semField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        String sql = "INSERT INTO std1 (name, roll_number, course, semester, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, roll_number);
            stmt.setString(3, course);
            stmt.setString(4, semester);
            stmt.setString(5, email);
            stmt.setString(6, phone);

            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Data inserted successfully.");
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/std";
        String user = "root";
        String password = "admin"; // Replace with your real MySQL password
        return DriverManager.getConnection(url, user, password);
    }

    private void clearFields() {
        nameField.clear(); rollField.clear(); courseField.clear();
        semField.clear(); emailField.clear(); phoneField.clear();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}