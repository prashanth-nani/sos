package in.ac.mnnit.sos.services;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.R;

/**
 * Created by Banda Prashanth Yadav on 21/2/17.
 */

public class Config {
    private static final String BASE_URL = "http://sosmnnit.16mb.com/";
//private static final String BASE_URL = "http://172.31.74.249/sos-php-server/";
    public static final String REGISTER_URL = BASE_URL.concat("register.php");
    public static final String PROCESS_EMAIL_URL = BASE_URL.concat("process_email.php");
    public static final String LOGIN_URL = BASE_URL.concat("login.php");


    public static String getNearbyPlacesUrl(LatLng currentLatLng, String type)
    {
        String MAPS_API_KEY = MainActivity.APP_CONTEXT.getResources().getString(R.string.google_maps_api_key);
        String latLngString = String.valueOf(currentLatLng.latitude)+","+String.valueOf(currentLatLng.longitude);
        Log.d("TAG", "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latLngString+"&rankby=distance&type="+type+"&key="+MAPS_API_KEY);
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latLngString+"&rankby=distance&type="+type+"&key="+MAPS_API_KEY;
    }

    public static String getPlaceDetailsUrl(String reference){
        String MAPS_API_KEY = MainActivity.APP_CONTEXT.getResources().getString(R.string.google_maps_api_key);
        return "https://maps.googleapis.com/maps/api/place/details/json?key="+MAPS_API_KEY+"&reference="+reference;
    }
}