package es.studium.singnuploginrealtime.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

import es.studium.singnuploginrealtime.R;
import es.studium.singnuploginrealtime.SignupActivity;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV;
    FirebaseDatabase database;
    DatabaseReference reference;
    String name,email,username,password,lvlAccesses;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        lvlAccesses = intent.getStringExtra("lvlAccesses");
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
    }

    public void saveEventAction(View view)
    {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("WeekInfo");
        String eventName = eventNameET.getText().toString();
        String dateSelect = String.valueOf(CalendarUtils.selectedDate);
        Event newEvent = new Event(eventName, dateSelect,username);
        reference.child(eventName).setValue(newEvent);
        Toast.makeText(EventEditActivity.this, "You have create day Info!", Toast.LENGTH_SHORT).show();
        finish();
    }
}