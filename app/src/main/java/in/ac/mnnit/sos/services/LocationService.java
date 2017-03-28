package in.ac.mnnit.sos.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.ac.mnnit.sos.MainActivity;

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

    public String getAddressFromLatLng(LatLng latLng) throws IOException {
        StringBuilder namedAddress = new StringBuilder("");
        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        Address address = addresses.get(0);
        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            namedAddress.append(address.getAddressLine(i)).append(", ");
        }
        return namedAddress.toString();
    }

    public LatLng getLatLngFromAddress(String locationName) throws IOException {
        List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
        Address address;
        if(addresses.size() > 0){
            address = addresses.get(0);
            return new LatLng(address.getLatitude(), address.getLongitude());
        }
        return null;
    }

    public void trackUserLocation() {
        LocationListener locationListener = new MyLocationListener();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ((MainActivity) context).showGPSOffSnackbar();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocation == null){
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(lastKnownLocation != null){
            LocationDetailsHolder locationDetailsHolder = new LocationDetailsHolder();
            locationDetailsHolder.updateLastKnownLocation(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
        }
    }
}
