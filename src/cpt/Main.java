package cpt;

import java.io.*;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.IndexRange;

public class Main {
    public static void main(String[] args) throws Exception{
        readDataLineByLine("src/csv/cancer-deaths-by-type-grouped.csv");
    }
    public static void readDataLineByLine(String file) throws Exception
    {
        ArrayList<cancer> groups = new ArrayList<>();
        List<List<Integer>> records = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<Integer> year = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line, legends;
            legends = br.readLine();
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
    
}