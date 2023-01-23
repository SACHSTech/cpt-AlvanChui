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
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
    public static List<countries_data> countriesList = new ArrayList<>();
    public static String[] legends;
    public static void main(String[] args) throws Exception{
        try (BufferedReader br = new BufferedReader(new FileReader("src/csv/cancer-deaths-by-type-grouped.csv"))) {
            String line;
            line = br.readLine();
            legends = line.split(",");
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
                cancer newdata = new cancer(countries.get(count), codes.get(count), (int)years.get(count), dataArr);
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
            
            for(int i = 0; i < countries.size(); i++) {
                countries_data newCountry = new countries_data(true, countries.get(i));
                countriesList.add(newCountry);
            }
            launch(args);
        }
    }

    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;
    private Tab tab3;
    //public static List<CheckBox> countryCBList = new ArrayList<CheckBox>();
    
    
    public Parent createContent() {
        //initializing tabpane
        tabPane = new TabPane();
        tabPane.setPrefSize(400, 360);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        
        //table
        //TableView<List<String>> table = new TableView<>();
        //ObservableList<List<String>> tableData = loadTableDataFromDatabase();

        // Line Chart
        final List<Series> seriesList = new ArrayList<Series>(countries.size());
        final NumberAxis xAxis = new NumberAxis(legends[2], 1990, 2020, 1);
        final NumberAxis yAxis = new NumberAxis("Death from cancer", 0, 1000000, 50000);
        final LineChart<Number, Number> LineChart = new LineChart<Number,Number>(xAxis,yAxis);
        LineChart.setTitle("total death from cancer over the past decades");
        //mergeSort(codes, 0, codes.size()-1);
        
        //for(int i = 0; i < cancerList.size();i++) {
            //int result = binarySearch_String(codes, cancerList.get(i).getCode()); 
            // System.out.println(result);  
            //if(result != -1){
                //seriesList.get(result).getData().add(new XYChart.Data(cancerList.get(i).getYear(), cancerList.get(i).getTotalDeath()));
            //}
        //}
            //LineChart.getData().addAll(seriesList.get(1));
        
        //Pie Chart
        ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();
        
            PieChart pieChart = new PieChart();
            pieChart.setClockwise(false);
        
        //tab 1
        // tab1.setText("Database");
        // tab1.setContent();
        // tabPane.getTabs().add(tab1);
        
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

    public static void mergeSort(List<String> a, int from, int to) {
        if (from == to) {
            return;
        }
        int mid = (from + to) / 2;
        // sort the first and the second half
        mergeSort(a, from, mid);
        mergeSort(a, mid + 1, to);
        merge(a, from, mid, to);
    }// end mergeSort
//work

    public static void merge(List<String> a, int from, int mid, int to) {
        int n = to - from + 1;       // size of the range to be merged
        List<String> b = new ArrayList<>();   // merge both halves into a temporary array b
        int i1 = from;               // next element to consider in the first range
        int i2 = mid + 1;            // next element to consider in the second range
        int j = 0;                   // next open position in b

        // as long as neither i1 nor i2 past the end, move the smaller into b
        while (i1 <= mid && i2 <= to) {
            if (a.get(i1).compareTo(a.get(i2)) < 0) {
                b.add(j, a.get(i1));
                i1++;
            } else {
                b.add(j, a.get(i2));
                i2++;
            }
            j++;
        }

        // note that only one of the two while loops below is executed
        // copy any remaining entries of the first half
        while (i1 <= mid) {
            b.add(j, a.get(i1));
            i1++;
            j++;
        }

        // copy any remaining entries of the second half
        while (i2 <= to) {
            b.add(j, a.get(i2));
            i2++;
            j++;
        }

        // copy back from the temporary array
        for (j = 0; j < n; j++) {
            a.add(from + j, b.get(j));
        }
    }//end merge

    @Override
    public void start(Stage primaryStage){
        var group = new Group();
        group.setManaged(false);
        var pane = new StackPane(createContent(), group);
        primaryStage.setScene(new Scene(pane, 600 ,400));
        primaryStage.show();
    } 
}

