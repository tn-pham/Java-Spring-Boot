package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;

/**
 * Class storing data about the class info
 */
public class SpecificClass implements Comparable<SpecificClass> {
    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public SpecificClass(CSVLine line) {
        this.type = line.getComponent();
        this.enrollmentCap = Integer.parseInt(line.getEnrollmentCap());
        this.enrollmentTotal = Integer.parseInt(line.getEnrollmentTotal());
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void addEnrollmentTotal(CSVLine line) {
        int num = Integer.parseInt(line.getEnrollmentTotal());
        enrollmentTotal += num;
    }

    public void addEnrollmentCap(CSVLine line) {
        int num = Integer.parseInt(line.getEnrollmentCap());
        enrollmentCap += num;
    }

    @Override
    public String toString() {
        return "Type=" + type
                + ", Enrollment=" + enrollmentTotal
                + "/" + enrollmentCap;
    }

    @Override
    public int compareTo(SpecificClass other) {
        return type.compareTo(other.getType());
    }
}
