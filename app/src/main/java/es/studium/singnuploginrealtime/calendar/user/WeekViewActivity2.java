package es.studium.singnuploginrealtime.calendar.user;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static es.studium.singnuploginrealtime.calendar.CalendarUtils.daysInWeekArray;
import static es.studium.singnuploginrealtime.calendar.CalendarUtils.monthYearFromDate;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.studium.singnuploginrealtime.R;
import es.studium.singnuploginrealtime.calendar.CalendarAdapter;
import es.studium.singnuploginrealtime.calendar.CalendarUtils;
import es.studium.singnuploginrealtime.calendar.Event;
import es.studium.singnuploginrealtime.calendar.EventAdapter;
import es.studium.singnuploginrealtime.navBottom.MainActivity;
import es.studium.singnuploginrealtime.navBottom.ProfileActivity;
import es.studium.singnuploginrealtime.navBottom.SearchActivity;
import es.studium.singnuploginrealtime.navBottom.SettingsActivity;
import es.studium.singnuploginrealtime.navBottom.user.MainActivity2;
import es.studium.singnuploginrealtime.navBottom.user.SearchActivity2;
import es.studium.singnuploginrealtime.navBottom.user.SettingsActivity2;

public class WeekViewActivity2 extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    String date;
    String name,email,username,password,lvlAccesses;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        FirebaseApp.initializeApp(this);
        initWidgets();
        setWeekView();
        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        lvlAccesses = intent.getStringExtra("lvlAccesses");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    Intent intentHome = new Intent(getApplicationContext(), MainActivity2.class);
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
                    Intent intentSearch = new Intent(getApplicationContext(), SearchActivity2.class);
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
                    Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity2.class);
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
        eventListView = findViewById(R.id.eventListView);

    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        String nullo = "";
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this, nullo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater() {
        date = String.valueOf(CalendarUtils.selectedDate);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WeekInfo");
        ArrayList<Event> dailyEvents = Event.eventsForDate(String.valueOf(CalendarUtils.selectedDate));
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        ArrayList<Event> dailyEventsEmpty = Event.eventsForDate(String.valueOf(CalendarUtils.selectedDate));
        EventAdapter eventAdapterEmpty = new EventAdapter(getApplicationContext(), dailyEventsEmpty);

        eventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                        if (itemSnapshot.getValue(Event.class).getUsername().equals(username)) {
                            if (itemSnapshot.getValue(Event.class).getDate().equals(date)) {
                                Event event = itemSnapshot.getValue(Event.class);
                                System.out.println(event.getDate() + "------------" + event.getName() + "----------------");
                                dailyEvents.add(event);
                            }
                        }
                    }

                    eventListView.setAdapter(eventAdapter);

                    // Configurar el listener de clic largo en el ListView
                    eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            deleteDataFromFirebase(dailyEvents.get(position).getName());
                            eventAdapter.notifyDataSetChanged();
                            Toast.makeText(WeekViewActivity2.this, "Deleted", Toast.LENGTH_SHORT).show();

                            Intent intentWeekData = new Intent(getApplicationContext(),WeekViewActivity2.class);
                            intentWeekData.putExtra("name", name);
                            intentWeekData.putExtra("email", email);
                            intentWeekData.putExtra("username", username);
                            intentWeekData.putExtra("password", password);
                            intentWeekData.putExtra("lvlAccesses",lvlAccesses);
                            startActivity(intentWeekData);
                            //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            finish();
                            return true;
                        }
                    });
                } else {
                    eventListView.setAdapter(eventAdapterEmpty);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void deleteDataFromFirebase(String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WeekInfo");

        // Obtiene una referencia al nodo específico que deseas eliminar
        DatabaseReference nodeReference = reference.child(name);

        // Elimina los datos del nodo
        nodeReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // La eliminación se realizó correctamente
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Se produjo un error al eliminar los datos
                    }
                });
    }

    public void newEventAction(View view) {
        Intent intentNewEvent = new Intent(getApplicationContext(),EventEditActivity2.class);
        intentNewEvent.putExtra("name", name);
        intentNewEvent.putExtra("email", email);
        intentNewEvent.putExtra("username", username);
        intentNewEvent.putExtra("password", password);
        intentNewEvent.putExtra("lvlAccesses",lvlAccesses);
        startActivity(intentNewEvent);
        //startActivity(new Intent(this, EventEditActivity.class));
    }
}