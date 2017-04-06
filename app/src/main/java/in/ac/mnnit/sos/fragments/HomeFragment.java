package in.ac.mnnit.sos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.appServices.AlarmService;
import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.services.AlarmHelper;
//import in.ac.mnnit.sos.services.FlashHelper;
import in.ac.mnnit.sos.services.FlashLightHelper;
import in.ac.mnnit.sos.services.LocationDetailsHolder;
import in.ac.mnnit.sos.services.MessageService;
import in.ac.mnnit.sos.services.MyLocation;
import in.ac.mnnit.sos.services.NearbySearchHelper;
import in.ac.mnnit.sos.services.VoiceRecordHelper;


public class HomeFragment extends Fragment implements View.OnClickListener {

    Button dangerButton;
    ImageButton recordButton;
    ImageButton flashButton;
    private AlarmHelper alarmHelper;
//    private FlashHelper flashService;
    private boolean serviceStarted = false;
    private boolean serviceInitiated =  false;
    private View view;
    private Activity activity;
    private Intent alarmIntent;
    private Context context;

    Timer alarmTimer;
    Timer taskTimer;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        alarmHelper = new AlarmHelper(getActivity());
        alarmIntent = new Intent(getActivity(), AlarmService.class);
//        flashService = new FlashHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            view.setOnClickListener(this);
            dangerButton = (Button) view.findViewById(R.id.dangerButton);
            dangerButton.setOnClickListener(this);
            recordButton = (ImageButton) view.findViewById(R.id.record);
            recordButton.setOnClickListener(this);
            flashButton = (ImageButton) view.findViewById(R.id.flash);
            flashButton.setOnClickListener(this);
        }
        return view;
    }

    public void initializeTimers(){
        alarmTimer = new Timer();
        taskTimer = new Timer();
    }

//    public void onClickDanger(){
//        dangerButton.setText("Click to abort within\r\n10s");
//        if(!serviceStarted && !serviceInitiated) {
//            initializeTimers();
//            long interval =  5 * 60 * 1000; //5 Min
//            serviceInitiated = true;
//            alarmTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    startAlarm();
//                    serviceStarted = true;
//                    changeText();
//                }
//            }, 10000);
//
//
//            taskTimer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    kickOffTasks();
//                }
//            }, 20 * 1000, interval);
//        }else{
//            dangerButton.setText("I am in\r\ndanger");
//            stopAlarm();
//            alarmTimer.cancel();
//            taskTimer.cancel();
//            serviceStarted = false;
//            serviceInitiated = false;
//        }
//    }

    public void onClickDanger(){
        if(!serviceStarted)
        {
            serviceStarted = true;
            dangerButton.setText("Stop");
            sendSMS();
            context.startService(alarmIntent);
        }
        else {
            context.stopService(alarmIntent);
            dangerButton.setText("I am in\r\ndanger");
            serviceStarted = false;
        }
    }

    public void changeText(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dangerButton.setText("STOP");
            }
        });
    }

    public void kickOffTasks(){
        sendSMS();
    }

    public void sendSMS(){
        MessageService messageService = new MessageService();
        LocalDatabaseAdapter localDatabaseAdapter = new LocalDatabaseAdapter(getActivity());
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
        Snackbar.make(getActivity().findViewById(android.R.id.content), "SMS Sent", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public void startAlarm(){
        alarmHelper.startAlarm();
    }

    public void stopAlarm(){
        alarmHelper.stopAlarm();
    }

    public void startFlash(){
//        flashService.startOn();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void startRecording(){
        VoiceRecordHelper voiceRecordHelper = new VoiceRecordHelper(context);
        voiceRecordHelper.recordAudio();
    }

    public void stopRecording(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    boolean recording = false;
    boolean isFlashOn = false;
    VoiceRecordHelper voiceRecordHelper;
    FlashLightHelper flashLightHelper;
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dangerButton){
            onClickDanger();
        }
        else if (v.getId() == R.id.record)
        {
            if(!recording){
                voiceRecordHelper = new VoiceRecordHelper(context);
                voiceRecordHelper.recordAudio();
                recordButton.setImageResource(R.drawable.ic_stop);
                recording = true;
            }else {
                voiceRecordHelper.stopRecording();
                recordButton.setImageResource(R.drawable.ic_record);
                recording = false;
            }
        }
        else if(v.getId() == R.id.flash){
            if(!isFlashOn) {
                flashLightHelper = new FlashLightHelper(getActivity());
                flashLightHelper.turnOnFlashLight();
                flashButton.setImageResource(R.drawable.ic_flash_on);
                isFlashOn = true;
            }
            else {
                flashLightHelper.turnOffFlashLight();
                flashButton.setImageResource(R.drawable.ic_flash_off);
                isFlashOn = false;
            }
        }
//        else if(v.getId() == R.id.nearby){
////            LatLng latLng = new LatLng(25.491776, 81.865708);
////            NearbySearchHelper nearbySearchHelper = new NearbySearchHelper(getActivity());
////            nearbySearchHelper.search(latLng, NearbySearchHelper.POLICE_REQUEST);
//            ((MainActivity)getActivity()).showBottomSheet();
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
