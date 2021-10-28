package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing data about offerings of each course during
 */
public class Offering implements Comparable<Offering> {
    private static int nextId = 0;

    private int courseOfferingId;
    private Location location;
    private List<Instructor> instructors = new ArrayList<>();
    private Semester semester;
    private List<SpecificClass> classes = new ArrayList<>();

    public Offering(CSVLine line) {
        this.courseOfferingId = nextId++;
        this.location = new Location(line);
        this.semester = new Semester(line);

        String instructorList[] = line.getInstructor().split(",");
        for(String name: instructorList) {
            addInstructor(name.trim());
        }

        addClass(line);
    }

    private void addInstructor(String name) {
        for(Instructor ins: instructors) {
            if(ins.getName().equals(name))
                return;
        }

        instructors.add(new Instructor(name));
        java.util.Collections.sort(instructors);
    }

    public int getCourseOfferingId() {
        return courseOfferingId;
    }

    public String getLocation() {
        return location.getLocation();
    }

    public String getInstructors() {
        String instructorList = instructors.get(0).getName();
        for(int i = 1; i < instructors.size(); i++) {
            instructorList += (", " + instructors.get(i).getName());
        }
        return instructorList;
    }

    public String getSemesterCode() {
        return semester.getSemesterCode();
    }

    public String getTerm() {
        return semester.getSemesterName();
    }

    public String getYear() {
        return "" + semester.getYear();
    }

    @JsonIgnore
    public List<SpecificClass> getClasses() {
        return classes;
    }

    public void addClass(CSVLine line) {
        for(SpecificClass c: classes) {
            if(c.getType().equals(line.getComponent())) {
                c.addEnrollmentTotal(line);
                c.addEnrollmentCap(line);
                return;
            }
        }
        classes.add(new SpecificClass(line));
        java.util.Collections.sort(classes);
    }

    public int getStudentCount(String semesterCode) {

        if(!semesterCode.equals(getSemesterCode()))
            return 0;

        int total = 0;
        for(SpecificClass c: classes) {
            if(c.getType().equals("LEC"))
                total += c.getEnrollmentTotal();
        }
        return total;
    }

    @Override
    public String toString() {
        String text = semester.getSemesterCode()
                + " in " + getLocation()
                + " by " + getInstructors() + "\n";

        for(SpecificClass c: classes) {
            text = text + "\t\t" + c.toString() + "\n";
        }
        return text;
    }

    @Override
    public int compareTo(Offering other) {
        return semester.getSemesterCode().compareTo(other.getSemesterCode());
    }
}
