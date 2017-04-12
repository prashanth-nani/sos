package in.ac.mnnit.sos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import in.ac.mnnit.sos.fragments.PlaceDetailsBottomSheetFragment;
import in.ac.mnnit.sos.fragments.PlaceFragment;
import in.ac.mnnit.sos.models.Place;
import in.ac.mnnit.sos.services.HttpRequestsHelper;
import in.ac.mnnit.sos.services.MyLocation;
import in.ac.mnnit.sos.services.NearbySearchHelper;

public class PlacesActivity extends AppCompatActivity implements PlaceFragment.OnListFragmentInteractionListener, HttpRequestsHelper.OnServerResponseListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        final Bundle extras = getIntent().getExtras();

        final Bundle args = new Bundle();

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location) {
                args.putDouble("lat", location.getLatitude());
                args.putDouble("lng", location.getLongitude());
                args.putInt("typeId", (int) extras.get("typeId"));

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                PlaceFragment placeListFragment = new PlaceFragment();
                placeListFragment.setArguments(args);
                fragmentTransaction.add(R.id.place_list_frame, placeListFragment, "placeListFragment");
                fragmentTransaction.commit();
                findViewById(R.id.gettingPlacesText).setVisibility(View.INVISIBLE);
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getBaseContext(), locationResult);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Place place) {
        PlacesActivity.place = place;
        NearbySearchHelper nearbySearchHelper = new NearbySearchHelper(getBaseContext(), this);
        nearbySearchHelper.fillPlaceDetails(place);
    }

    public void showPlaceDetailsBottomSheet(Place place){
        BottomSheetDialogFragment bottomSheetDialogFragment = new PlaceDetailsBottomSheetFragment();
//        BottomSheetDialogFragment placeDetailsDialogFragment = PlaceDetailsBottomSheetFragment.newInstance(place);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @Override
    public void onServerResponse(int requestID, Object response) {
        if(requestID == NearbySearchHelper.PLACE_DETAILS_REQUEST){
            try {
                String address = null;
                String phone = null;

                JSONObject resultObj = new JSONObject((String) response);
                JSONObject result = resultObj.getJSONObject("result");
                try{
                    address = result.getString("formatted_address");
                }
                catch (Exception e){

                }
                try{
                    phone = result.getString("international_phone_number");
                }
                catch (Exception e){

                }
                place.setAddress(address);
                place.setPhone(phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showPlaceDetailsBottomSheet(place);
        }
    }

    @Override
    public void onServerResponseError(int requestID, Object error) {

    }
}
