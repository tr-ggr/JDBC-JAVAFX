package com.example.jdbcjavafx;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Product {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty seller;
    private final StringProperty cost;
    private final StringProperty date;

    public Product(Integer id, String name, String cost, String seller, String date){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.seller = new SimpleStringProperty(seller);
        this.cost = new SimpleStringProperty(cost);
        this.date = new SimpleStringProperty(date);
    }



    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSeller() {
        return seller.get();
    }

    public StringProperty sellerProperty() {
        return seller;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}
