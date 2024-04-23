package com.example.jdbcjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCredits {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeposit;

    @FXML
    private Label lblCredits;

    @FXML
    private TextField txtCredits;

    @FXML
    void Deposit(MouseEvent event) {

    }

    @FXML
    void GoBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene homepageScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.show();

    }

    @FXML
    public void initialize() {
        lblCredits.setText("Credits: " + HelloController.user.credits);
    }

}
