package in.ac.mnnit.sos.services;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;

/**
 * Created by prashanth on 28/3/17.
 */

public class AlarmService implements View.OnClickListener{

    private Context context;
    private MediaPlayer mediaPlayer;

    public AlarmService(Context context) {
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

    @Override
    public void onClick(View v) {

    }
}
