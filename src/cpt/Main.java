package cpt;

import java.io.*;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.IndexRange;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 


public class Main extends Application{
    public static void main(String[] args) throws Exception{
        readDataLineByLine("src/csv/cancer-deaths-by-type-grouped.csv");
        launch(args);
        
    }
    
    public static void readDataLineByLine(String file) throws Exception
    {
        ArrayList<cancer> groups = new ArrayList<>();
        List<List<Integer>> records = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<Integer> year = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line = br.readLine();
            String[] legends = line.split(",");
            while ((line = br.readLine()) != null) {
                List<Integer> data = new ArrayList<>();
                String[] values = line.split(",");
                countries.add(values[0]);
                codes.add(values[1]);
                year.add(Integer.parseInt(values[2],0,values[2].length(), 10));
                for(int count = 3; count < values.length; count++) {
                    values[count-3] = values[count];
                }
                for(int count = 0; count < values.length; count++) {
                    data.add(Integer.parseInt(values[count], 0, values[count].length(), 10));
                }
                records.add(data);
            }
            int[] dataArr = new int[records.size()];
            for(int count = 0; count < records.size(); count++) {
                dataArr = records.get(count).stream().mapToInt( (i) -> i.intValue()).toArray();
                cancer newdata = new cancer(countries.get(count), codes.get(count), (int)year.get(count),dataArr);
                groups.add(newdata);
            }
        }
    }
    private LineChart chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
 
    public Parent createLineChart() {
        xAxis = new NumberAxis("Years", 1990, 2020, 1);
        yAxis = new NumberAxis("Death by cancer", 0, 1000000, 1000);
        ObservableList<XYChart.Series<Double,Double>> lineChartData =
            FXCollections.observableArrayList(
                new LineChart.Series<>("Series 1",
                FXCollections.observableArrayList
                (
                    new XYChart.Data<>(0.0, 1.0),
                    new XYChart.Data<>(1.2, 1.4),
                    new XYChart.Data<>(2.2, 1.9),
                    new XYChart.Data<>(2.7, 2.3),
                    new XYChart.Data<>(2.9, 0.5)
                )),
                new LineChart.Series<>("Series 2", FXCollections.observableArrayList
                (
                    new XYChart.Data<>(0.0, 1.6),
                    new XYChart.Data<>(0.8, 0.4),
                    new XYChart.Data<>(1.4, 2.9),
                    new XYChart.Data<>(2.1, 1.3),
                    new XYChart.Data<>(2.6, 0.9)
                ))
            );
        chart = new LineChart(xAxis, yAxis, lineChartData);
        return chart;
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createLineChart()));
        primaryStage.show();
    }
 
    /**
     * Java main for when running without JavaFX launcher
     */
 
}