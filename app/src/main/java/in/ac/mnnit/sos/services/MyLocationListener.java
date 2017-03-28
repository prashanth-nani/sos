package in.ac.mnnit.sos.services;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by prashanth on 23/3/17.
 */

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        Log.e("TAG", "Location is: ("+location.getLatitude()+", "+location.getLongitude()+")");
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        LocationDetailsHolder locationDetailsHolder = new LocationDetailsHolder();
        locationDetailsHolder.addPoint(ll);
        LocationDetailsHolder.LATEST_LOCATION = ll;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
