package in.ac.mnnit.sos.services;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by prashanth on 24/3/17.
 */

public class LocationDetailsHolder {
    public static List<LatLng> points;
    public static LatLng LATEST_LOCATION = null;
    public static LatLng LAST_KNOWN_LOCATION = null;
}
