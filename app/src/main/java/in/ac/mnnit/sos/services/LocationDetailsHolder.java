package in.ac.mnnit.sos.services;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.ac.mnnit.sos.MainActivity;

/**
 * Created by prashanth on 24/3/17.
 */

public class LocationDetailsHolder {
    private LocationService locationService;
    public static List<LatLng> points;
    public static LatLng LATEST_LOCATION = null;
    public static LatLng LAST_KNOWN_LOCATION = null;
    public static String LAST_KNOWN_LOCATION_NAME = null;
    public static String LATEST_LOCATION_NAME = null;

    public LocationDetailsHolder() {
        this.locationService = new LocationService(MainActivity.MAIN_ACTIVITY_CONTEXT);
    }

    public void addPoint(LatLng point){
        if(points == null){
            points = new ArrayList<>();
        }
        points.add(point);
//        updateLatestLocationName();
    }

    public void updateLastKnownLocation(LatLng lastKnownLocation){
        LAST_KNOWN_LOCATION = lastKnownLocation;
//        updateLastLocationName();
    }

    public void updateLastLocationName(){
        LAST_KNOWN_LOCATION_NAME = null;
        try {
            if(LAST_KNOWN_LOCATION != null)
                LAST_KNOWN_LOCATION_NAME = locationService.getAddressFromLatLng(LAST_KNOWN_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLatestLocationName(){
        LATEST_LOCATION_NAME = null;
        try {
            if(LATEST_LOCATION != null)
                LATEST_LOCATION_NAME = locationService.getAddressFromLatLng(LATEST_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LatLng getLastBestLocation(){
        if(LATEST_LOCATION != null)
            return LATEST_LOCATION;
        else if(LAST_KNOWN_LOCATION != null)
            return LAST_KNOWN_LOCATION;
        else
            return null;
    }
}
