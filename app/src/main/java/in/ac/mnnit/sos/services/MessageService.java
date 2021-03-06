package in.ac.mnnit.sos.services;

import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Banda Prashanth Yadav on 24/3/17.
 */

public class MessageService {

    public void sendSMS(ArrayList<String> receipentList, String message){
        SmsManager smsManager = SmsManager.getDefault();
        for (String receipent: receipentList) {
            Log.e("TAG", receipent);
            smsManager.sendTextMessage(receipent, null, message, null, null);
        }
    }
}
