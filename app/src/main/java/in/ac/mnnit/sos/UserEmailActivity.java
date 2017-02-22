package in.ac.mnnit.sos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import in.ac.mnnit.sos.server.ProcessEmail;

import static com.android.volley.Request.Method.POST;

public class UserEmailActivity extends AppCompatActivity {

    EditText email;
    Button continueBtn;
    ProgressBar progressBar;
    LinearLayout linearLayout;

    private final String processEmailUrl = "http://172.31.74.249/sos/process_email.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("loggedin", false)){
            Intent intent = new Intent(UserEmailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
        else {
            setContentView(R.layout.activity_user_email);

            email = (EditText) findViewById(R.id.emailEditText);
            continueBtn = (Button) findViewById(R.id.continueButton);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            linearLayout = (LinearLayout) findViewById(R.id.activity_user_email);
        }
    }

    public void onClickContinue(final View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setAlpha(0.6f);

        final ProcessEmail processEmail = new ProcessEmail(email.getText().toString(), POST, processEmailUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String isRegistered) {
                        Intent intent;
                        if (isRegistered.equalsIgnoreCase("true")) {
                            intent = new Intent(UserEmailActivity.this, LoginActivity.class);
                        } else {
                            intent = new Intent(UserEmailActivity.this, RegisterActivity.class);
                        }
                        intent.putExtra("email", email.getText().toString());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setAlpha(1f);
                        Snackbar.make(v, "Unable to reach the server at the moment. Please try after sometime.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(UserEmailActivity.this);
        queue.add(processEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.GONE);
        linearLayout.setAlpha(1f);
    }
}
