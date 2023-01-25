package cpt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.event.MouseInputListener;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
import javafx.css.converter.StringConverter;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Main extends Application
{
    //static lists of data
    public static List<List<Integer>> records = new ArrayList<>();
    public static List<String> countries = new ArrayList<>();
    public static List<String> codes = new ArrayList<>();
    public static List<Integer> years = new ArrayList<>();
    public static List<cancer> cancerList = new ArrayList<>();
    public static List<countryData> countryDataList = new ArrayList<>();
    public static String[] legends;

    public static void main(String[] args) throws Exception
    {
        //read data from csv
        try (BufferedReader br = new BufferedReader(new FileReader("src/csv/modified-cancer-deaths-by-type-grouped.csv"))) 
        {
            String line;
            line = br.readLine();
            legends = line.split(",");
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
        //call cancer constructor
        for(int count = 0; count < records.size(); count++) 
        {
            cancer newdata = new cancer(countries.get(count), codes.get(count), (int)years.get(count), records.get(count));
            cancerList.add(newdata);
        }
        //remove duplicates
        Set<String> setCountries = new HashSet<>(countries);
        countries.clear();
        countries.addAll(setCountries);
        Set<String> setCodes = new HashSet<>(codes);
        codes.clear();
        codes.addAll(setCodes);
        Set<Integer> setYear = new HashSet<>(years);
        years.clear();
        years.addAll(setYear);
        //call countryData constructor
        for(int i = 0; i < countries.size(); i++)
        {
            countryData newCountryData = new countryData(true, countries.get(i));
            countryDataList.add(newCountryData);
        }
        //launch data visualization
        launch(args);
        
    }
    //tabpane
    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;
    private Tab tab3;
    //define line chart, xy axes
    NumberAxis xAxis = new NumberAxis(legends[2], 1990, 2016, 1);
    NumberAxis yAxis = new NumberAxis("Deaths", 0, 3480000, 20000);
    LineChart<Number, Number> LineChart = new LineChart<Number,Number>(xAxis,yAxis);
    List<CheckBox> chkbList = new ArrayList<>();
    //define piechart/ comboBox
    PieChart pieChart;
    ComboBox comboBox;
    
    public Parent createContent() 
    {
        //initializing tabpane
        tabPane = new TabPane();
        tabPane.setPrefSize(750, 1000);
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        //clone cancer data list without array values
        List<tableData> tableList = new ArrayList<>();
        for(int i = 0; i < cancerList.size(); i++)
        {
            tableData newTableData = new tableData(cancerList.get(i)); 
            tableList.add(newTableData);
        }
        
        //table of data of different countries
        ObservableList<tableData> tData = FXCollections.observableList(tableList);
        List<TableColumn> columns = new ArrayList<>();
        String[] legendsReference = {"country", "code", "year", "Other" ,"Gallbladder","Larynx","Other_pharynx","Kidney", "Ovarian","Lip_n_oral_cavity","Bladder","Brain_nervous_system", "Non_Hodgkin_lymphoma", "Cervical", "Leukemia", "Prostate", "Pancreatic", "Esophageal", "Breast", "Liver", "Colon_n_rectum", "Stomach" ,"Tracheal_bronchus_lung"};
        //columns
        for(int i = 0; i < legendsReference.length;i++)
        {
            TableColumn col = new TableColumn();
            System.out.println(legends[i] + " " + legendsReference[i]);
            col.setText(legends[i]);
            col.setCellValueFactory(new PropertyValueFactory(legendsReference[i]));
            columns.add(i, col);
        }
        final TableView tableView = new TableView();
        tableView.setItems(tData);
        for(int i = 0; i < columns.size(); i++)
        tableView.getColumns().addAll(columns.get(i));
        

        // Line Chart
        LineChart.setTitle("total death from cancer over the past decades");
        List<Series<Number,Number>> seriesList = new ArrayList<>();


        // for(int i = 0; i < countries.size();i++)
        // {
        //     //create a list of series that stores the series of each country
        //     Series newSeries = new XYChart.Series();
        //     newSeries.setName(countries.get(i));
        //     seriesList.add(newSeries);
        // }
        // //read data from the constructor objects
        // int resultIndex;
        // for(int i = 0; i < cancerList.size();i++)
        // {
        //     resultIndex = codes.indexOf(cancerList.get(i).getCode());
        //     seriesList.get(resultIndex).getData().add(new XYChart.Data(cancerList.get(i).getYear(), cancerList.get(i).getTotalDeath()));
        // }
        // //get all of the series
        // for(int n = 0; n < countries.size();n++)
        // {
        //     LineChart.getData().addAll(seriesList.get(n));
        // }
        
        chkbList = new ArrayList<>();
        for (int i = 0; i < countries.size();i++){
            CheckBox chk = new CheckBox(countries.get(i));
            chk.setSelected(true);
            chkbList.add(i, chk);
        }
        refreshLineChart();

        //Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        //read data from constructor
        int[] SumByType = new int[cancerList.size()-3];
        for(int i = 0; i < cancerList.size();i++){
            for(int dataIndex = 3; dataIndex <cancerList.get(i).getCancerData().size(); dataIndex++)
            {
                SumByType[dataIndex-3] += cancerList.get(i).getCancerData_Index(dataIndex-3);
            }
        }
        //add data to pie chart
        for(int i = 3; i < legends.length; i++)
        {
            pieChartData.add(new PieChart.Data(legends[i], (double)SumByType[i-3]));
        }
        //generate piechart
        pieChart = new PieChart(pieChartData);
        pieChart.setClockwise(false);
        
        

        //tab 1: table
        tab1.setText("Database");
        tab1.setContent(tableView);
        tabPane.getTabs().add(tab1);
        
        //tab 2 layout
        VBox vbox = new VBox(3);
        vbox.maxHeight(tabPane.getHeight());
        vbox.getChildren().addAll(chkbList);
        final BorderPane borderPane_line = new BorderPane();
        borderPane_line.setCenter(LineChart);
        borderPane_line.setRight(vbox);
        //tab 2: line chart
        tab2.setText("Line Chart");
        tab2.setContent(borderPane_line);
        tabPane.getTabs().add(tab2);
            
        //clone countries as array and sort it
        List<String> countriesClone = new ArrayList<>();
        countriesClone.add(0, "Worldwide");
        countriesClone.addAll(countries);
        String[] countriesArr = countriesClone.toArray(new String[countriesClone.size()]);
        selectionSort(countriesArr, 1, countriesArr.length);
        ObservableList<String> options = FXCollections.observableArrayList(countriesArr);
        
        //tab 3 layout
        Label label = new Label("Select country:");
        final BorderPane borderPane_pie = new BorderPane();
        final HBox hbox = new HBox(10);
        hbox.setTranslateX(8);
        hbox.setTranslateY(5);
        comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        hbox.getChildren().addAll(label, comboBox);
        hbox.setAlignment(Pos.CENTER_LEFT);
        borderPane_pie.setTop(hbox);
        borderPane_pie.setCenter(pieChart);
        
        //tab 3: pie chart
        tab3.setText("Pie Chart");
        tab3.setContent(borderPane_pie);
        tabPane.getTabs().add(tab3);

        for (int i = 0; i < chkbList.size(); i++)
        {
            int value = i;
            chkbList.get(i).selectedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    refreshLineChart();
                    System.out.println(newValue);
                }
            });
        }
        comboBox.valueProperty().addListener(new ChangeListener<String>()
        {
            @Override 
            public void changed(ObservableValue ov, String oldValue, String newValue)
            {
                refreshPieChart(newValue);
            }    
        });

        return tabPane;
    }
    /**
     * updates the line chart when called
  
     */
    public void refreshLineChart()
    {
        LineChart.getData().clear();
        List<Series<Number,Number>> seriesList = new ArrayList<>();
        List<Series<Number,Number>> copyseriesList = new ArrayList<>();


        for (int i = 0; i < countries.size();i++){
            Series newSeries = new XYChart.Series();
            seriesList.add(newSeries);  
        }        
        int resultIndex;
        for(int i = 0; i < cancerList.size();i++)
        {
            resultIndex = codes.indexOf(cancerList.get(i).getCode());
            seriesList.get(resultIndex).getData().add(new XYChart.Data(cancerList.get(i).getYear(), cancerList.get(i).getTotalDeath()));
        }

        Boolean[] checkBoxSelected = new Boolean[countries.size()];        
        int k = 0;
        for (int i = 0; i < countries.size();i++){
            checkBoxSelected[i] = chkbList.get(i).isSelected();
            System.out.println(countries.get(i) + " : " + checkBoxSelected[i]);


            if (checkBoxSelected[i])
            {
                copyseriesList.add(seriesList.get(i));
                copyseriesList.get(k).setName(countries.get(i));
                k++;
            }
        }
        for(int n = 0; n < countries.size();n++)
        {
            LineChart.getData().addAll(copyseriesList.get(n));
        }

    }
    /**
     * updates the pie chart when called
     * @param newCountry stores the value of the selected country
     */
    public void refreshPieChart(String newCountry)
    {
        int[] SumByType1 = new int[cancerList.size()-3];
        for(int i = 0; i < cancerList.size();i++){
            for(int dataIndex = 3; dataIndex <cancerList.get(i).getCancerData().size(); dataIndex++)
            {
//                System.out.println(cancerList.get(i).getCountry());
                if(newCountry == "Worldwide" || cancerList.get(i).getCountry() == newCountry)
                {
                    SumByType1[dataIndex-3] += cancerList.get(i).getCancerData_Index(dataIndex-3);
                }
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(int i = 3; i < legends.length; i++)
        {
            pieChartData.add(new PieChart.Data(legends[i], (double)SumByType1[i-3]));
        }

        pieChart.setData(pieChartData);
    }
/**
 * 
 * @param array unsorted array of String values
 * @param start Starting index
 * @param end Ending index
 */
public static void selectionSort(String[] array, int start, int end)
{
  

  for (int j = start; j < end-1; j++)
  {
    // Look through the array and find the min value
    int min = j;
    for(int k = j + 1; k < end; k++)
        if(array[k].compareTo(array[min]) < 0) 
        {
            min = k;
        }  

    // Swap values of array[j] & array[min] 
    String temp = array[j];
    array[j] = array[min];
    array[min] = temp;
  }
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

