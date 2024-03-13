package com.example.examenfx.Controller;

import com.example.examenfx.Model.Category;
import com.example.examenfx.Repository.CategoryRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    private CategoryRepository categoryRepository;
    @FXML
    private TableColumn<Category, Integer> cid;

    @FXML
    private TableColumn<Category, String> clibelle;

    @FXML
    private TextField id;

    @FXML
    private TableView<Category> tableFx;

    @FXML
    private TextField tlibelle;

    @FXML
    void clearChamps(ActionEvent event) {
        tlibelle.setText("");
    }
    @FXML
    void addCategory(ActionEvent event) {
        Category category = new Category(tlibelle.getText());

        categoryRepository.add(category);
        this.clearChamps(event);
        this.affiche();
    }

    @FXML
    void charge(MouseEvent event) {
        Category category = (Category) this.tableFx.getSelectionModel().getSelectedItem();
        if(event.getClickCount() == 2){
            tlibelle.setText(category.getLibelle());
            id.setText(category.getId()+"");
        }
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        // Récupérer l'ID de la catégorie sélectionnée
        Category selectedCategory = this.tableFx.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer la catégorie");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer définitivement cette catégorie ?");

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer la catégorie si l'utilisateur a confirmé
                int id = selectedCategory.getId();
                categoryRepository.delete(id);
                this.affiche();
            }
        } else {
            // Aucune catégorie sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune catégorie sélectionnée");
            alert.setContentText("Veuillez sélectionner une catégorie à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateCategory(ActionEvent event) {
        Category category = new Category(tlibelle.getText());
        category.setId(Integer.parseInt(id.getText()));

        categoryRepository.update(category);
        this.clearChamps(event);
        this.affiche();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.categoryRepository = new CategoryRepository ();
        this.affiche();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void affiche(){
        ObservableList<Category> list = categoryRepository.getAll();
        cid.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        clibelle.setCellValueFactory(new PropertyValueFactory<Category, String>("libelle"));

        tableFx.setItems(list);

    }


}
