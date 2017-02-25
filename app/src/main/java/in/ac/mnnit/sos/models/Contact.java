package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 25/2/17.
 */

public class Contact {
    private String fullName;
    private String firstName;
    private String lastName;
    private String[] phone;
    private String[] email;
    private String[] address;

    public Contact(String fullName, String[] phone, String[] email, String[] address) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getPhone() {
        return phone;
    }

    public void setPhone(String[] phone) {
        this.phone = phone;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }
}
