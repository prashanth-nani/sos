package in.ac.mnnit.sos.database.entity;

import java.util.Map;

/**
 * Created by Banda Prashanth Yadav on 1/3/17.
 */

public class EcontactPhone {
    private int id;
    private int contactID;
    private String phoneNumber;
    private String type;

    public EcontactPhone(String phoneNumber, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
