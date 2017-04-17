package in.ac.mnnit.sos.database.entity;

/**
 * Created by Banda Prashanth Yadav on 1/3/17.
 */

public class EcontactEmail {
    private int id;
    private int contactID;
    private String emailID;
    private String type;

    public EcontactEmail(String emailID, String type) {
        this.emailID = emailID;
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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
