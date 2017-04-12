package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 4/4/17.
 */

public class Place {
    private double latitude;
    private double longitude;
    private String name;
    private String iconLink;
    private String vicinity;
    private String rating;
    private String phone;
    private String address;
    private String id;
    private String reference;

    public Place(String id, String reference, double latitude, double longitude, String name, String iconLink, String vicinity, String rating, String phone) {
        this.id = id;
        this.reference = reference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.iconLink = iconLink;
        this.vicinity = vicinity;
        this.rating = rating;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getIconLink() {
        return iconLink;
    }

    public String getPhone() {
        return phone;
    }

    public String getVicinity() {

        return vicinity;
    }

    public String getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
