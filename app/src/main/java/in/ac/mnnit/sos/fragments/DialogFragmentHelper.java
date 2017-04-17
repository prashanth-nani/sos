package in.ac.mnnit.sos.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Banda Prashanth Yadav on 5/3/17.
 */

public class DialogFragmentHelper {

    public static int dialogID;
    private static String dialogTitle;
    private static String dialogMessage;
    private static String positiveText;
    private static String negativeText;
    private static OnDialogResponseListener onDialogResponseListener;
    private FragmentManager fragmentManager;
    CustomDialog customDialogFragment;

    public DialogFragmentHelper(int dialogID, String dialogTitle, String dialogMessage, String negativeText, String positiveText, OnDialogResponseListener listener, FragmentManager fragmentManager) {
        DialogFragmentHelper.dialogID = dialogID;
        DialogFragmentHelper.dialogTitle = dialogTitle;
        DialogFragmentHelper.dialogMessage = dialogMessage;
        DialogFragmentHelper.negativeText = negativeText;
        DialogFragmentHelper.positiveText = positiveText;
        DialogFragmentHelper.onDialogResponseListener = listener;
        this.fragmentManager = fragmentManager;
    }

    public void show(){
        customDialogFragment = new CustomDialog();
        customDialogFragment.setCancelable(false);
        customDialogFragment.show(fragmentManager, "dialog"+dialogID);
    }

    public void dismiss(){
        customDialogFragment.dismiss();
    }

    public static class CustomDialog extends DialogFragment{
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            if(dialogTitle != null)
                builder.setTitle(dialogTitle);

            if(dialogMessage != null)
                builder.setMessage(dialogMessage);

            if(negativeText != null) {
                builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogResponseListener.onNegativeResponse(dialogID);
                    }
                });
            }

            if(positiveText != null) {
                builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogResponseListener.onPositiveResponse(dialogID);
                    }
                });
            }

            return builder.create();
        }
    }

    public interface OnDialogResponseListener{
        void onPositiveResponse(int dialogID);
        void onNegativeResponse(int dialogID);
    }
}
