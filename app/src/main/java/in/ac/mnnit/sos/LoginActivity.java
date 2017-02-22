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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import in.ac.mnnit.sos.models.Credential;
import in.ac.mnnit.sos.server.Config;
import in.ac.mnnit.sos.server.LoginUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FrameLayout progressBarHolder;

    private Config config = new Config();
    private String BaseUrl = config.getBaseURL();
    private String loginUrl = BaseUrl.concat("login.php");

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

    public void onClickLogin(final View v) {
        String passwordText = password.getText().toString().trim();
        if (passwordText.equalsIgnoreCase("")) {
            password.setError("Password is empty");
        }
        else {
            progressBarHolder.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            Credential cred = new Credential(email.getText().toString(), passwordText);
            LoginUser loginUser = new LoginUser(cred, Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBarHolder.setVisibility(View.GONE);

                            if (response.equalsIgnoreCase("true")) {
                                SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("loggedin", true);
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("name", email.getText().toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                            } else {
                                Snackbar.make(v, "Wrong Credentials", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBarHolder.setVisibility(View.GONE);

                            Snackbar.make(v, "Couldn't reach the server at the moment", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginUser);
        }
    }
}
