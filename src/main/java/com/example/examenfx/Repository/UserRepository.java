package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;
import com.example.examenfx.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private  Connection connection;

    public  UserRepository(){
        this.connection = new BD().getConnection();
    }

    public  boolean saveUser(User user) {
        try {
            String sql = "INSERT INTO user (nom, prenom, email, password, telephone, login) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getLogin());

            int rowAffect = preparedStatement.executeUpdate();
            return rowAffect > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByLogin(String login) {
        try {

            String sql =  "SELECT * from user ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                User p =  new User();
                p.setId(result.getInt("id"));
                p.setNom(result.getString("nom"));
                p.setPrenom(result.getString("prenom"));
                p.setEmail(result.getString("email"));
                p.setPassword(result.getString("password"));
                p.setTelephone(result.getString("telephone"));
                p.setLogin(result.getString("login"));

                return p;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean isUserExists(String login) {

        try {

            String sql = "SELECT * FROM user WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
