package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;
import com.example.examenfx.Model.Category;
import com.example.examenfx.Model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String sql = "INSERT produit  (libelle_quantite,prix_unitaire,idCategory) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, produit.getLibelle_quantite());
            statement.setInt(2, produit.getPrix_unitaire());
            statement.setString(3, produit.getIdCategory());
            statement.executeUpdate();
            System.out.println("Produit enregistrer");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Produit produit) {
        try {
            String sql = "UPDATE produit SET libelle_quantite = ? , prix_unitaire = ?, idCategory = ? where id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, produit.getLibelle_quantite());
            statement.setInt(2, produit.getPrix_unitaire());
            statement.setString(3, produit.getIdCategory());
            statement.setInt(4, produit.getId());
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
                p.setLibelle_quantite(result.getInt("libelle_quantite"));
                p.setPrix_unitaire(result.getInt("prix_unitaire"));
                p.setIdCategory(result.getString("idCategory"));

                list.add(p);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
