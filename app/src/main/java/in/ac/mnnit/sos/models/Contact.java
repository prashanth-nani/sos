package in.ac.mnnit.sos.models;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prashanth on 25/2/17.
 */

public class Contact {
    private String name = null;
    private Map<String, String> labelledPhone = new HashMap<>();
    private Map<String, String> labelledEmail = new HashMap<>();
    private Map<String, String> labelledAddress = new HashMap<>();
    private String[] phone;
    private String[] email;
    private String[] address;
    private Bitmap thumbnailPhoto = null;
    private Bitmap highResPhoto = null;


    public Contact(String name, String[] phone, String[] email, String[] address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(String name, Map<String, String> labelledPhone, Map<String, String> labelledEmail, Map<String, String> labelledAddress) {
        this.name = name;
        this.labelledPhone = labelledPhone;
        this.labelledEmail = labelledEmail;
        this.labelledAddress = labelledAddress;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getLabelledPhone() {
        return labelledPhone;
    }

    public Map<String, String> getLabelledEmail() {
        return labelledEmail;
    }

    public Map<String, String> getLabelledAddress() {
        return labelledAddress;
    }

    public String[] getPhone() {
        return phone;
    }

    public String[] getEmail() {
        return email;
    }

    public String[] getAddress() {
        return address;
    }

    public Bitmap getHighResPhoto() {
        return highResPhoto;
    }

    public Bitmap getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(Bitmap thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public void setHighResPhoto(Bitmap highResPhoto) {
        this.highResPhoto = highResPhoto;
    }
}
