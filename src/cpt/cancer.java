package cpt;

public class cancer {
    private String country;
    private String code;
    private int year;
    private int[] cancerData;
    
    public cancer(String countrysString, String codeString, int intYear, int[] intArrCancerData){
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
    public int[] getCancerData() {
        return cancerData;
    }
    public int getTotalDeath(){
        int sum = 0;
        for(int i = 0; i < cancerData.length; i++){
            sum += cancerData[i];
        }
        return sum;
    }
    @Override
    public String toString() {
        String returnString = country + ", " + code + ", " + year + ", ";
        for(int count = 0 ; count < cancerData.length; count++){
            returnString += cancerData[count];
            if(count < cancerData.length - 1){
                returnString += ", ";
            }
        }
        return returnString;
    }
}