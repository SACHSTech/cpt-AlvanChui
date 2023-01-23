package cpt;

public class countries_data {
    private boolean displayed;
    private String country;

    public countries_data(boolean displayedBool, String countryString) {
        displayed = displayedBool;
        country = countryString;
    }
    public boolean isDisplayed() {
        return displayed;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
