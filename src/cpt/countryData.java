package cpt;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class countryData {
    private BooleanProperty display;
    private String country;
    public countryData(boolean displayBool, String countryString)
    {
        display = new SimpleBooleanProperty(displayBool);
        country = countryString;
    }
    public BooleanProperty isDisplay() {
        return display;
    }
    public String getCountry() {
        return country;
    }
}
