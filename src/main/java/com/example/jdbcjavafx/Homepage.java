package com.example.jdbcjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

public class Homepage {

    @FXML
    private Button btnAddCredits;

    @FXML
    private Button btnListing;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Product, String> clmnCost;

    @FXML
    private TableColumn<Product, String> clmnID;

    @FXML
    private TableColumn<Product, String> clmnName;

    @FXML
    private TableColumn<Product, String> clmnOption;

    @FXML
    private TableColumn<Product, String> clmnSeller;

    @FXML
    private TableView<Product> tableview;

    @FXML
    private Label txtCredits;

    //declare observable list for database data
    ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtolentino", "root", "")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tblproducts");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String seller = rs.getString("seller");
                String cost = rs.getString("cost");
                String date = rs.getDate("date").toString();
                Product product = new Product(id, name, seller, cost, date);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        clmnID.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        clmnName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        clmnCost.setCellValueFactory(
                new PropertyValueFactory<>("cost")
        );
        clmnSeller.setCellValueFactory(
                new PropertyValueFactory<>("seller")
        );
        clmnOption.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );

        tableview.setItems(products);

        if(!HelloController.user.isSeller) {
            btnListing.setDisable(true);
        }
    }



    @FXML
    void AddCredits(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-credits.fxml"));
        Scene homepageScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.show();

    }

    @FXML
    void CreateListing(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("create-listing.fxml"));
        Scene homepageScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.show();
    }

    @FXML
    void Logout(MouseEvent event) throws IOException {
        HelloController.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene homepageScene = new Scene(root);

        // Get the Stage from the event and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.show();
    }

    @FXML
    void BuyProduct(MouseEvent event) {
        try{

            Product product = tableview.getSelectionModel().getSelectedItem();
            System.out.println(product.getId());

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("PLEASE SELECT A PRODUCT");
            a.show();
        }



    }


}
