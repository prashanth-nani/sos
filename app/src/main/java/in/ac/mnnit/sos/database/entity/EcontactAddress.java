package in.ac.mnnit.sos.database.entity;

/**
 * Created by prashanth on 1/3/17.
 */

public class EcontactAddress {
    private int id;
    private int contactID;
    private String address;
    private int type;

    public EcontactAddress(String address, int type) {
        this.address = address;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
