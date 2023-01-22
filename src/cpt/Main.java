package cpt;

import java.io.*;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<cancer> groups = new ArrayList<>();
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
                year.add(Integer.parseInt(values[2],10));
                for(int count = 3; count < values.length; count++) {
                    values[count-3] = values[count];
                }
                for(int count = 0; count < values.length; count++) {
                    data.add(Integer.parseInt(values[count], 10));
                }
                records.add(data);
            }
            int[] dataArr = new int[records.size()];
            for(int count = 0; count < records.size(); count++) {
                dataArr = records.get(count).stream().mapToInt((i) -> i.intValue()).toArray();
                cancer newdata = new cancer(countries.get(count), codes.get(count), (int)year.get(count),dataArr);
                groups.add(newdata);
            }
            Set<String> setCountries = new HashSet<>(countries);
            countries.clear();
            countries.addAll(setCountries);
            Set<String> setCodes = new HashSet<>(codes);
            codes.clear();
            codes.addAll(setCodes);
            Set<Integer> setYear = new HashSet<>(year);
            year.clear();
            year.addAll(setYear);
            System.out.println(countries);
            System.out.println(codes);
            System.out.println((year));

        }
    }
    private LineChart LineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
 
    public Parent createLineChart() {
        xAxis = new NumberAxis("Years", 1990, 2020, 1);
        yAxis = new NumberAxis("Death by cancer", 0, 1000000, 10000);
        ObservableList<XYChart.Series<Integer,Integer>> lineChartData =
            FXCollections.observableArrayList(
                new LineChart.Series<>("Series 1",
                FXCollections.observableArrayList(
                    
                )),
                new LineChart.Series<>("Series 2",
                FXCollections.observableArrayList(
                ))
            );
        LineChart = new LineChart(xAxis, yAxis, lineChartData);
        return LineChart;
    }
 
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createLineChart()));
        primaryStage.show();
    } 
    
    public static int binarySearch_String(List<String> list, String key){  
        int low = 0;
        int high = list.size() - 1;
        while(low <= high){  
            int mid = (low + high)/2;
            int cur = key.compareTo(list.get(mid));
            if(cur == 0){   
                return mid;
            }
            else if(cur > 0){  
                low = mid + 1;     
            }
            else if(cur < 0){  
                high = mid - 1;  
            }
        }
        return -1;
    }
}