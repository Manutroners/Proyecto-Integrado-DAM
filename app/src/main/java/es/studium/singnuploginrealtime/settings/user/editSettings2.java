package es.studium.singnuploginrealtime.settings.user;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.studium.singnuploginrealtime.HelperClass;
import es.studium.singnuploginrealtime.R;
import es.studium.singnuploginrealtime.navBottom.SettingsActivity;
import es.studium.singnuploginrealtime.navBottom.user.SettingsActivity2;

public class editSettings2 extends AppCompatActivity {

    TextView usernameText;
    EditText nameText,mailText;
    String name,email,username,password,lvlAccesses,usernameEdited;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_settings2);
        Intent intent = getIntent();
        name= intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        lvlAccesses = intent.getStringExtra("lvlAccesses");

        nameText = findViewById(R.id.nameText);
        usernameText = findViewById(R.id.usernameID);
        mailText = findViewById(R.id.mailText);

        nameText.setText(name);
        usernameText.setText("Hello!," + username);
        mailText.setText(email);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");


    }

    public void saveInfoEdit(View view)
    {
        updateData();
        finish();
    }

    public void updateData() {
        String nameEdited = nameText.getText().toString();
        //usernameEdited = usernameText.getText().toString();
        String mailEdited = mailText.getText().toString();
        if (nameEdited.isEmpty() || mailEdited.isEmpty())
        {
            Toast.makeText(this, "No puedes dejar huecos en blanco", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HelperClass helperClass = new HelperClass(nameEdited, mailEdited, username, password, lvlAccesses);
            reference.child(username).setValue(helperClass);
            refreshInfo();
            Toast.makeText(this, "Se ha editado correctamente", Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private void refreshInfo()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    String nameFromDB = snapshot.child(username).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                    String lvlAccessesDB = snapshot.child(username).child("lvlAccesses").getValue(String.class);
                    Intent intent = new Intent(editSettings2.this, SettingsActivity2.class);
                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);
                    intent.putExtra("lvlAccesses",lvlAccessesDB);
                    startActivity(intent);
                } else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
