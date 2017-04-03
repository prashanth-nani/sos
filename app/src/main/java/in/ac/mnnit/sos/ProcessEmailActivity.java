package in.ac.mnnit.sos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import in.ac.mnnit.sos.services.HttpRequestsHelper;
import in.ac.mnnit.sos.extras.Utils;

public class ProcessEmailActivity extends AppCompatActivity
        implements HttpRequestsHelper.OnServerResponseListener {

    private EditText email;
    private View continueBtnView;
    private FrameLayout progressBarHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("loggedin", false)) {
            Intent intent = new Intent(ProcessEmailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_process_email);

            email = (EditText) findViewById(R.id.emailEditText);
            progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        }
    }

    public void onClickContinue(View v) {
        continueBtnView = v;
        Utils utils = new Utils();
        String emailText = email.getText().toString().trim();

        if (emailText.equalsIgnoreCase("")) {
            email.setError("Enter your email ID");
        } else if (!utils.isValidEmail(emailText)){
            email.setError("Invalid email ID");
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            progressBarHolder.setVisibility(View.VISIBLE);

            HttpRequestsHelper httpRequestsHelper = new HttpRequestsHelper(getApplicationContext(), this);
            httpRequestsHelper.processUserEmail(emailText);
        }
    }

    @Override
    public void onServerResponse(int requestID, Object response) {
        if(requestID == HttpRequestsHelper.PROCESS_EMAIL_REQUEST){
            progressBarHolder.setVisibility(View.GONE);

            Intent intent;
            if (((String)response).equalsIgnoreCase("true")) {
                intent = new Intent(ProcessEmailActivity.this, LoginActivity.class);
            } else {
                intent = new Intent(ProcessEmailActivity.this, RegisterActivity.class);
            }
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public void onServerResponseError(int requestID, Object error) {
        if(requestID == HttpRequestsHelper.PROCESS_EMAIL_REQUEST){
            progressBarHolder.setVisibility(View.GONE);

            Snackbar.make(continueBtnView, "Unable to reach the server at the moment. Please try after sometime.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
