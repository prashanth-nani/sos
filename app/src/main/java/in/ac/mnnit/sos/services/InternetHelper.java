package in.ac.mnnit.sos.services;

import android.content.Context;
import android.os.AsyncTask;

import in.ac.mnnit.sos.MainActivity;
import in.ac.mnnit.sos.extras.Utils;

/**
 * Created by prashanth on 6/3/17.
 */

public class InternetHelper extends AsyncTask<Context, Void, Boolean> {

    Context context;
    public InternetHelper(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Show loading dialog
    }

    @Override
    protected Boolean doInBackground(Context...params) {
        return new Utils().isInternetAvailable();
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            ((MainActivity) context).onInternetConnected();
        }
        else {
            ((MainActivity) context).showInternetNotConnectedDialog();
        }
    }
}
