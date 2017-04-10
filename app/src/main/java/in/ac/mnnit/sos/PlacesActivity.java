package in.ac.mnnit.sos;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import in.ac.mnnit.sos.fragments.PlaceFragment;
import in.ac.mnnit.sos.models.Place;
import in.ac.mnnit.sos.services.LocationDetailsHolder;

public class PlacesActivity extends AppCompatActivity implements PlaceFragment.OnListFragmentInteractionListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Bundle extras = getIntent().getExtras();

        Bundle args = new Bundle();
        args.putDouble("lat", LocationDetailsHolder.LATEST_LOCATION.latitude);
        args.putDouble("lng", LocationDetailsHolder.LATEST_LOCATION.longitude);
        args.putInt("typeId", (int) extras.get("typeId"));

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        PlaceFragment placeListFragment = new PlaceFragment();
        placeListFragment.setArguments(args);
        fragmentTransaction.add(R.id.place_list_frame, placeListFragment, "placeListFragment");
        fragmentTransaction.commit();
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

    }
}
