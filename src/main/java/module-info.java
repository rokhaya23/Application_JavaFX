module com.example.examenfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires java.desktop;
    requires org.apache.poi.ooxml;

    opens com.example.examenfx to javafx.fxml;
    exports com.example.examenfx;
    opens com.example.examenfx.Model;
    opens com.example.examenfx.Controller;
    opens com.example.examenfx.Repository;
}