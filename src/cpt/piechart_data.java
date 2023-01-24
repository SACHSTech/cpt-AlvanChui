package cpt;

public class piechart_data {
    private String country;
    private int TotalD;

    public piechart_data(String countryString, int intTotalD) {
        country = countryString;
        TotalD = intTotalD;
    }
    public String getCountry() {
        return country;
    }
    public int isDisplayed() {
        return TotalD;
    }
}
