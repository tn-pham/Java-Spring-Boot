package ca.sfu.cmpt213.Assignment5.model;

import ca.sfu.cmpt213.Assignment5.model.exception.CourseNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.DepartmentNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.json.JSONCourse;
import ca.sfu.cmpt213.Assignment5.model.json.JSONDepartment;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to build Watchers
 */
public class Watcher {
    private static int nextId = 0;

    private int id;
    private JSONDepartment department = new JSONDepartment();
    private JSONCourse course = new JSONCourse();
    private List<String> events = new ArrayList<>();

    public Watcher(int deptId, int courseId) throws CourseNotFoundException, DepartmentNotFoundException {
        id = nextId++;

        Database database = Database.getDatabase();
        database.setWatcherInfo(deptId, courseId, department, course);
    }

    public int getId() {
        return id;
    }

    public JSONDepartment getDepartment() {
        return department;
    }

    public JSONCourse getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void addEvent(String event) {
        events.add(event);
    }
}
