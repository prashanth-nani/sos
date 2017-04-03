package in.ac.mnnit.sos.services;

import com.google.android.gms.maps.model.LatLng;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.R;

/**
 * Created by prashanth on 21/2/17.
 */

public class Config {
    private static final String BASE_URL = "http://sosmnnit.16mb.com/";
    public static final String REGISTER_URL = BASE_URL.concat("register.php");
    public static final String PROCESS_EMAIL_URL = BASE_URL.concat("process_email.php");
    public static final String LOGIN_URL = BASE_URL.concat("login.php");


    public static String getNearbyPlacesUrl(LatLng currentLatLng, String type)
    {
        String MAPS_API_KEY = MainActivity.APP_CONTEXT.getResources().getString(R.string.google_maps_api_key);
        String latLngString = String.valueOf(currentLatLng.latitude)+","+String.valueOf(currentLatLng.longitude);
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latLngString+"&rankby=distance&type="+type+"&key="+MAPS_API_KEY;
    }
}