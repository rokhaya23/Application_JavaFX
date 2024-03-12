module com.example.examenfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.examenfx to javafx.fxml;
    exports com.example.examenfx;
    opens com.example.examenfx.Model;
}