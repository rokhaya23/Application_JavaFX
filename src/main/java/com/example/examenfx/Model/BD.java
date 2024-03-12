package com.example.examenfx.Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class BD {



    private final String server= "localhost";
    private final String username = "root";
    private final String password = "root";
    private final String bd = "examenfx";
    private final String url = ""
            + "jdbc:mysql://"+server+":3306/"+bd;
    private Connection conn;
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(url, username, password);
            System.out.println("Connecté");
        } catch (Exception ex) {
            conn=null;
            System.out.print("Erreur");
        }
        return conn;
    }

}

