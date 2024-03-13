package com.example.examenfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PrincipalController {

    @FXML
    private TextField messageFx;

    @FXML
    private Pane paneP;

    @FXML
    void category(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/examenfx/category.fxml"));
        paneP.getChildren().removeAll();
        paneP.getChildren().setAll(fxml);
    }

    @FXML
    void extraire_document(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/examenfx/document.fxml"));
        paneP.getChildren().removeAll();
        paneP.getChildren().setAll(fxml);
    }

    @FXML
    void produit(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/examenfx/produit.fxml"));
        paneP.getChildren().removeAll();
        paneP.getChildren().setAll(fxml);
    }

    @FXML
    void statistique(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/examenfx/statistique.fxml"));
        paneP.getChildren().removeAll();
        paneP.getChildren().setAll(fxml);
    }
}
