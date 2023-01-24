package cpt;

import java.util.List;

public class cancer {
    private String country;
    private String code;
    private int year;
    private List<Integer> cancerData;
    
    public cancer(String countrysString, String codeString, int intYear, List<Integer> intArrCancerData){
        country = countrysString;
        code = codeString;
        year = intYear;
        cancerData = intArrCancerData;
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
    public List<Integer> getCancerData() {
        return cancerData;
    }
    public int getCancerData_Index(int Index) {
        return cancerData.get(Index);
    }
    public int getTotalDeath(){
        int sum = 0;
        for(int i = 0; i < cancerData.size(); i++){
            sum += cancerData.get(i);
        }
        return sum;
    }
    @Override
    public String toString() {
        String returnString = country + ", " + code + ", " + year + ", ";
        for(int count = 0 ; count < cancerData.size(); count++){
            returnString += cancerData.get(count);
            if(count < cancerData.size() - 1){
                returnString += ", ";
            }
        }
        return returnString;
    }
}