package ca.sfu.cmpt213.Assignment5.model.csv;

/**
 * Helper class to extract info from csv files
 */
public class CSVLine {

    private String semester;
    private String subjectName;
    private String catalogNumber;
    private String location;
    private String enrollmentCap;
    private String enrollmentTotal;
    private String instructor;
    private String component;

    public CSVLine(){
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEnrollmentCap(String enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void setEnrollmentTotal(String enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void setInstructors(String instructor) {
        this.instructor = instructor;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getEnrollmentCap() {
        return enrollmentCap;
    }

    public String getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getComponent() {
        return component;
    }

}
