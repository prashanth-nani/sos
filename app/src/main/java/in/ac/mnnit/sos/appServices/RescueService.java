package in.ac.mnnit.sos.appServices;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.services.AlarmHelper;
import in.ac.mnnit.sos.services.LocationDetailsHolder;
import in.ac.mnnit.sos.services.MessageService;
import in.ac.mnnit.sos.services.MyLocation;

public class RescueService extends Service {
    AlarmHelper alarmHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Thread smsthread;


    public static boolean running = false;
    public RescueService() {

    }

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("alarmService", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "here");
        Thread thread = new Thread(new AlarmThread(startId));
        thread.start();
        smsthread = new Thread(new SMSThread(startId));
        smsthread.start();

        running = true;
        editor.putBoolean("alarmOn", true);
        editor.apply();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Alarm Service stopped", Toast.LENGTH_SHORT).show();
        alarmHelper.stopAlarm();
//        smsthread.interrupt();
//        smsthread.stop();
        running = false;
        editor.putBoolean("alarmOn", false);
        editor.apply();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final class AlarmThread implements Runnable{

        int serviceId;
        AlarmThread(int serviceId) {
            this.serviceId = serviceId;
        }


        @Override
        public void run() {
            alarmHelper = new AlarmHelper(RescueService.this);
            alarmHelper.startAlarm();
        }
    }

    private final class SMSThread implements Runnable{
        int serviceId;

        SMSThread(int serviceId){
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            Looper.prepare();
            sendSMS();
        }

        public void sendSMS(){
            final MessageService messageService = new MessageService();
            LocalDatabaseAdapter localDatabaseAdapter = new LocalDatabaseAdapter(RescueService.this);
            final ArrayList<String> phones = localDatabaseAdapter.getAllPhones();



            MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                String locationBaseLink = "http://maps.google.com/maps?q=";
                String messageContent =  "Please help!! I'm in danger.";
                @Override
                public void gotLocation(Location location){
                    LatLng bestKnownLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    if(location != null)
                    {
                        String locationMapLink = locationBaseLink.concat(String.valueOf(bestKnownLoc.latitude)+","+String.valueOf(bestKnownLoc.longitude));
                        messageContent = "Please help!! I'm in danger. I'm at "+locationMapLink;
                    }
                    messageService.sendSMS(phones, messageContent);
                    Log.d("TAG", "SMS Sent");
                }
            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(getBaseContext(), locationResult);

//            LocationDetailsHolder locationDetailsHolder = new LocationDetailsHolder();
//            LatLng bestKnownLoc = locationDetailsHolder.getLastBestLocation();

        }
    }
}
