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
    private Label txtWelcome;

    @FXML
    private Button btnDeleteListing;

    @FXML
    private Button btnEditListing;

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

    static Product editProduct;

    //declare observable list for database data
    ObservableList<Product> products = FXCollections.observableArrayList();

    public void RefreshTable(){
        products.clear();

        try(Connection conn = MySQLConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tblproducts");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String seller = rs.getString("seller");
                String cost = rs.getString("cost");
                String date = rs.getDate("date").toString();
                Product product = new Product(id, name, cost, seller, date);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableview.setItems(products);
    }

    @FXML
    public void initialize() {
        txtWelcome.setText("Welcome, " + HelloController.user.name + "!");

        RefreshTable();


        clmnID.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        clmnCost.setCellValueFactory(
                new PropertyValueFactory<>("cost")
        );
        clmnName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        clmnSeller.setCellValueFactory(
                new PropertyValueFactory<>("seller")
        );
        clmnOption.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );

        tableview.setItems(products);

        if(!HelloController.user.isSeller) {
            btnListing.setVisible(false);
            btnDeleteListing.setVisible(false);
            btnEditListing.setVisible(false);
        }

        txtCredits.setText("Credits: $" + HelloController.user.credits);
    }



    @FXML
    void AddCredits(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-credits.fxml"));
        Scene homepageScene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.setResizable(false);
        window.show();

    }

    @FXML
    void CreateListing(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("create-listing.fxml"));
        Scene homepageScene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    void Logout(MouseEvent event) throws IOException {
        HelloController.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene homepageScene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homepageScene);
        window.setResizable(false);
        window.show();
    }

    @FXML
    void BuyProduct(MouseEvent event) {
        try {
            Product product = tableview.getSelectionModel().getSelectedItem();
            if (product.getSeller().equals(HelloController.user.name)) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Couldn't buy your own Product!");
                a.show();
                return;
            }

            if (product.getCost() > HelloController.user.credits) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Insufficient Credits!");
                a.show();
                return;
            }

            try (Connection c = MySQLConnection.getConnection();
                 Statement s = c.createStatement()) {
                c.setAutoCommit(false);
                String sql = "UPDATE tblusers SET credits = " + (HelloController.user.credits - product.getCost()) + " WHERE id = " + HelloController.user.id;

                s.execute(sql);

                sql = "SELECT * FROM tblusers WHERE name = '" + product.getSeller() + "'";

                ResultSet rs = s.executeQuery(sql);

                if (rs.next()) {
                    int sellerCredits = rs.getInt("credits");
                    sql = "UPDATE tblusers SET credits = " + (sellerCredits + product.getCost()) + " WHERE name = '" + product.getSeller() + "'";
                    s.execute(sql);
                }

                sql = "DELETE FROM tblproducts WHERE id = " + tableview.getSelectionModel().getSelectedItem().getId();

                s.execute(sql);

                c.commit();

                RefreshTable();

                HelloController.user.credits -= product.getCost();
                txtCredits.setText("Credits: $" + HelloController.user.credits);


                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("SUCCESSFULLY PURCHASED " + product.getName() + " FOR $" + product.getCost());
                a.show();

            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("PLEASE SELECT A PRODUCT");
                a.show();

                e.printStackTrace();
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("PLEASE SELECT A PRODUCT");
            a.show();

            e.printStackTrace();
        }



    }


    @FXML
    void DeleteProduct(MouseEvent event) {
        try{
            Product product = tableview.getSelectionModel().getSelectedItem();

            if(!product.getSeller().equals(HelloController.user.name)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Could only delete your own Product!");
                a.show();
                return;
            }

            try(Connection c = MySQLConnection.getConnection();
                Statement s = c.createStatement()) {
                String sql = "DELETE FROM tblproducts WHERE id = " + tableview.getSelectionModel().getSelectedItem().getId();
                s.execute(sql);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Product deleted successfully!");
                a.show();

                RefreshTable();

            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("PLEASE SELECT A PRODUCT");
                a.show();

                e.printStackTrace();
            }

        } catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("PLEASE SELECT A PRODUCT");
            a.show();

            e.printStackTrace();
        }


    }

    @FXML
    void EditProduct(MouseEvent event) {
        try {


            Product product = tableview.getSelectionModel().getSelectedItem();
            if (!product.getSeller().equals(HelloController.user.name)) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Could only edit your own Product!");
                a.show();
                return;
            }

            editProduct = product;

            try {
                Parent root = FXMLLoader.load(getClass().getResource("edit-listing.fxml"));
                Scene homepageScene = new Scene(root);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(homepageScene);
                window.setResizable(false);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("PLEASE SELECT A PRODUCT");
            a.show();

            e.printStackTrace();
        }
    }



}
