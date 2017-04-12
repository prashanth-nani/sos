package in.ac.mnnit.sos.fragments;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.TextView;

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
        TextView placeD = (TextView) contentView.findViewById(R.id.place);
        placeD.setText(PlacesActivity.place.getPhone());
        dialog.setContentView(contentView);
    }
}
