package com.example.jdbcjavafx;

public class User {
    public Integer id;
    public String name;
    public Integer credits;
    public boolean isSeller;

    public User(Integer id, String name, Integer credits, boolean isSeller) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.isSeller = isSeller;
    }
}
