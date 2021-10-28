package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;

/**
 * Class storing data about location of classes
 */
public class Location {
    private String location;

    public Location(CSVLine line) {
        if(line.getLocation() == null)
            this.location = "";
        else
            this.location = line.getLocation();
    }

    public String getLocation() {
        return location;
    }
}
