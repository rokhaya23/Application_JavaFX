package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;
import com.example.examenfx.Model.Category;
import com.example.examenfx.Model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ProduitRepository {
    private static Connection connection;

    public  ProduitRepository(){
        this.connection = new BD().getConnection();
    }

    public void delete(int id) {
        try {
            String sql = "DELETE from produit  where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Produit supprimer");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void add(Produit produit) {
        try {
            LocalDate currentDate = LocalDate.now();
            Date date = Date.valueOf(currentDate);


            String sql = "INSERT produit  (nom_produit,libelle_quantite,prix_unitaire,idCategory,date) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produit.getNom_produit());
            statement.setInt(2, produit.getLibelle_quantite());
            statement.setInt(3, produit.getPrix_unitaire());
            statement.setInt(4, produit.getIdCategory());
            statement.setDate(5, date); // Utilisez la date actuelle dans la requête

            statement.executeUpdate();
            System.out.println("Produit enregistrer");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void update(Produit produit) {
        try {

            String sql = "UPDATE produit SET nom_produit = ? , libelle_quantite = ? , prix_unitaire = ?, idCategory = ? where id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produit.getNom_produit());
            statement.setInt(2, produit.getLibelle_quantite());
            statement.setInt(3, produit.getPrix_unitaire());
            statement.setInt(4, produit.getIdCategory());
            statement.setInt(5, produit.getId());
            statement.executeUpdate();
            System.out.println("Produit updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static ObservableList<Produit> getAll() {
        ObservableList<Produit> list = FXCollections.observableArrayList();
        try {

            String sql =  "SELECT * from produit ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Produit p =  new Produit();
                p.setId(result.getInt("id"));
                p.setNom_produit(result.getString("nom_produit"));
                p.setLibelle_quantite(result.getInt("libelle_quantite"));
                p.setPrix_unitaire(result.getInt("prix_unitaire"));
                p.setIdCategory(result.getInt("idCategory"));

                list.add(p);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getCategoryNames() {
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        try {
            String sql = "SELECT libelle FROM category";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String categoryName = result.getString("libelle");
                categoryNames.add(categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryNames;
    }

    public String getCategoryNameById(int categoryId) {
        try {
            String categoryName = null;
            String sql = "SELECT libelle FROM category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                categoryName = result.getString("libelle");
            }
            return categoryName;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du nom de la catégorie par ID", e);
        }
    }

    public int getCategoryIdByName(String categoryName) {
        int categoryId = -1; // Valeur par défaut si la catégorie n'est pas trouvée
        try {
            String sql = "SELECT id FROM category WHERE libelle = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, categoryName);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                categoryId = result.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryId;
    }

}
