package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 1/3/17.
 */

public class Address {
    private String address = null;
    private String type = null;

    public Address(){

    }

    public Address(String address, String type) {
        this.address = address;
        this.type = type;
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
