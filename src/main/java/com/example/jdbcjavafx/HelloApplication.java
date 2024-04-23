package com.example.jdbcjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 373, 593);
        stage.setTitle("WildMarket");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        try(Connection c = MySQLConnection.getConnection();
            Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS " +
                    "tblusers (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255) UNIQUE, password VARCHAR(255), credits INT(255) NULL DEFAULT 0, " +
                    "isSeller BIT(1))");

            s.execute("CREATE TABLE IF NOT EXISTS " +
                    "tblproducts (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), seller VARCHAR(255), " +
                    "cost INT(255), date DATE,  " +
                    "FOREIGN KEY (seller) REFERENCES tblusers(name))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}