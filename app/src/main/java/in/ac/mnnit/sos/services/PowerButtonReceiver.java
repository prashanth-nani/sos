package in.ac.mnnit.sos.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import in.ac.mnnit.sos.appServices.RescueService;

/**
 * Created by Banda Prashanth Yadav on 16/4/17.
 */

public class PowerButtonReceiver extends BroadcastReceiver {

    public static int powerButtonClickCounter = 0;
    private static boolean running = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, RescueService.class);
        if(!running) {
            if (powerButtonClickCounter >= 5) {
                powerButtonClickCounter = 0;
                running = true;
                context.startService(alarmIntent);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON) || intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                powerButtonClickCounter++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        powerButtonClickCounter = 0;
                    }
                }, 5000);
            }
        }
    }
}
