package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueRepository {

    private  Connection connection;

    public StatistiqueRepository() {
        this.connection = new BD().getConnection();
    }

    // Méthode pour obtenir le nombre de produits par catégorie
    public Map<String, Integer> getProductCountByCategory() {
        Map<String, Integer> productCountByCategory = new HashMap<>();

        // Créez la requête SQL pour obtenir le nombre de produits par catégorie
        String query = "SELECT category.libelle, COUNT(produit.Id) AS product_count " +
                "FROM produit " +
                "JOIN category ON produit.idCategory = category.Id " +
                "GROUP BY category.libelle";
        // Préparez la requête
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Exécutez la requête et obtenez le résultat
            try (ResultSet resultSet = statement.executeQuery()) {
                // Traitez les résultats et stockez-les dans productCountByCategory
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("libelle"); // Correction de la colonne de nom de catégorie
                    int productCount = resultSet.getInt("product_count");
                    productCountByCategory.put(categoryName, productCount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions appropriées
        }

        return productCountByCategory;
    }
    public Map<String, Integer> getProductCountByMonth(int year) {
        Map<String, Integer> productCountByMonth = new HashMap<>();

        String query = "SELECT MONTH(date) AS month, COUNT(*) AS product_count " +
                "FROM produit " +
                "WHERE YEAR(date) = ? " +  // Utilisez le paramètre pour filtrer par année
                "GROUP BY MONTH(date)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, year);  // Définissez la valeur du paramètre
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int mois = resultSet.getInt("month");  // Utilisez le nom correct de la colonne
                    int nombreProduits = resultSet.getInt("product_count");
                    String nomMois = getNomMois(mois);
                    productCountByMonth.put(nomMois, nombreProduits);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productCountByMonth;
    }


    // Méthode pour obtenir le nom du mois à partir de son numéro
    private String getNomMois(int mois) {
        switch (mois) {
            case 1: return "Janvier";
            case 2: return "Février";
            case 3: return "Mars";
            case 4: return "Avril";
            case 5: return "Mai";
            case 6: return "Juin";
            case 7: return "Juillet";
            case 8: return "Août";
            case 9: return "Septembre";
            case 10: return "Octobre";
            case 11: return "Novembre";
            case 12: return "Décembre";
            default: return "Mois inconnu";
        }
    }
}

