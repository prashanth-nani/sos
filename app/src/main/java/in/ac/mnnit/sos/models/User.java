package in.ac.mnnit.sos.models;

/**
 * Created by Banda Prashanth Yadav on 21/2/17.
 */

public class User {
    private String name;
    private String phone;
    private String gender;
    private String email;
    private String password;

    public User(String name, String phone, String gender, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
