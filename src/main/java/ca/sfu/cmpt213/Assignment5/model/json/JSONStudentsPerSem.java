package ca.sfu.cmpt213.Assignment5.model.json;

/**
 * Support class to interpret Students per semester info as JSON
 */
public class JSONStudentsPerSem {
    private String semesterCode;
    private int totalCoursesTaken;

    public JSONStudentsPerSem(String semesterCode, int totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void addTotalCourses(int num) {
        totalCoursesTaken += num;
    }
}
