package in.ac.mnnit.sos.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.ac.mnnit.sos.PlacesActivity;
import in.ac.mnnit.sos.R;

/**
 * Created by prashanth on 12/4/17.
 */

public class PlaceDetailsBottomSheetFragment extends BottomSheetDialogFragment {

//    public static PlaceDetailsBottomSheetFragment newInstance(Place place){
//        PlaceDetailsBottomSheetFragment placeDetailsBottomSheetFragment = new PlaceDetailsBottomSheetFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("place", (Serializable) place);
//        placeDetailsBottomSheetFragment.setArguments(args);
//        return placeDetailsBottomSheetFragment;
//    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_place_details, null);
        TextView placeName = (TextView) contentView.findViewById(R.id.place_details_name);
        TextView placeAddress = (TextView) contentView.findViewById(R.id.place_details_address);
        TextView placePhone = (TextView) contentView.findViewById(R.id.place_details_phone);

        placeName.setText(PlacesActivity.place.getName());

        if (PlacesActivity.place.getAddress() != null)
            placeAddress.setText(PlacesActivity.place.getAddress());
        else
            placeAddress.setVisibility(View.INVISIBLE);

        if (PlacesActivity.place.getPhone() != null)
            placePhone.setText(PlacesActivity.place.getPhone());
        else
            placePhone.setVisibility(View.INVISIBLE);

        placePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneUri = "tel:" + PlacesActivity.place.getPhone();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneUri));
                startActivity(intent);
            }
        });

        placeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+PlacesActivity.place.getLatitude()+","+PlacesActivity.place.getLongitude()));
                startActivity(intent);
            }
        });

        dialog.setContentView(contentView);
    }
}
