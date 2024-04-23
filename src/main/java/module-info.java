module com.example.jdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jdbcjavafx to javafx.fxml;
    exports com.example.jdbcjavafx;
}