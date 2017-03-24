package in.ac.mnnit.sos.services;

import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by prashanth on 24/3/17.
 */

public class MessageService {

    public void sendSMS(ArrayList<String> receipentList, String message){
        SmsManager smsManager = SmsManager.getDefault();
        for (String receipent: receipentList) {
            smsManager.sendTextMessage(receipent, null, message, null, null);
        }
    }
}
