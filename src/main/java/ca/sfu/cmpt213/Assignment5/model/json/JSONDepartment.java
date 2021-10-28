package ca.sfu.cmpt213.Assignment5.model.json;

/**
 * Support class to interpret Course info as JSON
 */
public class JSONDepartment {
    private int deptId;
    private String name;

    public JSONDepartment() {
    }

    public int getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
