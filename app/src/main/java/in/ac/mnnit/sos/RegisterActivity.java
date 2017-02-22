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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import in.ac.mnnit.sos.models.User;
import in.ac.mnnit.sos.server.Config;
import in.ac.mnnit.sos.server.RegisterUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText etEmail;
    private EditText password;
    private FrameLayout progressBarHolder;

    private User user;
    Config config = new Config();
    private String BaseUrl = config.getBaseURL();
    private final String registerUrl = BaseUrl.concat("register.php");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        name = (EditText) findViewById(R.id.nameEditText);
        phone = (EditText) findViewById(R.id.phoneEditText);
        radioGroup = (RadioGroup) findViewById(R.id.radioSex);
        etEmail = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        etEmail.setText(email);
        etEmail.setFocusable(false);
    }

    public void onClickRegister(final View v) {
        String nameText = name.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if (nameText.equalsIgnoreCase("")) {
            name.setError("Name cannot be empty");
        } else if (passwordText.equalsIgnoreCase("")) {
            password.setError("Password cannot be empty");
        } else {

            progressBarHolder.setVisibility(View.VISIBLE);

            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);

            user = new User(name.getText().toString(), phone.getText().toString(), radioButton.getText().toString(), etEmail.getText().toString(), password.getText().toString());
            RegisterUser registerUser = new RegisterUser(user, Request.Method.POST, registerUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBarHolder.setVisibility(View.GONE);

                            if (response.equalsIgnoreCase("SUCCESS")) {
                                Snackbar.make(v, "Successfully registered!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.putExtra("name", user.getName());
                                intent.putExtra("email", user.getEmail());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);

                                SharedPreferences sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("loggedin", true);
                                editor.commit();
                            } else {
                                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBarHolder.setVisibility(View.GONE);
                            Snackbar.make(v, "Unable to reach the server at the moment. Please try after sometime.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerUser);
        }
    }
}
