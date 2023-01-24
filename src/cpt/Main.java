package cpt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 


public class Main extends Application
{
    public static List<List<Integer>> records = new ArrayList<>();
    public static List<String> countries = new ArrayList<>();
    public static List<String> codes = new ArrayList<>();
    public static List<Integer> years = new ArrayList<>();
    public static List<cancer> cancerList = new ArrayList<>();
    public static List<piechart_data> countriesList = new ArrayList<>();
    public static String[] legends;
    public static String cancerTypes = "Death [";
    public static void main(String[] args) throws Exception
    {
        try (BufferedReader br = new BufferedReader(new FileReader("src/csv/modified-cancer-deaths-by-type-grouped.csv"))) 
        {
            String line;
            line = br.readLine();
            legends = line.split(",");
            for(int i = 3; i < legends.length; i++)
            {
                cancerTypes += legends[i] + ",";
            }
            cancerTypes+= "]";
            while ((line = br.readLine()) != null) 
            {
                List<Integer> data = new ArrayList<>();
                String[] values = line.split(",");
                countries.add(values[0]);
                codes.add(values[1]);
                years.add(Integer.parseInt(values[2],10));
                for(int count = 3; count < values.length; count++) 
                {
                    values[count-3] = values[count];
                }
                for(int count = 0; count < values.length; count++) 
                {
                    data.add(Integer.parseInt(values[count], 10));
                }
                records.add(data);
            }
        }
        try (BufferedReader br_ww = new BufferedReader(new FileReader("src/csv/worldwide-cancer-deaths-by-type-grouped.csv"))) 
        {
            int[] worldwideData;
            String nextline;
            while ((nextline = br_ww.readLine()) != null) 
            {
                String[] values = nextline.split(",");
                for (int i = 3; i < values.length; i++)
                {
                    worldwideData
                }
            }
        }
            for(int count = 0; count < records.size(); count++) 
            {
                cancer newdata = new cancer(countries.get(count), codes.get(count), (int)years.get(count), records.get(count));
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
            
            launch(args);
        
    }

    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;
    private Tab tab3;
    
    
    public Parent createContent() 
    {
        //initializing tabpane
        tabPane = new TabPane();
        tabPane.setPrefSize(400, 360);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        
        //table
        ObservableList<cancer> data = FXCollections.observableList(cancerList);
        TableColumn ColCountry = new TableColumn();
        ColCountry.setText(legends[0]);
        ColCountry.setCellValueFactory(new PropertyValueFactory("country"));
        TableColumn ColCode = new TableColumn();
        ColCode.setText(legends[1]);
        ColCode.setCellValueFactory(new PropertyValueFactory("code"));
        TableColumn ColYear = new TableColumn();
        ColYear.setText(legends[2]);
        ColYear.setCellValueFactory(new PropertyValueFactory("year"));
        TableColumn ColCancerData = new TableColumn();
        ColCancerData.setText(cancerTypes);
        ColCancerData.setCellValueFactory(new PropertyValueFactory("cancerData"));
        final TableView tableView = new TableView();
        tableView.setItems(data);
        tableView.getColumns().addAll(ColCountry, ColCode, ColYear, ColCancerData);
        

        // Line Chart
        final List<Series> seriesList = new ArrayList<Series>();
        final NumberAxis xAxis = new NumberAxis(legends[2], 1990, 2020, 1);
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> LineChart = new LineChart<Number,Number>(xAxis,yAxis);
        LineChart.setTitle("total death from cancer over the past decades");
        
        for(int i = 0; i < countries.size();i++)
        {
            Series newSeries = new XYChart.Series();
            newSeries.setName(countries.get(i));
            seriesList.add(newSeries);
        }
        int resultIndex;
        for(int i = 0; i < cancerList.size();i++)
        {
            resultIndex = codes.indexOf(cancerList.get(i).getCode());
            seriesList.get(resultIndex).getData().add(new XYChart.Data(cancerList.get(i).getYear(), cancerList.get(i).getTotalDeath()));
        }
        for(int n = 0; n < countries.size();n++)
        {
            LineChart.getData().addAll(seriesList.get(n));
        }
        
        //Pie Chart
        int[] SumByType = new int[cancerList.size()-3];
        for(int i = 0; i < cancerList.size();i++){
            for(int dataIndex = 3; dataIndex <cancerList.get(i).getCancerData().size(); dataIndex++)
            {
                SumByType[dataIndex-3] += cancerList.get(i).getCancerData_Index(dataIndex);
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(int i = 0; i < legends.length; i++)
        {
            new PieChart.Data(legends[i], (double)SumByType[i]);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setClockwise(false);
        
        new PieChart.Data("Sun", 20)
        //tab 1
        tab1.setText("Database");
        tab1.setContent(tableView);
        tabPane.getTabs().add(tab1);
        
        //tab 2
        tab2.setText("Line Chart");
        tab2.setContent(LineChart);
        tabPane.getTabs().add(tab2);
            
        //tab 3
        tab3.setText("Pie Chart");
        tab3.setContent(pieChart);
        tabPane.getTabs().add(tab3);

        return tabPane;
    }


    @Override
    public void start(Stage primaryStage)
    {
        var group = new Group();
        group.setManaged(false);
        var pane = new StackPane(createContent(), group);
        primaryStage.setScene(new Scene(pane, 1200 ,720));
        primaryStage.resizableProperty();
        primaryStage.show();
    } 
}

