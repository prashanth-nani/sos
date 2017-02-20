package in.ac.mnnit.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import in.ac.mnnit.sos.server.ProcessEmail;

import static com.android.volley.Request.Method.POST;

public class UserEmailActivity extends AppCompatActivity {

    EditText email;
    Button continueBtn;
    private final String processEmailUrl = "http://172.31.74.249/sos/process_email.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_email);

        email = (EditText) findViewById(R.id.emailEditText);
        continueBtn = (Button) findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessEmail processEmail = new ProcessEmail(email.getText().toString(), POST, processEmailUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String isRegistered) {
                                Intent intent;
                                Toast.makeText(UserEmailActivity.this, isRegistered, Toast.LENGTH_SHORT).show();
                                if(isRegistered.equalsIgnoreCase("true")){
                                    intent = new Intent(UserEmailActivity.this, LoginActivity.class);
                                }
                                else {
                                    intent = new Intent(UserEmailActivity.this, RegisterActivity.class);
                                }
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Toast.makeText(UserEmailActivity.this, "Unable to reach the server at the moment. Please try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(UserEmailActivity.this);
                queue.add(processEmail);
            }
        });
    }
}
