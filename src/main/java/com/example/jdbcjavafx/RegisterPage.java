package com.example.jdbcjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Base64;

public class RegisterPage {

    @FXML
    private ToggleGroup Type;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private RadioButton rbBuyer;

    @FXML
    private RadioButton rbSeller;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void GoToLogin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene registerScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    void Register(MouseEvent event) {
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() ||
                Type.getSelectedToggle() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields!");
            a.show();
            return;
        }


        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String type = ((RadioButton) Type.getSelectedToggle()).getText();
        System.out.println(username + " " + password + " " + type);

        try(Connection c = MySQLConnection.getConnection()) {
            // Check if the user already exists
            PreparedStatement checkUser = c.prepareStatement("SELECT * FROM tblusers WHERE name = ?");
            checkUser.setString(1, username);
            ResultSet resultSet = checkUser.executeQuery();

            // If the user does not exist, insert the new user
            if (!resultSet.next()) {
                PreparedStatement s = c.prepareStatement("INSERT INTO tblusers (name, password, isSeller) VALUES (?, ?, ?)");
                s.setString(1, username);
                s.setString(2, password);
                s.setInt(3, (type.equals("Seller") ? 1 : 0));
                s.execute();

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("User registered successfully!");
                a.show();
            } else {
                System.out.println("User already exists!");

                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("User already exists!");
                a.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
