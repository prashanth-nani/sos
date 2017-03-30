package in.ac.mnnit.sos.services;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by prashanth on 28/3/17.
 */

public class AlarmHelper {

    private Context context;
    private MediaPlayer mediaPlayer;

    public AlarmHelper(Context context) {
        this.context = context;
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        int resId = context.getResources().getIdentifier("siren", "raw", context.getPackageName());
        String resource = "android.resource://"+context.getPackageName()+"/"+resId;
        mediaPlayer = MediaPlayer.create(context, Uri.parse(resource));
    }

    public void startAlarm(){
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopAlarm(){
        mediaPlayer.stop();
    }
}
