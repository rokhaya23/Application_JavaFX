package com.example.examenfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Optional;

public class PrincipalController {

    @FXML
    private TextField messageFx;

    @FXML
    private Pane paneP;

    @FXML
    void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur appuie sur OK, effectuez la déconnexion
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close(); // Fermer la fenêtre actuelle
            // Ouvrir une nouvelle fenêtre de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examenfx/hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void category(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/example/examenfx/category.fxml"));
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
