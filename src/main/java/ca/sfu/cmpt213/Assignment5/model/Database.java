package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;
import ca.sfu.cmpt213.Assignment5.model.csv.CSVTranslator;
import ca.sfu.cmpt213.Assignment5.model.exception.CourseNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.DepartmentNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.OfferingNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.WatcherNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.json.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Facade class
 */
public class Database {
    private static Database instance;
    private List<Department> departments;
    private List<WatcherObserver> watcherObservers = new ArrayList<>();
    private List<Watcher> watchers = new ArrayList<>();

    private Database() {
        CSVTranslator csv = new CSVTranslator();
        departments = csv.TranslateCSV();
        java.util.Collections.sort(departments);
    }

    public static Database getDatabase() {
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public int getDeptCount() {
        return departments.size();
    }

    public int getCourseCount() {
        int total = 0;
        for(Department d: departments)
            total += d.getCourseCount();

        return total;
    }

    public int getOfferingCount() {
        int total = 0;
        for(Department d: departments)
            total += d.getOfferingCount();

        return total;
    }

    public List<JSONStudentsPerSem> getStudentsPerSem(int departmentId) throws DepartmentNotFoundException {
        return getDepartment(departmentId).getStudentCount();
    }

    public void setWatcherInfo(int departmentId, int courseId, JSONDepartment department, JSONCourse course)
            throws DepartmentNotFoundException, CourseNotFoundException {

        Department d = getDepartment(departmentId);
        Course c = getCourse(courseId, d);

        department.setName(d.getName());
        department.setDeptId(departmentId);
        course.setCatalogNumber(c.getCatalogNumber());
        course.setCourseId(courseId);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Course> getCourses(int departmentId) throws DepartmentNotFoundException {
        return getDepartment(departmentId).getCourses();
    }

    //Doesn't have to be like this but I just want to try the optional requirement
    //So code is a bit repeatable in below due to the database structure
    public List<JSONOffering> getOfferingsAsJSON(int departmentId, int courseId)
            throws DepartmentNotFoundException, CourseNotFoundException {

        Department department = getDepartment(departmentId);
        return getCourse(courseId, department).getOfferingsForJSON();
    }

    public List<Offering> getOfferings(int departmentId, int courseId)
            throws DepartmentNotFoundException, CourseNotFoundException {

        Department department = getDepartment(departmentId);
        return getCourse(courseId, department).getOfferings();
    }

    private Department getDepartment(int departmentId) throws DepartmentNotFoundException {
        for(Department d: departments)
            if(d.getDeptId() == departmentId)
                return d;

        throw new DepartmentNotFoundException("Department of ID " + departmentId + " not found.");
    }

    private Course getCourse(int courseId, Department department) throws CourseNotFoundException {
        List<Course> courses = department.getCourses();
        for(Course c: courses)
            if(c.getCourseId() == courseId)
                return c;

        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }

    public List<SpecificClass> getClasses(int departmentId, int courseId, int offeringId)
            throws DepartmentNotFoundException, CourseNotFoundException, OfferingNotFoundException {

        List<Offering> offeringList = getOfferings(departmentId, courseId);
        for(Offering o: offeringList)
            if(o.getCourseOfferingId() == offeringId)
                return o.getClasses();

        throw new OfferingNotFoundException("Offering of ID " + offeringId + " not found.");
    }

    public void addOffering(CSVLine line) {
        CSVTranslator translator = new CSVTranslator();
        translator.addDepartment(line, departments);
        java.util.Collections.sort(departments);

        String event = new Date()
                + ": Added section " + line.getComponent()
                + " with enrollment (" + line.getEnrollmentTotal()
                + "/" + line.getEnrollmentCap()
                + ") to offering " + Semester.convertSemesterCode(line.getSemester());

        notifyWatchers(line, event);
    }

    @Override
    public String toString() {
        String text = "";

        for(Department d: departments)
            text += d.toString();

        return text;
    }

    public void addWatcher(WatcherObserver watcher) {
        watcherObservers.add(watcher);
    }

    public void addWatcher(Watcher watcher) {
        watchers.add(watcher);
    }

    public List<Watcher> getWatchers() {
        return watchers;
    }

    public void notifyWatchers(CSVLine line, String event) {
        for(WatcherObserver watcher: watcherObservers)
            watcher.notifyNewOffering(line, event);
    }

    public List<String> getChanges(int watcherId) throws WatcherNotFoundException {
        for(Watcher w: watchers)
            if(w.getId() == watcherId)
                return w.getEvents();

        throw new WatcherNotFoundException("Unable to find requested watcher.");
    }

    public void deleteWatcher(int watcherId) {
        for(int i = 0; i < watchers.size(); i++) {
            if (watchers.get(i).getId() == watcherId) {
                watchers.remove(i);
                watcherObservers.remove(i);
            }
        }
    }
}
