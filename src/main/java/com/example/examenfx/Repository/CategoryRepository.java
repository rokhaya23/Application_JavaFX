package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;
import com.example.examenfx.Model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRepository {
    private Connection connection;

    public  CategoryRepository(){
        this.connection = new BD().getConnection();
    }
    public void delete(int id) {
        try {
            String sql = "DELETE from category  where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Categorie supprimer");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void add(Category category) {
        try {
            String sql = "INSERT category  (libelle) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getLibelle());
            statement.executeUpdate();
            System.out.println("Categorie enregistrer");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Category category) {
        try {
            String sql = "UPDATE category SET libelle = ?  where id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getLibelle());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
            System.out.println("Categorie updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public  ObservableList<Category> getAll() {
        ObservableList<Category> list = FXCollections.observableArrayList();
        try {

            String sql =  "SELECT * from category ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Category c =  new Category();
                c.setId(result.getInt("id"));
                c.setLibelle(result.getString("libelle"));

                list.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category getById(int id) {
        Category category = null;
        try {
            String sql = "SELECT * FROM category WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                category = new Category();
                category.setId(result.getInt("id"));
                category.setLibelle(result.getString("libelle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
