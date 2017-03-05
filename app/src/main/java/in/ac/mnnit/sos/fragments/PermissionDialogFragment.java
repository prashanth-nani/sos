package in.ac.mnnit.sos.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.R;

/**
 * Created by prashanth on 5/3/17.
 */

public class PermissionDialogFragment extends DialogFragment {

    View mainActivityView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivityView = getActivity().findViewById(R.id.content);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Grant read contacts permission?");
        builder.setMessage("Permission to read contacts is needed to pick emergency contacts from your phone book.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).showPermissionRequiredSnackbar();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).openAppSettings();
            }
        });
        return builder.create();
    }
}
