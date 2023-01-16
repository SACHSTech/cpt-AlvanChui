package basic;
import java.io.*;
import com.opencsv.CSVReader;

public class cancer {
    public void cancer(String country, int Year,int Other,int Gallbladder,int Larynx,int OtherPharynx, int Kidney, int Ovarian,int Lip_oral_cavity,int Bladder, int Brain_nervous_system,int Non_Hodgkin_lymphoma,int Cervical,int Leukemia,int Prostate,int Pancreatic,int Esophageal, int Breast,int Liver,int Colon_rectum,int Stomach,int Tracheal_bronchus_lung) {
        
        
    }
    public static void readDataLineByLine(String file)
    {
        try{
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}