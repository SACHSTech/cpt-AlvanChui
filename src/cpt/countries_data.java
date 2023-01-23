package cpt;

public class countries_data {
    private int TotalD;
    private String country;

    public countries_data(int intTotalD, String countryString) {
        TotalD = intTotalD;
        country = countryString;
    }
    public int isDisplayed() {
        return TotalD;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
