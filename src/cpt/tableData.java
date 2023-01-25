package cpt;

import java.util.List;

public class tableData {
    private String country;
    private String code;
    private int year;
    private List<Integer> cancerData;
    private int Other ,Gallbladder,Larynx,Other_pharynx,Kidney, Ovarian,Lip_n_oral_cavity,Bladder,Brain_nervous_system, Non_Hodgkin_lymphoma, Cervical, Leukemia, Prostate, Pancreatic, Esophageal, Breast, Liver, Colon_n_rectum, Stomach ,Tracheal_bronchus_lung;
    
    public tableData(cancer cancerData)
    {
        country = cancerData.getCountry();
        code = cancerData.getCode();
        year = cancerData.getYear();
        Other = cancerData.getCancerData_Index(0);
        Gallbladder = cancerData.getCancerData_Index(1);
        Larynx = cancerData.getCancerData_Index(2);
        Other_pharynx = cancerData.getCancerData_Index(3);
        Kidney = cancerData.getCancerData_Index(4);
        Ovarian = cancerData.getCancerData_Index(5);
        Lip_n_oral_cavity = cancerData.getCancerData_Index(6);
        Bladder = cancerData.getCancerData_Index(7);
        Brain_nervous_system = cancerData.getCancerData_Index(8);
        Non_Hodgkin_lymphoma = cancerData.getCancerData_Index(9);
        Cervical = cancerData.getCancerData_Index(10);
        Leukemia = cancerData.getCancerData_Index(11);
        Prostate = cancerData.getCancerData_Index(12);
        Pancreatic = cancerData.getCancerData_Index(13);
        Esophageal= cancerData.getCancerData_Index(14);
        Breast = cancerData.getCancerData_Index(15);
        Liver = cancerData.getCancerData_Index(16);
        Colon_n_rectum = cancerData.getCancerData_Index(17);
        Stomach = cancerData.getCancerData_Index(18);
        Tracheal_bronchus_lung = cancerData.getCancerData_Index(19);
    }
    public void setCancerData(int dataSlot, int i, List<Integer> dataSet){
        dataSlot = dataSet.get(i);
        i++;
    }
    public String getCountry() {
        return country;
    }
    public String getCode() {
        return code;
    }
    public int getYear() {
        return year;
    }
    public int getOther() {
        return Other;
    }
    public int getGallbladder() {
        return Gallbladder;
    }
    public int getLarynx() {
        return Larynx;
    }
    public int getOther_pharynx() {
        return Other_pharynx;
    }
    public int getKidney() {
        return Kidney;
    }
    public int getOvarian() {
        return Ovarian;
    }
    public int getLip_n_oral_cavity() {
        return Lip_n_oral_cavity;
    }
    public int getBladder() {
        return Bladder;
    }
    public int getBrain_nervous_system() {
        return Brain_nervous_system;
    }public int getNon_Hodgkin_lymphoma() {
        return Non_Hodgkin_lymphoma;
    }
    public int getCervical() {
        return Cervical;
    }
    public int getLeukemia() {
        return Leukemia;
    }public int getProstate() {
        return Prostate;
    }
    public int getPancreatic() {
        return Pancreatic;
    }public int getEsophageal() {
        return Esophageal;
    }public int getBreast() {
        return Breast;
    }public int getLiver() {
        return Liver;
    }public int getColon_n_rectum() {
        return Colon_n_rectum;
    }public int getStomach() {
        return Stomach;
    }public int getTracheal_bronchus_lung() {
        return Tracheal_bronchus_lung;
    }
    public List<Integer> getCancerData() {
        return cancerData;
    }
    public int getCancerData_Index(int Index) {
        return cancerData.get(Index);
    }
}