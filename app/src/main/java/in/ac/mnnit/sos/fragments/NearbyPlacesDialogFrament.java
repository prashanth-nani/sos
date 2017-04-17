package in.ac.mnnit.sos.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.PlacesActivity;
import in.ac.mnnit.sos.R;
import in.ac.mnnit.sos.services.NearbySearchHelper;

/**
 * Created by Banda Prashanth Yadav on 5/4/17.
 */

public class NearbyPlacesDialogFrament extends BottomSheetDialogFragment implements View.OnClickListener{

    private ImageButton policeButton;
    private ImageButton hospitalButton;
    private ImageButton fireButton;
    private ImageButton atmButton;

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_nearby_menu, null);
        policeButton = (ImageButton) contentView.findViewById(R.id.police_button);
        policeButton.setOnClickListener(this);
        hospitalButton = (ImageButton) contentView.findViewById(R.id.hospital_button);
        hospitalButton.setOnClickListener(this);
        fireButton = (ImageButton) contentView.findViewById(R.id.fire_button);
        fireButton.setOnClickListener(this);
        atmButton = (ImageButton) contentView.findViewById(R.id.atm_button);
        atmButton.setOnClickListener(this);
        dialog.setContentView(contentView);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.police_button:
                Log.d("Tag", "Poice");
                i = new Intent(getContext(), PlacesActivity.class);
                i.putExtra("typeId", NearbySearchHelper.POLICE_REQUEST);
                startActivity(i);
                break;
            case R.id.hospital_button:
                Log.d("Tag", "Hospital");
                i = new Intent(getContext(), PlacesActivity.class);
                i.putExtra("typeId", NearbySearchHelper.HOSPITAL_REQUEST);
                startActivity(i);
                break;
            case R.id.fire_button:
                Log.d("Tag", "Fire");
                i = new Intent(getContext(), PlacesActivity.class);
                i.putExtra("typeId", NearbySearchHelper.FIRE_REQUEST);
                startActivity(i);
                break;
            case R.id.atm_button:
                Log.d("Tag", "ATM");
                i = new Intent(getContext(), PlacesActivity.class);
                i.putExtra("typeId", NearbySearchHelper.ATM_REQUEST);
                startActivity(i);
                break;
        }
        ((MainActivity)getActivity()).dismissBottomSheet();
    }
}
