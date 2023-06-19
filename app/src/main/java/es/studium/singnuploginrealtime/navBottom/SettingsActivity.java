package es.studium.singnuploginrealtime.navBottom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.studium.singnuploginrealtime.LoginActivity;
import es.studium.singnuploginrealtime.R;
import es.studium.singnuploginrealtime.calendar.WeekViewActivity;
import es.studium.singnuploginrealtime.settings.editSettings;

public class SettingsActivity extends AppCompatActivity {

    TextView nameText;
    Button editPerfile,logOut;
    ImageView perfilImage;
    String name,email,username,password,lvlAccesses;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editPerfile = findViewById(R.id.editPerfile);
        logOut = findViewById(R.id.logOut);
        perfilImage = findViewById(R.id.perfilImage);
        nameText = findViewById(R.id.nameID);
        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        lvlAccesses = intent.getStringExtra("lvlAccesses");
        nameText.setText(name);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    Intent intentHome = new Intent(getApplicationContext(),MainActivity.class);
                    intentHome.putExtra("name", name);
                    intentHome.putExtra("email", email);
                    intentHome.putExtra("username", username);
                    intentHome.putExtra("password", password);
                    intentHome.putExtra("lvlAccesses",lvlAccesses);
                    startActivity(intentHome);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_search:
                    Intent intentSearch = new Intent(getApplicationContext(),SearchActivity.class);
                    intentSearch.putExtra("name", name);
                    intentSearch.putExtra("email", email);
                    intentSearch.putExtra("username", username);
                    intentSearch.putExtra("password", password);
                    intentSearch.putExtra("lvlAccesses",lvlAccesses);
                    intentSearch.putExtra("lvlAccesses",lvlAccesses);
                    startActivity(intentSearch);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    return true;
                case R.id.bottom_profile:
                    Intent intentProfile = new Intent(getApplicationContext(),ProfileActivity.class);
                    intentProfile.putExtra("name", name);
                    intentProfile.putExtra("email", email);
                    intentProfile.putExtra("username", username);
                    intentProfile.putExtra("password", password);
                    intentProfile.putExtra("lvlAccesses",lvlAccesses);
                    startActivity(intentProfile);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

    }

    public void logOut(View view)
    {
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    public void EditProfile(View view)
    {
        Intent intentSettings = new Intent(this,editSettings.class);
        intentSettings.putExtra("name", name);
        intentSettings.putExtra("email", email);
        intentSettings.putExtra("username", username);
        intentSettings.putExtra("password", password);
        intentSettings.putExtra("lvlAccesses",lvlAccesses);
        startActivity(intentSettings);
        finish();
    }
}