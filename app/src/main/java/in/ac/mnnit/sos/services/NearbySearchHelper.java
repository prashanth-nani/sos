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
 * Created by prashanth on 3/4/17.
 */

public class NearbySearchHelper {

    private HttpRequestsHelper httpRequestsHelper;
//    private OnServerResponseListener onServerResponseListener;
//    private Context context;
//    public static List<Place> places = new ArrayList<>();

    public static final int POLICE_REQUEST = 4;
    public static final int HOSPITAL_REQUEST = 5;
    public static final int FIRE_REQUEST = 6;
    public static final int ATM_REQUEST = 7;

    public NearbySearchHelper(Context context, OnServerResponseListener onServerResponseListener){
//        this.context = context;
//        this.onServerResponseListener = onServerResponseListener;
        httpRequestsHelper = new HttpRequestsHelper(context, onServerResponseListener);
    }

    public void search(LatLng currentLocation, int type){
        httpRequestsHelper.populateNearbyPlaces(currentLocation, type);
    }

//    String TAG= "TAG";
//    @Override
//    public void onServerResponse(int requestID, Object response) {
//        if(requestID == POLICE_REQUEST || requestID == HOSPITAL_REQUEST || requestID == FIRE_REQUEST || requestID == ATM_REQUEST){
//            try {
//                places.clear();
//                JSONObject resultObj = new JSONObject((String) response);
//                String nextPageToken = (String) resultObj.get("next_page_token");
//                String statusCode = resultObj.getString("status");
//                if(statusCode.equals("OK")) {
//                    JSONArray resultsArray = resultObj.getJSONArray("results");
//
//                    int len = resultsArray.length();
//                    for (int i = 0; i < len; i++) {
//                        JSONObject temp = resultsArray.getJSONObject(i);
//                        double lat = (double) temp.getJSONObject("geometry").getJSONObject("location").get("lat");
//                        double lng = (double) temp.getJSONObject("geometry").getJSONObject("location").get("lng");
//                        String name = (String) temp.get("name");
//                        String vicinity = (String) temp.get("vicinity");
//                        String iconLink = temp.getString("icon");
//                        String rating = null;
//                        String phone = null;
//                        try {
//                            rating = temp.get("rating").toString();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            phone = temp.getString("international_phone_number");
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        Log.d(TAG, name + "\n" + lat + "," + lng + "\n" + vicinity + "\n" + rating + "\n\n");
//                    places.add(new Place(lat, lng, name, iconLink, vicinity, rating, phone));
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void onServerResponseError(int requestID, Object error) {
//        Log.d("TAG", error.toString());
//    }
}
