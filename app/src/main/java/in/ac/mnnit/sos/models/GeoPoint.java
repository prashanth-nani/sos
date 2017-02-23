package in.ac.mnnit.sos.models;

/**
 * Created by prashanth on 23/2/17.
 */
public class GeoPoint {
    private double latitude;
    private double longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public double getLatitude() {
        return latitude;
    }
}
