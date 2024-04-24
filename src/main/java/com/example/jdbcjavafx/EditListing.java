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
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

public class EditListing {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEditProduct;


    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtProductName;

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
    void EditProduct(MouseEvent event) {
        int cost;
        try{
            cost = Integer.parseInt(txtCost.getText());
        } catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Cost!");
            a.show();
            return;
        }

        if(txtProductName.getText().isEmpty() || cost <= 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please enter a valid name or cost!");
            a.show();
            return;
        }

        String name = txtProductName.getText();
        String dateString = LocalDate.now().toString();

        int tax = (int) (cost * 0.05);

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Tax: " + tax +  "\nConfirm Payment?\nCurrent Credits: $" + HelloController.user.credits);

        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.CANCEL){
            return;
        }

        if(tax > HelloController.user.credits){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Insufficient Credits!");
            a.show();
            return;
        }


        try{
            Connection c = MySQLConnection.getConnection();
            Statement s = c.createStatement();
            String query = "UPDATE tblproducts SET name = '"+name+"', cost = "+cost+" WHERE id = "+Homepage.editProduct.getId();
            s.executeUpdate(query);

            query = "UPDATE tblusers SET credits = " + (HelloController.user.credits - tax) + " WHERE id = " + HelloController.user.id;
            s.executeUpdate(query);

            a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Product updated successfully!");
            a.show();

            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            Scene homepageScene = new Scene(root);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(homepageScene);
            window.setResizable(false);
            window.show();

        } catch (Exception e) {
            a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Update Failed!");
            a.show();

            e.printStackTrace();
        }

        HelloController.user.credits -= tax;
        Homepage.editProduct = null;
    }

    @FXML
    public void initialize(){
        txtProductName.setText(Homepage.editProduct.getName());
        txtCost.setText(String.valueOf(Homepage.editProduct.getCost()));
    }


}
