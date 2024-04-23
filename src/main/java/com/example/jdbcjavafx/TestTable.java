package com.example.jdbcjavafx;

import com.mysql.cj.MysqlConnection;

import java.sql.*;

public class TestTable {
    public static void main(String[] args) throws SQLException {
        try(Connection c = MySQLConnection.getConnection();
            Statement s = c.createStatement()) {

            s.execute("DROP TABLE IF EXISTS tblproducts");
            s.execute("DROP TABLE IF EXISTS tblusers");


//            s.execute("CREATE TABLE IF NOT EXISTS " +
//                    "tblusers (id INT PRIMARY KEY AUTO_INCREMENT, " +
//                    "name VARCHAR(255) UNIQUE, password VARCHAR(255), credits INT(255) NULL DEFAULT 0, " +
//                    "isSeller BIT(1))");
//
//            s.execute("CREATE TABLE IF NOT EXISTS " +
//                    "tblproducts (id INT PRIMARY KEY AUTO_INCREMENT, " +
//                    "name VARCHAR(255), seller VARCHAR(255), " +
//                    "cost INT(255), date DATE,  " +
//                    "FOREIGN KEY (seller) REFERENCES tblusers(name))");
//
//            s.execute("INSERT INTO tblusers (name, password, isSeller, credits) " +
//                    "VALUES ('admin', 'admin', 1, 1000)");
//
//            s.execute("INSERT INTO tblusers (name, password, isSeller, credits) " +
//                    "VALUES ('buyer', 'buyer', 0, 5000)");
//
//            s.execute("INSERT INTO tblproducts (name, cost, seller, date) " +
//                    "VALUES ('Condoms', 50, 'admin', '2022-01-01')");





            System.out.println("Successfully Printed!");
        }catch (SQLException e) {

            e.printStackTrace();

        }
    }
}
