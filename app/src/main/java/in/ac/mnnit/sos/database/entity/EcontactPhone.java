package in.ac.mnnit.sos.database.entity;

import java.util.Map;

/**
 * Created by prashanth on 1/3/17.
 */

public class EcontactPhone {
    private int id;
    private int contactID;
    private String phoneNumber;
    private int type;

    public EcontactPhone(String phoneNumber, int type) {
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
