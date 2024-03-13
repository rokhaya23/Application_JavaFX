package com.example.examenfx.Controller;

import com.example.examenfx.Model.Category;
import com.example.examenfx.Model.Produit;
import com.example.examenfx.Repository.CategoryRepository;
import com.example.examenfx.Repository.ProduitRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {
    private ProduitRepository produitRepository;
    private CategoryRepository categoryRepository;


    @FXML
    private TableColumn<Produit, String> ccategory;

    @FXML
    private TableColumn<Produit, Integer> cid;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private TableColumn<Produit, Integer> cquantite;

    @FXML
    private TextField id;

    @FXML
    private TableView<Produit> tableFx;

    @FXML
    private TextField tprix_unitaire;

    @FXML
    private TextField tquantite;

    @FXML
    private TableColumn<Produit, Integer> cprix_unitaire;
    @FXML
    void addProduit(ActionEvent event) {
        Produit produit = new Produit(Integer.parseInt(tquantite.getText()),
                Integer.parseInt(tprix_unitaire.getText()),
                (String) comboCategory.getValue());

        produitRepository.add(produit);
        this.clearChamps(event);
        this.affiche();
    }

    @FXML
    void charge(MouseEvent event) {
        Produit produit = (Produit) this.tableFx.getSelectionModel().getSelectedItem();
        if(event.getClickCount() == 2){
            tquantite.setText(produit.getLibelle_quantite()+"");
            tprix_unitaire.setText(produit.getPrix_unitaire()+"");
            comboCategory.setValue(produit.getIdCategory());
            id.setText(produit.getId()+"");
        }
    }

    @FXML
    void deleteProduit(ActionEvent event) {
        // Récupérer l'ID du produit sélectionné
        Produit selectedProduit = this.tableFx.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer le produit");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer définitivement ce produit ?");

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer le produit si l'utilisateur a confirmé
                int id = selectedProduit.getId();
                produitRepository.delete(id);
                this.affiche();
            }
        } else {
            // Aucun produit sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun produit sélectionné");
            alert.setContentText("Veuillez sélectionner un produit à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateProduit(ActionEvent event) {
        Produit produit = new Produit(Integer.parseInt(tquantite.getText()),
                Integer.parseInt(tprix_unitaire.getText()),
                comboCategory.getAccessibleText());
        produit.setId(Integer.parseInt(id.getText()));

        produitRepository.update(produit);
        this.clearChamps(event);
        this.affiche();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.produitRepository = new ProduitRepository();
        this.affiche();

        // Récupérer les catégories depuis la base de données
        List<Category> categoriesList = CategoryRepository.getAll();

        // Construire une liste de noms de catégories à afficher dans le ComboBox
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        for (Category category : categoriesList) {
            categoryNames.add(category.getLibelle());
        }

        // Définir les noms de catégories dans le ComboBox
        comboCategory.setItems(categoryNames);
    }


    @FXML
    void clearChamps(ActionEvent event) {
        tquantite.setText("");
        tprix_unitaire.setText("");
        comboCategory.setValue("");
    }

    void affiche(){
        ObservableList<Produit> list = produitRepository.getAll();
        cid.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
        cquantite.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("libelle_quantite"));
        cprix_unitaire.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix_unitaire"));
        ccategory.setCellValueFactory(new PropertyValueFactory<Produit, String>("idCategorie"));

        tableFx.setItems(list);

    }
}
