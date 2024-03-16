package com.example.examenfx.Model;

import java.sql.Date;

public class Produit {
    private int id;
    private String nom_produit;
    private int libelle_quantite;
    private int prix_unitaire;
    private int idCategory;
    private Date date;
    private String category ;
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibelle_quantite() {
        return libelle_quantite;
    }

    public void setLibelle_quantite(int libelle_quantite) {
        this.libelle_quantite = libelle_quantite;
    }

    public int getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(int prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Produit() {
    }

    public Produit(String nom_produit ,int libelle_quantite, int prix_unitaire, int idCategory, Date date, String category) {
        this.libelle_quantite = libelle_quantite;
        this.prix_unitaire = prix_unitaire;
        this.idCategory = idCategory;
        this.nom_produit = nom_produit;
        this.date = date;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", libelle_quantite=" + libelle_quantite +
                ", prix_unitaire=" + prix_unitaire +
                ", idCategory=" + idCategory +
                ", nom_produit=" + nom_produit +

                '}';
    }
}
