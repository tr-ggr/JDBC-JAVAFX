package com.example.jdbcjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
        int credits;
        try{
            credits = Integer.parseInt(txtCredits.getText());
        } catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Cost!");
            a.show();
            return;
        }
        if(credits <= 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Cost!");
            a.show();
            return;
        }


        if(credits > 100000){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You can only deposit up to 100,000 credits at a time.");
            a.show();
            return;
        }


        try{

            Connection c = MySQLConnection.getConnection();
            Statement s = c.createStatement();
            String query = "UPDATE tblusers SET credits = " + (HelloController.user.credits + credits) + " WHERE id = " + HelloController.user.id;
            s.executeUpdate(query);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Credits added successfully!");
            a.show();



        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Transaction Failed!");
            a.show();
        }

        HelloController.user.credits += credits;
        lblCredits.setText("Credits: $" + HelloController.user.credits);



    }

    @FXML
    void GoBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene homepageScene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    public void initialize() {
        lblCredits.setText("Credits: $" + HelloController.user.credits);
    }

}
