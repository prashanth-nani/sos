package in.ac.mnnit.sos.services;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import in.ac.mnnit.sos.services.HttpRequestsHelper.OnServerResponseListener;

/**
 * Created by prashanth on 3/4/17.
 */

public class NearbySearchHelper implements OnServerResponseListener{

    HttpRequestsHelper httpRequestsHelper;
    Context context;

    public NearbySearchHelper(Context context){
        httpRequestsHelper = new HttpRequestsHelper(context, this);
    }

    public void search(LatLng currentLocation, int type){
        httpRequestsHelper.populateNearbyPlaces(currentLocation, type);

    }

    @Override
    public void onServerResponse(int requestID, Object response) {

    }

    @Override
    public void onServerResponseError(int requestID, Object error) {

    }
}
