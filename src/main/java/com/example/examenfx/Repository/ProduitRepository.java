package com.example.examenfx.Repository;

import com.example.examenfx.Model.BD;
import com.example.examenfx.Model.Category;
import com.example.examenfx.Model.Produit;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProduitRepository {
    private  Connection connection;

    public  ProduitRepository(){
        this.connection = new BD().getConnection();
    }
    private ProduitRepository produitRepository;

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



    public  ObservableList<Produit> getAll() {
        ObservableList<Produit> list = FXCollections.observableArrayList();
        try {

            String sql =   "SELECT p.id, p.nom_produit, p.libelle_quantite, p.prix_unitaire, p.idCategory, c.libelle AS category " +
                    "FROM produit p " +
                    "INNER JOIN category c ON p.idCategory = c.id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Produit p =  new Produit();
                p.setId(result.getInt("id"));
                p.setNom_produit(result.getString("nom_produit"));
                p.setLibelle_quantite(result.getInt("libelle_quantite"));
                p.setPrix_unitaire(result.getInt("prix_unitaire"));
                p.setIdCategory(result.getInt("idCategory"));
                p.setCategory(result.getString("category"));

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


    public  void exportToPDF(ObservableList<Produit> productList) {
        try {
            // Création du document PDF avec le format A4
            Document document = new Document(PageSize.A4);
            File file = new File("products.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Personnalisation du format d'affichage
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Ajout du titre
            Paragraph title = new Paragraph("Liste des Produits", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n")); // Espacement

            // Création du tableau
            PdfPTable table = new PdfPTable(4); // 4 colonnes pour les attributs

            // Ajout des en-têtes de colonnes
            table.addCell(createCell("Nom", tableHeaderFont));
            table.addCell(createCell("Prix", tableHeaderFont));
            table.addCell(createCell("Quantité", tableHeaderFont));
            table.addCell(createCell("Categorie", tableHeaderFont));

            // Ajout des produits
            for (Produit produit : productList) {
                table.addCell(createCell(produit.getNom_produit(), normalFont));
                table.addCell(createCell(String.valueOf(produit.getPrix_unitaire()), normalFont));
                table.addCell(createCell(String.valueOf(produit.getLibelle_quantite()), normalFont));
                String categoryName = getCategoryNameById(produit.getIdCategory());
                table.addCell(createCell(categoryName, normalFont)); // Ajouter le nom de la catégorie au lieu de l'ID
            }

            document.add(table);
            document.close();

            // Ouvrir le fichier PDF avec le programme par défaut
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Le programme par défaut n'est pas supporté sur ce système.");
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    // Méthode utilitaire pour créer une cellule de tableau PDF
    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public void exportToExcel(Map<String, Integer> productCountByCategory) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Produit par Catégorie");
            int rowCount = 0;

            // En-tête du tableau Excel
            Row headerRow = sheet.createRow(rowCount++);
            headerRow.createCell(0).setCellValue("Catégorie");
            headerRow.createCell(1).setCellValue("Nombre de Produits");

            // Ajouter les données dans le tableau Excel
            for (Map.Entry<String, Integer> entry : productCountByCategory.entrySet()) {
                Row row = sheet.createRow(rowCount++);
                String categoryName = getCategoryNameById(Integer.parseInt(entry.getKey())); // Obtenir le nom de la catégorie
                row.createCell(0).setCellValue(categoryName); // Nom de la catégorie
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Enregistrer le fichier Excel
            try (FileOutputStream outputStream = new FileOutputStream("products.xlsx")) {
                workbook.write(outputStream);
                System.out.println("Fichier Excel généré avec succès !");

                // Après avoir enregistré le fichier Excel, ouvrez-le directement
                openExcelFile("products.xlsx");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void openExcelFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Le fichier n'existe pas : " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  Map<String, Integer> getProductCountByCategory() throws SQLException {
        Map<String, Integer> productCountByCategory = new HashMap<>();

        // Établir la connexion à la base de données
        String sql = "SELECT idCategory, COUNT(*) AS count FROM produit GROUP BY idCategory";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        // Itérer sur les résultats pour récupérer les nombres de produits par catégorie
        while (resultSet.next()) {
            String category = resultSet.getString("idCategory");
            int count = resultSet.getInt("count");
            productCountByCategory.put(category, count);
        }


        return productCountByCategory;
    }




}
