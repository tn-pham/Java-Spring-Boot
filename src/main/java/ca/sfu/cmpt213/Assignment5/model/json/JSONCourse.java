package ca.sfu.cmpt213.Assignment5.model.json;

/**
 * Support class to interpret Course info as JSON
 */
public class JSONCourse {
    private int courseId;
    private String catalogNumber;

    public JSONCourse() {
    }

    public JSONCourse(int courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
