package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 1/3/17.
 */

public class Phone {
    private String number;
    private String type;

    public Phone(){

    }

    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
