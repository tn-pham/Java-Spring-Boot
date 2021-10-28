package ca.sfu.cmpt213.Assignment5.model.json;

/**
 * Support class to interpret Watcher info as JSON
 */
public class JSONWatcher {
    int deptId;
    int courseId;

    public JSONWatcher() {}

    public int getDeptId() {
        return deptId;
    }

    public int getCourseId() {
        return courseId;
    }
}
