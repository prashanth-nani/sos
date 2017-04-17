package in.ac.mnnit.sos.database.entity;

/**
 * Created by Banda Prashanth Yadav on 1/3/17.
 */

public class EcontactAddress {
    private int id;
    private int contactID;
    private String address;
    private String type;

    public EcontactAddress(String address, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
