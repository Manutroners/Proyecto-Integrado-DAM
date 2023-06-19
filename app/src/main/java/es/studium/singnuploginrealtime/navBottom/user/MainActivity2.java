package es.studium.singnuploginrealtime.navBottom.user;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.studium.singnuploginrealtime.R;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String lvlAccesses = intent.getStringExtra("lvlAccesses");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_search:
                    Intent intentSearch = new Intent(getApplicationContext(),SearchActivity2.class);
                    intentSearch.putExtra("name", name);
                    intentSearch.putExtra("email", email);
                    intentSearch.putExtra("username", username);
                    intentSearch.putExtra("password", password);
                    intentSearch.putExtra("lvlAccesses",lvlAccesses);
                    startActivity(intentSearch);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    Intent intentSettings = new Intent(getApplicationContext(),SettingsActivity2.class);
                    intentSettings.putExtra("name", name);
                    intentSettings.putExtra("email", email);
                    intentSettings.putExtra("username", username);
                    intentSettings.putExtra("password", password);
                    intentSettings.putExtra("lvlAccesses",lvlAccesses);
                    startActivity(intentSettings);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    Intent intentProfile = new Intent(getApplicationContext(),ProfileActivity2.class);
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
}