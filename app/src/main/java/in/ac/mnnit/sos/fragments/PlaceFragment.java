package in.ac.mnnit.sos.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.models.Place;
import in.ac.mnnit.sos.services.HttpRequestsHelper;
import in.ac.mnnit.sos.services.NearbySearchHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlaceFragment extends Fragment implements HttpRequestsHelper.OnServerResponseListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceFragment() {
    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlaceFragment newInstance(int columnCount) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        Bundle args = getArguments();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            LatLng currentLocation = new LatLng(args.getDouble("lat"), args.getDouble("lng"));
            NearbySearchHelper nearbySearchHelper = new NearbySearchHelper(getActivity(), this);
            nearbySearchHelper.search(currentLocation, args.getInt("typeId"));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onServerResponse(int requestID, Object response) {
        if(requestID == NearbySearchHelper.POLICE_REQUEST || requestID == NearbySearchHelper.HOSPITAL_REQUEST || requestID == NearbySearchHelper.FIRE_REQUEST || requestID == NearbySearchHelper.ATM_REQUEST){
            try {
                List<Place> places = new ArrayList<>();
                JSONObject resultObj = new JSONObject((String) response);
                String nextPageToken = (String) resultObj.get("next_page_token");
                String statusCode = resultObj.getString("status");
                if(statusCode.equals("OK")) {
                    JSONArray resultsArray = resultObj.getJSONArray("results");

                    int len = resultsArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject temp = resultsArray.getJSONObject(i);
                        double lat = (double) temp.getJSONObject("geometry").getJSONObject("location").get("lat");
                        double lng = (double) temp.getJSONObject("geometry").getJSONObject("location").get("lng");
                        String name = (String) temp.get("name");
                        String vicinity = (String) temp.get("vicinity");
                        String iconLink = temp.getString("icon");
                        String rating = null;
                        String phone = null;
                        try {
                            rating = temp.get("rating").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            phone = temp.getString("international_phone_number");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.d("TAG", name + "\n" + lat + "," + lng + "\n" + vicinity + "\n" + rating + "\n\n");
                        places.add(new Place(lat, lng, name, iconLink, vicinity, rating, phone));
                    }
                    recyclerView.setAdapter(new MyPlaceRecyclerViewAdapter(places, mListener));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServerResponseError(int requestID, Object error) {
        if(requestID == NearbySearchHelper.POLICE_REQUEST || requestID == NearbySearchHelper.HOSPITAL_REQUEST || requestID == NearbySearchHelper.FIRE_REQUEST || requestID == NearbySearchHelper.ATM_REQUEST){
            Log.e("TAG", "Error occured while fetching places");
            Log.e("TAG", error.toString());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Place place);
    }
}
