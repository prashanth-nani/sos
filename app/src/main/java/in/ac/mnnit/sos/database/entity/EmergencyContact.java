package in.ac.mnnit.sos.database.entity;

/**
 * Created by prashanth on 1/3/17.
 */

public class EmergencyContact {
    private int id;
    private String name;

    public EmergencyContact(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
