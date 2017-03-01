package in.ac.mnnit.sos.database.entity;

/**
 * Created by prashanth on 1/3/17.
 */

public class EcontactEmail {
    private int id;
    private int contactID;
    private String emailID;
    private int type;

    public EcontactEmail(String emailID, int type) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
