package ca.sfu.cmpt213.Assignment5.model.json;

import ca.sfu.cmpt213.Assignment5.model.Offering;

/**
 * Support class to interpret Offering info as JSON
 */
public class JSONOffering {
    private int courseOfferingId;
    private String location;
    private String instructors;
    private String semesterCode;
    private String term;
    private String year;
    private JSONCourse course;

    public JSONOffering(Offering offering, int courseId, String catalogNumber) {
        this.course = new JSONCourse(courseId, catalogNumber);
        this.courseOfferingId = offering.getCourseOfferingId();
        this.location = offering.getLocation();
        this.instructors = offering.getInstructors();
        this.semesterCode = offering.getSemesterCode();
        this.term = offering.getTerm();
        this.year = offering.getYear();
    }

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public String getTerm() {
        return term;
    }

    public String getYear() {
        return year;
    }

    public JSONCourse getCourse() {
        return course;
    }
}
