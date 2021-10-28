package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;
import ca.sfu.cmpt213.Assignment5.model.json.JSONStudentsPerSem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing data about department info
 */
public class Department implements Comparable<Department> {

    private static int nextId = 0;

    private int deptId;
    private String name;
    private List<Course> courses = new ArrayList<>();

    public Department(CSVLine line) {
        this.deptId = nextId++;
        this.name = line.getSubjectName();
        addCourse(line);
    }

    public int getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public List<Course> getCourses() {
        return courses;
    }

    @JsonIgnore
    public int getCourseCount() {
        return courses.size();
    }

    @JsonIgnore
    public int getOfferingCount() {
        int total = 0;
        for(Course c: courses) {
            total += c.getOfferingCount();
        }
        return total;
    }

    public void addCourse(CSVLine line) {
        for(Course c: courses) {
            if (c.getCatalogNumber().equals(line.getCatalogNumber())) {
                c.addOffering(line);
                return;
            }
        }

        courses.add(new Course(line));
        java.util.Collections.sort(courses);
    }

    @JsonIgnore
    public List<String> getSemesterCodes() {
        List<String> semesterCodes = new ArrayList<>();

        for(Course c: courses) {
            List<String> codes = c.getSemesterCode();
            for(String s: codes)
                if(!semesterCodeFound(semesterCodes, s))
                    semesterCodes.add(s);
        }

        return semesterCodes;
    }

    private boolean semesterCodeFound(List<String> semesterCodes, String code) {
        for(String sc: semesterCodes)
            if(sc.equals(code))
                return true;
        return false;
    }

    @JsonIgnore
    public List<JSONStudentsPerSem> getStudentCount() {
        List<JSONStudentsPerSem> studentCounts = new ArrayList<>();
        List<String> semesterCodes = getSemesterCodes();

        for(String sc: semesterCodes) {
            int total = 0;
            for(Course c: courses) {
                total += c.getStudentCount(sc);
            }
            studentCounts.add(new JSONStudentsPerSem(sc, total));
        }

        return studentCounts;
    }

    @Override
    public String toString() {
        String text = "";

        for(Course c: courses) {
            text = text + getName() + " " + c.toString();
        }
        return text;
    }

    @Override
    public int compareTo(Department other) {
        return name.compareTo(other.getName());
    }
}
