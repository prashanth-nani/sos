package in.ac.mnnit.sos.services;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import in.ac.mnnit.sos.services.HttpRequestsHelper.OnServerResponseListener;

/**
 * Created by prashanth on 3/4/17.
 */

public class NearbySearchHelper implements OnServerResponseListener{

    private HttpRequestsHelper httpRequestsHelper;
//    private Context context;

    public static final int POLICE_REQUEST = 4;
    public static final int HOSPITAL_REQUEST = 5;
    public static final int FIRE_REQUEST = 6;
    public static final int ATM_REQUEST = 7;

    public NearbySearchHelper(Context context){
        httpRequestsHelper = new HttpRequestsHelper(context, this);
    }

    public void search(LatLng currentLocation, int type){
        httpRequestsHelper.populateNearbyPlaces(currentLocation, type);
    }

    @Override
    public void onServerResponse(int requestID, Object response) {
        Log.d("TAG", response.toString());
    }

    @Override
    public void onServerResponseError(int requestID, Object error) {
        Log.d("TAG", error.toString());
    }
}
