package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;
import ca.sfu.cmpt213.Assignment5.model.json.JSONOffering;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class storing data about course info
 */
public class Course implements Comparable<Course> {

    private static int nextId = 0;

    private int courseId;
    private String catalogNumber;
    private List<Offering> offerings = new ArrayList<>();

    public Course(CSVLine line) {
        this.courseId = nextId++;
        this.catalogNumber = line.getCatalogNumber();
        addOffering(line);
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    @JsonIgnore
    public List<JSONOffering> getOfferingsForJSON() {
        List<JSONOffering> ofs = new ArrayList<>();
        for(Offering o: offerings)
            ofs.add(new JSONOffering(o, courseId, catalogNumber));
        return ofs;
    }

    @JsonIgnore
    public List<Offering> getOfferings() {
        return offerings;
    }

    public void addOffering(CSVLine line) {
        for(Offering o: offerings) {
            if(o.getSemesterCode().equals(line.getSemester())
                && o.getLocation().equals(line.getLocation())) {
                    o.addClass(line);
                    return;
            }
        }

        offerings.add(new Offering(line));
        java.util.Collections.sort(offerings);
    }

    @JsonIgnore
    public int getOfferingCount() {
        return offerings.size();
    }

    @JsonIgnore
    public int getStudentCount(String semesterCode) {
        int total = 0;
        for(Offering o: offerings) {
            total += o.getStudentCount(semesterCode);
        }
        return total;
    }

    @JsonIgnore
    public List<String> getSemesterCode() {
        List<String> semesterCodes = new ArrayList<>();

        for(Offering o: offerings) {
            if(!semesterCodeFound(semesterCodes, o.getSemesterCode()))
                semesterCodes.add((o.getSemesterCode()));
        }

        return semesterCodes;
    }

    private boolean semesterCodeFound(List<String> semesterCodes, String code) {
        for(String sc: semesterCodes)
            if(sc.equals(code))
                return true;
        return false;
    }

    @Override
    public String toString() {
        String text = getCatalogNumber() + "\n";

        for(Offering o: offerings) {
            text = text + "\t" + o.toString();
        }
        return text;
    }

    @Override
    public int compareTo(Course other) {
        return catalogNumber.compareTo(other.getCatalogNumber());
    }
}
