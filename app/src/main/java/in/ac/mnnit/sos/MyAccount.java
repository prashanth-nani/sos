package in.ac.mnnit.sos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        TextView name = (TextView)findViewById(R.id.account_name);
        TextView email = (TextView) findViewById(R.id.account_email);
        TextView phone = (TextView) findViewById(R.id.account_phone);
        TextView gender = (TextView) findViewById(R.id.account_gender);

        name.setText(prefs.getString("name", "N/A"));
        email.setText(prefs.getString("email", "N/A"));
        phone.setText(prefs.getString("phone", "N/A"));
        gender.setText(prefs.getString("gender", "N/A"));

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
