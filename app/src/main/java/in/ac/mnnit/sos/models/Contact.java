package in.ac.mnnit.sos.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashanth on 25/2/17.
 */

public class Contact {
    private String name = null;
    private List<Phone> phones = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private byte[] thumbnailPhoto = null;
    private byte[] highResPhoto = null;

    public Contact(){

    }

    public Contact(String name, List<Phone> phones, List<Email> emails, List<Address> addresses) {
        this.name = name;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public byte[] getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(byte[] thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public byte[] getHighResPhoto() {
        return highResPhoto;
    }

    public void setHighResPhoto(byte[] highResPhoto) {
        this.highResPhoto = highResPhoto;
    }
}
