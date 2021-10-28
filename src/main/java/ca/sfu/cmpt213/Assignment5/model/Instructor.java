package ca.sfu.cmpt213.Assignment5.model;

/**
 * Class storing data about instructors
 */
public class Instructor implements Comparable<Instructor>  {
    private String name;

    public Instructor(String name) {
        if(name == null)
            this.name = "(null)";
        else
            this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Instructor other) {
        return name.compareTo(other.getName());
    }
}
