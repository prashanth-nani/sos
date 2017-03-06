package in.ac.mnnit.sos.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by prashanth on 6/3/17.
 */

public class LocationService {

    private Context context;
    private Geocoder geocoder;

    public LocationService(Context context) {
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    public LatLng getLatLngFromAddress(String locationName) throws IOException {
        List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
        Address address = addresses.get(0);
        return new LatLng(address.getLatitude(), address.getLongitude());
    }
}
