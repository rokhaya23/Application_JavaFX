package com.example.examenfx;

import com.example.examenfx.Model.User;
import com.example.examenfx.Model.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

public class HelloController implements Initializable {

    private  UserRepository userRepository;

    @FXML
    private TextField idLogin;

    @FXML
    private PasswordField idPassword;

    @FXML
    void signIn(ActionEvent event) throws IOException {
        // Récupérer les valeurs des champs de connexion
        String login = idLogin.getText();
        String password = idPassword.getText();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Appeler votre méthode de vérification de l'utilisateur (à adapter)
        boolean isValidUser = isUserValid(login, password);

        if (isValidUser) {
            System.out.println("Connexion réussie!");

                // Charger la scène de la page principale depuis le fichier FXML
                Parent mainPage = FXMLLoader.load(getClass().getResource("/com/example/examenfx/principal.fxml"));

                // Créer une nouvelle scène
                Scene scene = new Scene(mainPage);

                // Obtenir la scène actuelle à partir de l'événement
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Changer la scène actuelle vers la scène de la page principale
                stage.setScene(scene);
                stage.show();

        } else {
            showAlert("Erreur de connexion", "Login ou mot de passe incorrect");
        }
    }

    private boolean isUserValid(String login, String password) {
        User user = userRepository.getUserByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour rediriger vers la page principale

    @FXML
    void signUp(ActionEvent event) throws IOException {
        // Charger la scène de la page d'inscription depuis le fichier FXML
        Parent signup = FXMLLoader.load(getClass().getResource("signUp.fxml"));

        // Créer une nouvelle scène
        Scene scene = new Scene(signup);

        // Obtenir la scène actuelle à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène actuelle vers la scène de la page d'inscription
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userRepository = new UserRepository();
    }
}