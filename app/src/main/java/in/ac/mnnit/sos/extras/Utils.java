package in.ac.mnnit.sos.extras;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import in.ac.mnnit.sos.models.GeoPoint;

/**
 * Created by Banda Prashanth Yadav on 23/2/17.
 */

public class Utils {
    public double getDistanceBetweenPoints(GeoPoint pointOne, GeoPoint pointTwo){
        double distanceInKm = 0;

//        Implement logic to calculate distance between two geo co-ordinates
//          =================================================================
// References:
//        http://www.movable-type.co.uk/scripts/latlong.html
//        http://stackoverflow.com/questions/365826/calculate-distance-between-2-gps-coordinates
//        ===============================================================
// Haversine formula:
//
//        a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
//        c = 2 ⋅ atan2( √a, √(1−a) )
//        d = R ⋅ c
//        where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
//        note that angles need to be in radians to pass to trig functions!
//        ========================================================================
// JavaScript:
//        var R = 6371e3; // metres
//        var φ1 = lat1.toRadians();
//        var φ2 = lat2.toRadians();
//        var Δφ = (lat2-lat1).toRadians();
//        var Δλ = (lon2-lon1).toRadians();
//
//        var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
//                Math.cos(φ1) * Math.cos(φ2) *
//                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
//        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//
//        var d = R * c;

        return distanceInKm;
    }

    public Bitmap getPhotoBitmap(ContentResolver contentResolver, String photoUri) throws IOException {
        Bitmap photo = null;
        if(photoUri != null) {
            photo = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photoUri));
        }
        return photo;
    }

    public Bitmap getPhotoBitmap(ContentResolver contentResolver, Uri photoUri) throws IOException {
        Bitmap photo = null;
        if(photoUri != null) {
            photo = MediaStore.Images.Media.getBitmap(contentResolver, photoUri);
        }
        return photo;
    }

    public byte[] getBytesFromBitmap(Bitmap photo){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 1, stream);
        return stream.toByteArray();
    }

    public BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public Bitmap getBitmapFromBytes(byte[] photoBytes){
        return BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
    }

    public boolean isValidEmail(String email){
        //Implement this method. Return false if invalid
        return true;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isInternetAvailable() {
        String command = "ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec (command).waitFor() == 0);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public String getRecordingsDirectory(){
        File SDCardPath = Environment.getExternalStorageDirectory();
        File recordingsPath = new File(SDCardPath.getAbsolutePath()+"/SOSRecordings/");
        if(!recordingsPath.exists()){
            recordingsPath.mkdir();
        }
        return recordingsPath.getAbsolutePath();
    }
}
