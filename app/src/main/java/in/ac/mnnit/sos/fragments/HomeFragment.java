package in.ac.mnnit.sos.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.database.LocalDatabaseAdapter;
import in.ac.mnnit.sos.services.AlarmService;
//import in.ac.mnnit.sos.services.FlashService;
import in.ac.mnnit.sos.services.LocationDetailsHolder;
import in.ac.mnnit.sos.services.MessageService;


public class HomeFragment extends Fragment implements View.OnClickListener {

    Button dangerButton;
//    Button flashButton;
    private AlarmService alarmService;
//    private FlashService flashService;
    private boolean serviceStarted = false;
    private boolean serviceInitiated =  false;
    private View view;

    Timer alarmTimer;
    Timer taskTimer;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmService = new AlarmService(getActivity());
//        flashService = new FlashService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            dangerButton = (Button) view.findViewById(R.id.dangerButton);
            dangerButton.setOnClickListener(this);
//            flashButton = (Button) view.findViewById(R.id.flashButton);
//            flashButton.setOnClickListener(this);
        }
        return view;
    }

    public void initializeTimers(){
        alarmTimer = new Timer();
        taskTimer = new Timer();
    }

    public void onClickDanger(){
        dangerButton.setText("Click to abort within 10s");

        if(!serviceStarted && !serviceInitiated) {
            initializeTimers();
            long interval =  5 * 60 * 1000; //5 Min
            serviceInitiated = true;
            alarmTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startAlarm();
                    serviceStarted = true;
                    changeText();
                }
            }, 10000);


            taskTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    kickOffTasks();
                }
            }, 20 * 1000, interval);
        }else{
            dangerButton.setText("I am in\r\ndanger");
            stopAlarm();
            alarmTimer.cancel();
            taskTimer.cancel();
            serviceStarted = false;
            serviceInitiated = false;
        }
    }

    public void changeText(){
        getActivity().runOnUiThread(new Runnable() {
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
        alarmService.startAlarm();
    }

    public void stopAlarm(){
        alarmService.stopAlarm();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dangerButton){
            onClickDanger();
        }
//        else if (v.getId() == R.id.flashButton){
//            startFlash();
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
