package in.ac.mnnit.sos.appServices;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import in.ac.mnnit.sos.services.AlarmHelper;

public class AlarmService extends Service {
    AlarmHelper alarmHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AlarmService() {

    }

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("alarmService", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new AlarmThread(startId));
        thread.start();
        editor.putBoolean("alarmOn", true);
        editor.apply();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Alarm Service stopped", Toast.LENGTH_SHORT).show();
        alarmHelper.stopAlarm();
        editor.putBoolean("alarmOn", false);
        editor.apply();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    final class AlarmThread implements Runnable{

        int serviceId;
        AlarmThread(int serviceId) {
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            alarmHelper = new AlarmHelper(AlarmService.this);
            alarmHelper.startAlarm();
        }
    }
}
