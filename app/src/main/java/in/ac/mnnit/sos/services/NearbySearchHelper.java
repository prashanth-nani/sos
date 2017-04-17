package in.ac.mnnit.sos.services;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.ac.mnnit.sos.models.Place;
import in.ac.mnnit.sos.services.HttpRequestsHelper.OnServerResponseListener;

/**
 * Created by Banda Prashanth Yadav on 3/4/17.
 */

public class NearbySearchHelper {

    private HttpRequestsHelper httpRequestsHelper;


    public static final int POLICE_REQUEST = 4;
    public static final int HOSPITAL_REQUEST = 5;
    public static final int FIRE_REQUEST = 6;
    public static final int ATM_REQUEST = 7;
    public static final int PLACE_DETAILS_REQUEST = 8;

    public NearbySearchHelper(Context context, OnServerResponseListener onServerResponseListener){
        httpRequestsHelper = new HttpRequestsHelper(context, onServerResponseListener);
    }

    public void search(LatLng currentLocation, int type){
        httpRequestsHelper.populateNearbyPlaces(currentLocation, type);
    }

    public void fillPlaceDetails(Place place){
        httpRequestsHelper.populatePlaceDetailsByReference(place.getReference());
    }
}
