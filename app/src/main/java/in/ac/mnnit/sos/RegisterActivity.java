package in.ac.mnnit.sos;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import in.ac.mnnit.sos.models.User;
import in.ac.mnnit.sos.server.RegisterUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText etEmail;
    private EditText password;
    private Button registerButton;
    private User user;
    private final String registerUrl = "http://172.31.74.249/sos/register.php";

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
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//        });
        etEmail.setText(email);
    }

    public void onClickRegister(final View v) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        Snackbar.make(v, "Processing", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        user = new User(name.getText().toString(), phone.getText().toString(), radioButton.getText().toString(), etEmail.getText().toString(), password.getText().toString());
        RegisterUser registerUser = new RegisterUser(user, Request.Method.POST, registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("SUCCESS")){
                            Snackbar.make(v, "Successfully registered!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.putExtra("name", user.getName());
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(v, "Registration Failed!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerUser);
    }
}
