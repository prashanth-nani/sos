package in.ac.mnnit.sos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import in.ac.mnnit.sos.services.HttpRequestsHelper;
import in.ac.mnnit.sos.models.Credential;

public class LoginActivity extends AppCompatActivity
        implements HttpRequestsHelper.OnServerResponseListener {

    private EditText email;
    private EditText password;
    private FrameLayout progressBarHolder;

    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        email.setText(bundle.getString("email"));
        email.setFocusable(false);
    }

    public void onClickLogin(View v) {
        loginButton = v;
        String passwordText = password.getText().toString().trim();
        if (passwordText.equalsIgnoreCase("")) {
            password.setError("Password is empty");
        } else {
            progressBarHolder.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            Credential cred = new Credential(email.getText().toString(), passwordText);

            HttpRequestsHelper httpRequestsHelper = new HttpRequestsHelper(getApplicationContext(), this);
            httpRequestsHelper.login(cred);
        }
    }

    @Override
    public void onServerResponse(int requestID, Object response) {
        if (requestID == HttpRequestsHelper.LOGIN_REQUEST) {
            progressBarHolder.setVisibility(View.GONE);
            JSONObject result = null;
            String status = "ERROR";
            try {
                result = new JSONObject((String) response);
                status = result.getString("status");

                if (status.equalsIgnoreCase("OK")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putString("name", result.getString("name"));
                    editor.putString("email", result.getString("email"));
                    editor.putString("phone", result.getString("phone"));
                    editor.putString("gender", result.getString("gender"));
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", email.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Snackbar.make(loginButton, "Wrong Credentials", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServerResponseError(int requestID, Object error) {
        if (requestID == HttpRequestsHelper.LOGIN_REQUEST) {
            progressBarHolder.setVisibility(View.GONE);

            Snackbar.make(loginButton, "Couldn't reach the server at the moment", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}