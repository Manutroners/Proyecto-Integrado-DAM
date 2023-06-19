package es.studium.singnuploginrealtime.navBottom;

import static es.studium.singnuploginrealtime.calendar.CalendarUtils.daysInMonthArray;
import static es.studium.singnuploginrealtime.calendar.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;

import es.studium.singnuploginrealtime.R;
import es.studium.singnuploginrealtime.calendar.CalendarAdapter;
import es.studium.singnuploginrealtime.calendar.CalendarUtils;
import es.studium.singnuploginrealtime.calendar.WeekViewActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    ValueEventListener eventListener;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    String name,email,username,password,lvlAccesses,validarUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
       // validarUsername();
        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        lvlAccesses = intent.getStringExtra("lvlAccesses");
        setMonthView();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
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
                    startActivity(intentSearch);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    Intent intentSettings = new Intent(getApplicationContext(),SettingsActivity.class);
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
                    return true;
            }
            return false;
        });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, username);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        Intent intentWeek = new Intent(getApplicationContext(),WeekViewActivity.class);
        intentWeek.putExtra("name", name);
        intentWeek.putExtra("email", email);
        intentWeek.putExtra("username", username);
        intentWeek.putExtra("password", password);
        intentWeek.putExtra("lvlAccesses",lvlAccesses);
        startActivity(intentWeek);
        finish();
    }

}