package in.ac.mnnit.sos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        etEmail = (EditText) findViewById(R.id.emailEditText);
        etEmail.setText(email);
    }
}
