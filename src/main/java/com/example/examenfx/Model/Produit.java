package com.example.examenfx.Model;

public class Produit {
    private int id;
    private int libelle_quantite;
    private int prix_unitaire;
    private int idCategory;

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

    public Produit() {
    }

    public Produit(int libelle_quantite, int prix_unitaire, int idCategory) {
        this.libelle_quantite = libelle_quantite;
        this.prix_unitaire = prix_unitaire;
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", libelle_quantite=" + libelle_quantite +
                ", prix_unitaire=" + prix_unitaire +
                ", idCategory=" + idCategory +
                '}';
    }
}
