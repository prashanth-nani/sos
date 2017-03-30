package in.ac.mnnit.sos.services;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.ac.mnnit.sos.extras.Utils;

/**
 * Created by prashanth on 30/3/17.
 */

public class VoiceRecordHelper {

    MediaRecorder mediaRecorder;
    Context context;

    public VoiceRecordHelper(Context context) {
        this.context = context;
        Utils utils = new Utils();
        mediaRecorder = new MediaRecorder();
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.3gp'").format(new Date());
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(utils.getRecordingsDirectory(context)+"/"+fileName);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordAudio(){
        mediaRecorder.start();
    }

    public void stopRecording(){
        mediaRecorder.stop();
    }
}
