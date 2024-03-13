package com.example.examenfx.Controller;

import com.example.examenfx.Model.Produit;
import com.example.examenfx.Repository.ProduitRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {
    private ProduitRepository produitRepository;

    @FXML
    private TableColumn<Produit, Integer> ccategory;

    @FXML
    private TableColumn<Produit, Integer> cid;

    @FXML
    private TableColumn<Produit, String> cnom_produit;

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
    private TextField tnom_produit;

    @FXML
    private TextField search;

    @FXML
    private TextField tquantite;

    @FXML
    private TableColumn<Produit, Integer> cprix_unitaire;
    @FXML
    void addProduit(ActionEvent event) {
        // Vérifiez si les champs sont vides
        if (tnom_produit.getText().isEmpty() || tquantite.getText().isEmpty() || tprix_unitaire.getText().isEmpty() || comboCategory.getValue() == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        // Obtenez le nom de la catégorie sélectionnée depuis comboCategory
        String selectedCategoryName = comboCategory.getValue();

        // Vérifiez si le nom de catégorie sélectionné existe dans la base de données
        int categoryId = produitRepository.getCategoryIdByName(selectedCategoryName);
        if (categoryId == -1) {
            showAlert("La catégorie sélectionnée est invalide.");
            return;
        }

        // Créez un nouvel objet Produit en utilisant l'ID de la catégorie validée
        Produit produit = new Produit(tnom_produit.getText(),
                Integer.parseInt(tquantite.getText()),
                Integer.parseInt(tprix_unitaire.getText()),
                categoryId, Date.valueOf(currentDate));

        // Ajoutez le produit à la base de données
        produitRepository.add(produit);

        // Effacez les champs et mettez à jour l'affichage
        clearChamps(event);
        affiche();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void charge(MouseEvent event) {
        Produit produit = (Produit) this.tableFx.getSelectionModel().getSelectedItem();
        if(event.getClickCount() == 2){
            tnom_produit.setText(produit.getNom_produit());
            tquantite.setText(produit.getLibelle_quantite()+"");
            tprix_unitaire.setText(produit.getPrix_unitaire()+"");
            String categoryName = produitRepository.getCategoryNameById(produit.getIdCategory());
            comboCategory.setValue(categoryName);
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
        String categoryName = comboCategory.getValue();
        LocalDate currentDate = LocalDate.now();

        int selectedCategoryId = produitRepository.getCategoryIdByName(categoryName);

        Produit produit = new Produit(tnom_produit.getText(),Integer.parseInt(tquantite.getText()),
                Integer.parseInt(tprix_unitaire.getText()),
                selectedCategoryId,Date.valueOf(currentDate));
        produit.setId(Integer.parseInt(id.getText()));

        produitRepository.update(produit);
        this.clearChamps(event);
        this.affiche();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.produitRepository = new ProduitRepository();
        this.affiche();

        // Récupérer les noms de catégorie depuis la base de données
        ObservableList<String> categoryNames = produitRepository.getCategoryNames();
        comboCategory.setItems(categoryNames);

    }


    @FXML
    void clearChamps(ActionEvent event) {
        tnom_produit.setText("");
        tquantite.setText("");
        tprix_unitaire.setText("");
        comboCategory.getSelectionModel().clearSelection();
    }

    void affiche(){
        ObservableList<Produit> list = produitRepository.getAll();
        cid.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
        cnom_produit.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom_produit"));
        cquantite.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("libelle_quantite"));
        cprix_unitaire.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix_unitaire"));
        ccategory.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("idCategory"));

        tableFx.setItems(list);

    }
    @FXML
    void onSearch(KeyEvent event) {
        String keyword = search.getText().trim();
        ObservableList<Produit> filteredList = FXCollections.observableArrayList();

        if (keyword.isEmpty()) {
            // Si le champ de recherche est vide, affichez tous les produits
            filteredList.addAll(produitRepository.getAll());
        } else {
            // Sinon, recherchez les produits par nom de catégorie
            for (Produit produit : produitRepository.getAll()) {
                String categoryName = produitRepository.getCategoryNameById(produit.getIdCategory());
                if (categoryName.toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(produit);
                }
            }
        }

        // Mettez à jour la table avec la liste filtrée
        tableFx.setItems(filteredList);
    }
}
