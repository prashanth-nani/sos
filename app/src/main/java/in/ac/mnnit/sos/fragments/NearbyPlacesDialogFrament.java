package in.ac.mnnit.sos.fragments;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import in.ac.mnnit.sos.R;

/**
 * Created by prashanth on 5/4/17.
 */

public class NearbyPlacesDialogFrament extends BottomSheetDialogFragment {
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
        dialog.setContentView(contentView);
    }
}
