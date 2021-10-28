package ca.sfu.cmpt213.Assignment5.model.csv;

import ca.sfu.cmpt213.Assignment5.Application;
import ca.sfu.cmpt213.Assignment5.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to extract csv files
 */
public class CSVTranslator {

    private final int INDEX_SEMESTER = 0;
    private final int INDEX_SUBJECT = 1;
    private final int INDEX_CATALOG_NUMBER = 2;
    private final int INDEX_LOCATION = 3;
    private final int INDEX_ENROLMENT_CAPACITY = 4;
    private final int INDEX_ENROLMENT_TOTAL = 5;
    private final int INDEX_INSTRUCTORS = 6;
    private final int INDEX_COMPONENT_CODE = 7;

    private final int QUOTE_SPLIT_SIZE = 3;
    private final int QUOTE_SPLIT_INDEX_INSTRUCTORS = 1;
    private final int QUOTE_SPLIT_INDEX_COMPONENT_CODE = 2;

    private String FILES_PATH = "data/";
    private String FILE_EXTENSION = ".csv";

    File[] fileList;
    List<Department> departments = new ArrayList<>();
    CSVLine lineInCSV = new CSVLine();

    public CSVTranslator(){
    }

    public List<Department> TranslateCSV() {
        loadFiles();
        translateAllFiles();

        return departments;
    }

    private void loadFiles() {
        File folder = new File(FILES_PATH);
        fileList = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(FILE_EXTENSION);
            }
        });
    }

    private void translateAllFiles() {
        for (File file : fileList) {
            translate1File(file);
        }
    }

    private void translate1File(File file) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            //omit first line
            bf.readLine();

            String line;
            while((line = bf.readLine()) != null) {
                readCSVLine(line);
                addDepartment(lineInCSV, departments);
            }

            bf.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("ERROR: File not found");
            System.exit(Application.ERROR_EXIT_CODE);
        }
        catch(IOException ex) {
            System.out.println("ERROR: Cannot read file");
            System.exit(Application.ERROR_EXIT_CODE);
        }
        catch(NumberFormatException ex) {
            System.out.println("ERROR: Wrong number format");
            System.exit(Application.ERROR_EXIT_CODE);
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println("ERROR: CSV file has wrong format");
            System.exit(Application.ERROR_EXIT_CODE);
        }
    }

    private void readCSVLine(String line) {

        String commaSplit[] = line.split(",");
        lineInCSV.setSemester(commaSplit[INDEX_SEMESTER].trim());
        lineInCSV.setSubjectName(commaSplit[INDEX_SUBJECT].trim());
        lineInCSV.setCatalogNumber(commaSplit[INDEX_CATALOG_NUMBER].trim());
        lineInCSV.setLocation(commaSplit[INDEX_LOCATION].trim());
        lineInCSV.setEnrollmentCap(commaSplit[INDEX_ENROLMENT_CAPACITY].trim());
        lineInCSV.setEnrollmentTotal(commaSplit[INDEX_ENROLMENT_TOTAL].trim());

        //multiple instructors
        String quoteSplit[] = line.split("\"");
        if(quoteSplit.length == QUOTE_SPLIT_SIZE) {
            lineInCSV.setInstructors(quoteSplit[QUOTE_SPLIT_INDEX_INSTRUCTORS].trim());
            lineInCSV.setComponent(quoteSplit[QUOTE_SPLIT_INDEX_COMPONENT_CODE].substring(1).trim());  //remove first ","
        }
        else {
            lineInCSV.setInstructors(commaSplit[INDEX_INSTRUCTORS].trim());
            lineInCSV.setComponent(commaSplit[INDEX_COMPONENT_CODE].trim());
        }
    }

    public void addDepartment(CSVLine lineInCSV, List<Department> departments) {
        for(Department d: departments) {
            if(lineInCSV.getSubjectName().equals(d.getName())) {
                d.addCourse(lineInCSV);
                return;
            }
        }
        departments.add(new Department(lineInCSV));
    }
}
