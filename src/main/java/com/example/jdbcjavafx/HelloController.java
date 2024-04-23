package com.example.jdbcjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    public static User user;

    @FXML
    void GoToRegister(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-page.fxml"));
        Scene registerScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.show();
    }

    @FXML
    void Login(MouseEvent event) {
        try(
                Connection c = MySQLConnection.getConnection();
                Statement s = c.createStatement();
                ) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String query = "SELECT * FROM tblusers WHERE name = '" + username + "' AND password = '" + password + "'";

            ResultSet resultset = s.executeQuery(query);
             if (resultset.next() ) {

                int id = s.getResultSet().getInt("id");
                String name = s.getResultSet().getString("name");
                int credits = s.getResultSet().getInt("credits");
                boolean isSeller = s.getResultSet().getBoolean("isSeller");
                user = new User(id, name, credits, isSeller);

                Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                Scene homepageScene = new Scene(root);

                // Get the Stage from the event and set the new scene
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(homepageScene);
                window.show();

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid username or password");
                a.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
