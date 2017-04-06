package in.ac.mnnit.sos.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.extras.Utils;
import in.ac.mnnit.sos.models.Address;
import in.ac.mnnit.sos.models.Contact;
import in.ac.mnnit.sos.services.InternetHelper;
import in.ac.mnnit.sos.services.LocationService;
import in.ac.mnnit.sos.services.MyLocation;

public class LocationFragment extends Fragment implements OnMapReadyCallback {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private GoogleMap map;
    private MapFragment mapFragment;
    private OnFragmentInteractionListener mListener;
    private List<Contact> contacts;
    private TextView gettingLocationText;
    private Location currentLocation;
    private int circleColor;
    private View view;


    public LocationFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LocationFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LocationFragment newInstance(String param1, String param2) {
//        LocationFragment fragment = new LocationFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils utils = new Utils();
        if(utils.isNetworkAvailable(getActivity())){
            InternetHelper internetHelper = new InternetHelper(getActivity());
            internetHelper.execute();
            LocalDatabaseAdapter localDatabaseAdapter = new LocalDatabaseAdapter(getActivity());
            contacts = localDatabaseAdapter.getAllEmergencyContacts();
        }
        else{
            ((MainActivity) getActivity()).showNetworkNotConnectedDialog();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        gettingLocationText = (TextView) getActivity().findViewById(R.id.locationRequestText);
        circleColor = ContextCompat.getColor(getActivity(), R.color.circleColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            if(mapFragment.getView() != null)
                mapFragment.getView().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.fragment_location, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setupMap() throws IOException {
        LocationService locationService = new LocationService(getActivity());
        LatLng latLng = null;
        Utils utils = new Utils();
        BitmapDescriptor star = utils.getMarkerIconFromDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_stars));


        if(contacts != null) {
            for (Contact contact : contacts) {
                List<Address> addresses = contact.getAddresses();
                for (Address address : addresses) {
                    latLng = locationService.getLatLngFromAddress(address.getAddress());
                    if(latLng != null)
                        map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(contact.getName())
                            .icon(star));
                }
            }
        }

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){
                updateCurrentLocationOnMap(location);
                gettingLocationText.setVisibility(View.INVISIBLE);
                if(mapFragment.getView() != null)
                    mapFragment.getView().setVisibility(View.VISIBLE);
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity(), locationResult);
    }

    Marker currentLocationMarker;
    Circle radiusCircle;

    private void updateCurrentLocationOnMap(Location location){
        currentLocation = location;

        if(currentLocationMarker != null && radiusCircle != null) {
            currentLocationMarker.remove();
            radiusCircle.remove();
        }

        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        currentLocationMarker = map.addMarker(new MarkerOptions()
                .position(myLocation)
                .title("Your location"));
        radiusCircle = map.addCircle(new CircleOptions()
                .center(myLocation)
                .radius(10000)
                .strokeColor(Color.argb(0, 0, 0, 1))
                .fillColor(circleColor));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, (float) 11.5));
    }

    public void goToCurrentLocation(){
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, (float)14));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        try {
            setupMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
