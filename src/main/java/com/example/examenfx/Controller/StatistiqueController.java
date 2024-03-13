package com.example.examenfx.Controller;

import com.example.examenfx.Repository.ProduitRepository;
import com.example.examenfx.Repository.StatistiqueRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;


import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StatistiqueController implements Initializable {
    private StatistiqueRepository statistiqueRepository;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Integer> BarChart;

    @FXML
    private CategoryAxis CategoryAxis;

    @FXML
    private NumberAxis NumberAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.statistiqueRepository = new StatistiqueRepository();
        Map<String, Integer> productCountByCategory = statistiqueRepository.getProductCountByCategory();

        // Convertir les données en une liste observable pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : productCountByCategory.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Définir les données pour le PieChart
        pieChart.setData(pieChartData);

        // Récupérer le nombre de produits ajoutés par mois en 2024
        Map<String, Integer> productCountByMonth = statistiqueRepository.getProductCountByMonth(2024);

        ObservableList<BarChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
        BarChart.Series<String, Integer> series = new BarChart.Series<>();
        for (Map.Entry<String, Integer> entry : productCountByMonth.entrySet()) {
            series.getData().add(new BarChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChartData.add(series);

        // Définir les données pour le BarChart
        BarChart.setData(barChartData);


    }


}
