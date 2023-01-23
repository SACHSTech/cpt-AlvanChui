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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 


public class Main extends Application{
    public static List<List<Integer>> records = new ArrayList<>();
    public static List<String> countries = new ArrayList<>();
    public static List<String> codes = new ArrayList<>();
    public static List<Integer> years = new ArrayList<>();
    public static List<cancer> cancerList = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        try (BufferedReader br = new BufferedReader(new FileReader("src/csv/cancer-deaths-by-type-grouped.csv"))) {
            String line;
            line = br.readLine();
            String[] legends = line.split(",");
            while ((line = br.readLine()) != null) {
                List<Integer> data = new ArrayList<>();
                String[] values = line.split(",");
                countries.add(values[0]);
                codes.add(values[1]);
                years.add(Integer.parseInt(values[2],10));
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
                cancer newdata = new cancer(countries.get(count), codes.get(count), (int)years.get(count),false, dataArr);
                cancerList.add(newdata);
            }
            Set<String> setCountries = new HashSet<>(countries);
            countries.clear();
            countries.addAll(setCountries);
            Set<String> setCodes = new HashSet<>(codes);
            codes.clear();
            codes.addAll(setCodes);
            Set<Integer> setYear = new HashSet<>(years);
            years.clear();
            years.addAll(setYear);
            System.out.println(countries);
            System.out.println(codes);
            System.out.println((years));
            launch(args);        
        }
    }
    @Override 
    public void start(Stage primaryStage){
        var group = new Group();
        group.setManaged(false);
        var pane = new StackPane(createLineChart_TotalDeath(countries, codes, years), group);
        primaryStage.setScene(new Scene(pane, 600 ,400));
        primaryStage.show();
    } 
    private LineChart LineChart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
 
    public Parent createLineChart_TotalDeath(List<String> country,List<String> code,List<Integer> year) {
        xAxis = new NumberAxis("Years", 1990, 2020, 1);
        yAxis = new NumberAxis("Death by cancer", 0, 1000000, 50000);
        int n = 1;
        ObservableList<XYChart.Series<Number,Number>> lineChartData =
            FXCollections.observableArrayList(
                new LineChart.Series<>(cancerList.get(n).getCode(),
                FXCollections.observableArrayList()),
                new LineChart.Series<>("Series 2",
                FXCollections.observableArrayList(
                
                )
                )
            ); 
            new XYChart.Data<>(cancerList.get(n).getYear(),cancerList.get(n).getTotalDeath())
        LineChart = new LineChart(xAxis, yAxis, lineChartData);
        return LineChart;
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