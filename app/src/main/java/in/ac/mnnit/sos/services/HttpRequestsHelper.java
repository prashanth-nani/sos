package in.ac.mnnit.sos.services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import in.ac.mnnit.sos.models.Credential;
import in.ac.mnnit.sos.models.User;
import in.ac.mnnit.sos.services.Config;

/**
 * Created by Banda Prashanth Yadav on 2/3/17.
 */

public class HttpRequestsHelper {

    public static final int PROCESS_EMAIL_REQUEST = 1;
    public static final int REGISTER_REQUEST = 2;
    public static final int LOGIN_REQUEST = 3;


    private OnServerResponseListener onServerResponseListener;
    private RequestQueue requestQueue;

    private final String registerUrl = Config.REGISTER_URL;
    private final String processEmailUrl = Config.PROCESS_EMAIL_URL;
    private String loginUrl = Config.LOGIN_URL;

    public HttpRequestsHelper(Context context, OnServerResponseListener onServerResponseListener) {
        this.onServerResponseListener = onServerResponseListener;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void registerUser(User user){

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("phone", user.getPhone());
        params.put("gender", user.getGender());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());

        post(params, registerUrl, REGISTER_REQUEST);
    }

    public void processUserEmail(String email){
        Map<String, String> params = new HashMap<>();
        params.put("email", email);

        post(params, processEmailUrl, PROCESS_EMAIL_REQUEST);
    }

    public void login(Credential cred){
        Map<String, String> params = new HashMap<>();
        params.put("email", cred.getUsername());
        params.put("password", cred.getPassword());

        post(params, loginUrl, LOGIN_REQUEST);
    }

    public void populateNearbyPlaces(LatLng currentLatLng, int typeID){
        String url = "";
        switch (typeID){
            case NearbySearchHelper.POLICE_REQUEST:
                url = Config.getNearbyPlacesUrl(currentLatLng, "police");
                break;
            case NearbySearchHelper.HOSPITAL_REQUEST:
                url = Config.getNearbyPlacesUrl(currentLatLng, "hospital");
                break;
            case NearbySearchHelper.FIRE_REQUEST:
                url = Config.getNearbyPlacesUrl(currentLatLng, "fire_station");
                break;
            case NearbySearchHelper.ATM_REQUEST:
                url = Config.getNearbyPlacesUrl(currentLatLng, "atm");
                break;
        }
        get(url, typeID);
    }

    public void populatePlaceDetailsByReference(String reference){
        String url = Config.getPlaceDetailsUrl(reference);
        get(url, NearbySearchHelper.PLACE_DETAILS_REQUEST);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void post(Map<String, String> params, String url, final int requestID){
        PostRequest postRequest = new PostRequest(params, Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onServerResponseListener.onServerResponse(requestID, response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                onServerResponseListener.onServerResponseError(requestID, error);
            }
        });

        requestQueue.add(postRequest);
    }

    private void get(String url, final int requestID){
        GetRequest getRequest = new GetRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onServerResponseListener.onServerResponse(requestID, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onServerResponseListener.onServerResponseError(requestID, error);
            }
        });

        requestQueue.add(getRequest);
    }

    public interface OnServerResponseListener{
        void onServerResponse(int requestID, Object response);
        void onServerResponseError(int requestID, Object error);
    }

    private static class PostRequest extends StringRequest{
        Map<String, String> params;

        public PostRequest(Map<String, String> params, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
            this.params = params;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return params;
        }
    }

    private static class GetRequest extends StringRequest{

        public GetRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }

}
