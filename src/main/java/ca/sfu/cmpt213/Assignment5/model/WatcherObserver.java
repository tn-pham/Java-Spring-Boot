package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;

public interface WatcherObserver {
    void notifyNewOffering(CSVLine line, String time);
}
