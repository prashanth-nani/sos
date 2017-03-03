package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 1/3/17.
 */

public class Email {
    private String emailID = null;
    private String type = null;

    public Email(){

    }

    public Email(String emailID, String type) {
        this.emailID = emailID;
        this.type = type;
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
