package es.studium.singnuploginrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.studium.singnuploginrealtime.navBottom.MainActivity;

public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String lvlAccesses = "user";
                Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                            if (passwordFromDB.equals(password)) {
                                signupUsername.setText("");
                                signupPassword.setText("");
                                Toast.makeText(SignupActivity.this, "Contraseña y/o usuario ya existentes",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                signupUsername.setText("");
                                signupPassword.setText("");
                                Toast.makeText(SignupActivity.this, "Contraseña y/o usuario ya existentes",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                                Toast.makeText(SignupActivity.this, "No puedes dejar huecos en blanco",
                                        Toast.LENGTH_SHORT).show();
                                signupName.setText("");
                                signupEmail.setText("");
                                signupUsername.setText("");
                                signupPassword.setText("");

                            }else{
                                if (email.contains("@gmail.com") || email.contains("@gmail.es")) {
                                    //lvlAccesses = "user";
                                    HelperClass helperClass = new HelperClass(name, email, username, password, lvlAccesses);
                                    System.out.println(helperClass);
                                    reference.child(username).setValue(helperClass);
                                    Toast.makeText(SignupActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(SignupActivity.this, "El correo tiene que ser valido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });



            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}