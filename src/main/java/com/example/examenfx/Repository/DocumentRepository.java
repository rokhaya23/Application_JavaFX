package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;

import java.sql.Connection;

public class DocumentRepository {
    private static Connection connection;

    public  DocumentRepository(){
        this.connection = new BD().getConnection();
    }

}
