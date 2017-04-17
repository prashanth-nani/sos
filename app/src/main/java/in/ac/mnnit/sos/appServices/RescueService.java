package in.ac.mnnit.sos.appServices;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.services.AlarmHelper;
import in.ac.mnnit.sos.services.LocationDetailsHolder;
import in.ac.mnnit.sos.services.MessageService;

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
//        smsthread.start();

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
            sendSMS();
        }

        public void sendSMS(){
            MessageService messageService = new MessageService();
            LocalDatabaseAdapter localDatabaseAdapter = new LocalDatabaseAdapter(RescueService.this);
            ArrayList<String> phones = localDatabaseAdapter.getAllPhones();
            String locationBaseLink = "http://maps.google.com/maps?q=";
            String locationMapLink;
            String messageContent =  "Please help!! I'm in danger.";
            LocationDetailsHolder locationDetailsHolder = new LocationDetailsHolder();
            LatLng bestKnownLoc = locationDetailsHolder.getLastBestLocation();
            if(bestKnownLoc != null)
            {
                locationMapLink = locationBaseLink.concat(String.valueOf(bestKnownLoc.latitude)+","+String.valueOf(bestKnownLoc.longitude));
                messageContent = "Please help!! I'm in danger. I'm at "+locationMapLink;
            }
            messageService.sendSMS(phones, messageContent);
            Log.d("TAG", "SMS Sent");
        }
    }
}
