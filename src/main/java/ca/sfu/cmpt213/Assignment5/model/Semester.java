package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;

/**
 * Class storing data about semester and its meaning
 */
public class Semester implements Comparable<Semester> {
    private static final char SPRING = '1';
    private static final char SUMMER = '4';
    private static final char FALL = '7';
    private static final int START_YEAR = 1900;

    private String semesterCode;

    private Semester(String code) {
        this.semesterCode = code;
    }

    public Semester(CSVLine line) {
        this.semesterCode = line.getSemester();
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public String getSemesterName() {
        char termChar = semesterCode.charAt(semesterCode.length() - 1);

        if(termChar == SPRING)
            return "Spring";
        else if(termChar == SUMMER)
            return "Summer";
        else if(termChar == FALL)
            return "Fall";
        else
            return "";
    }

    public int getYear() {
        int yearInString = Integer.parseInt(semesterCode.substring(0, semesterCode.length() - 1));
        return yearInString + START_YEAR;
    }

    public static String convertSemesterCode(String semesterCode) {
        Semester semester = new Semester(semesterCode);
        return semester.getSemesterName() + " " + semester.getYear();
    }

    @Override
    public int compareTo(Semester other) {
        return semesterCode.compareTo(other.getSemesterCode());
    }
}
