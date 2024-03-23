package com.example.examenfx.Controller;

import com.example.examenfx.Model.User;
import com.example.examenfx.Repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private UserRepository userRepository;

    @FXML
    private TextField id;

    @FXML
    private TextField idemail;

    @FXML
    private TextField idlog;

    @FXML
    private TextField idnom;

    @FXML
    private PasswordField idpass;

    @FXML
    private TextField idprenom;

    @FXML
    private TextField idtelephone;

    @FXML
    void SignIn(ActionEvent event) throws IOException {

        Parent signin = FXMLLoader.load(getClass().getResource("/com/example/examenfx/hello-view.fxml"));

        // Créer une nouvelle scène
        Scene scene = new Scene(signin);

        // Obtenir la scène actuelle à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Changer la scène actuelle vers la scène de la page d'inscription
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        // Récupérer les valeurs des champs de saisie
        String nom = idnom.getText();
        String prenom = idprenom.getText();
        String email = idemail.getText();
        String login = idlog.getText();
        String password = idpass.getText();
        String telephone = idtelephone.getText();

        // Vérifier si les champs obligatoires sont saisis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || telephone.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs obligatoires.");
            return;
        }
        // Vérifier si le login est déjà utilisé
        if (userRepository.isUserExists(login)) {
            showAlert("Erreur d'inscription", "Ce login est déjà utilisé. Veuillez en choisir un autre.");
            return;
        }

        // Créer un nouvel utilisateur
        User newUser = new User(nom, prenom, email, password, telephone, login);

        // Enregistrer l'utilisateur dans la base de données
        boolean isUserSaved = userRepository.saveUser(newUser);

        // l'utilisateur est enregistré avec succès
        if (isUserSaved) {
            System.out.println("Inscription réussie!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/examenfx/principal.fxml"));
            Parent mainPage = loader.load();

            PrincipalController principalController = loader.getController();
            principalController.setLogin(login);
            // Créer une nouvelle scène
            Scene scene = new Scene(mainPage);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène actuelle vers la scène de la page principale
            stage.setScene(scene);
            stage.show();

        } else {
            showAlert("Erreur d'inscription", "Erreur lors de l'enregistrement de l'utilisateur");
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userRepository = new UserRepository();
    }

}
