package ca.sfu.cmpt213.Assignment5.controllers;

import ca.sfu.cmpt213.Assignment5.model.*;
import ca.sfu.cmpt213.Assignment5.model.csv.CSVLine;
import ca.sfu.cmpt213.Assignment5.model.exception.CourseNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.DepartmentNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.OfferingNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.exception.WatcherNotFoundException;
import ca.sfu.cmpt213.Assignment5.model.json.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  Controller to dump database
 */

@RestController
public class CoursePlanner {

    Database database = Database.getDatabase();

    @GetMapping("/api/dump-model")
    public String getDumpModel() {
        String result;

        result =  ("DEPT=" + database.getDeptCount() + "\n");
        result += ("COURSE=" + database.getCourseCount() + "\n");
        result += ("OFFERING=" + database.getOfferingCount() + "\n");

        System.out.println(database);

        return result;
    }

    @GetMapping("/api/about")
    public JSONAbout getAbout() {
        return new JSONAbout();
    }

    @GetMapping("/api/departments")
    public List<Department> getDepartments() {
        return database.getDepartments();
    }

    @GetMapping("/api/departments/{dId}/courses")
    public List<Course> getCourses(
            @PathVariable("dId") int dId)
            throws DepartmentNotFoundException {

        return database.getCourses(dId);
    }

    @GetMapping("/api/departments/{dId}/courses/{cId}/offerings")
    public List<JSONOffering> getOfferings(
            @PathVariable("dId") int dId,
            @PathVariable("cId") int cId)
            throws DepartmentNotFoundException, CourseNotFoundException {

        return database.getOfferingsAsJSON(dId, cId);
    }

    @GetMapping("/api/departments/{dId}/courses/{cId}/offerings/{oId}")
    public List<SpecificClass> getClasses(
            @PathVariable("dId") int dId,
            @PathVariable("cId") int cId,
            @PathVariable("oId") int oId)
            throws DepartmentNotFoundException, CourseNotFoundException, OfferingNotFoundException {

        return database.getClasses(dId, cId, oId);
    }

    @GetMapping("/api/stats/students-per-semester")
    public List<JSONStudentsPerSem> getStudentsPerSemester(
            @RequestParam(value = "deptId") int deptId)
            throws DepartmentNotFoundException {

        return database.getStudentsPerSem(deptId);
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SpecificClass addOffering(@RequestBody CSVLine newOffering) {
        database.addOffering(newOffering);
        return new SpecificClass(newOffering);
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Watcher addWatcher(
            @RequestBody JSONWatcher jsonWatcher)
            throws CourseNotFoundException, DepartmentNotFoundException {

        int deptId = jsonWatcher.getDeptId();
        int courseId = jsonWatcher.getCourseId();

        Watcher watcher = new Watcher(deptId, courseId);
        database.addWatcher(watcher);
        database.addWatcher((line, event) -> notifyWatchers(line, watcher, event));

        return watcher;
    }

    private void notifyWatchers(CSVLine line, Watcher watcher, String event) {
        String lineDeptName = line.getSubjectName();
        String watcherDeptName = watcher.getDepartment().getName();

        String lineCourseName = line.getCatalogNumber();
        String watcherCourseName = watcher.getCourse().getCatalogNumber();

        if(lineDeptName.equals(watcherDeptName)
                && lineCourseName.equals(watcherCourseName)) {

            watcher.addEvent(event);
        }
    }

    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers() {
        return database.getWatchers();
    }

    @GetMapping("/api/watchers/{wId}")
    public List<String> getWatcher(@PathVariable("wId") int wId) throws WatcherNotFoundException {
        return database.getChanges(wId);
    }

    @DeleteMapping("/api/watchers/{wId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeWatcher(@PathVariable("wId") int wId) {
        database.deleteWatcher(wId);
    }
}
