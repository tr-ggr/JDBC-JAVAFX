module com.example.jdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.jdbcjavafx to javafx.fxml;
    exports com.example.jdbcjavafx;
}