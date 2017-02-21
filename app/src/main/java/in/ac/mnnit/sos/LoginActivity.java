package in.ac.mnnit.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        etEmail = (EditText) findViewById(R.id.emailEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        etEmail.setText(email);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("name", etEmail.getText().toString());
                startActivity(intent);
            }
        });
    }
}
