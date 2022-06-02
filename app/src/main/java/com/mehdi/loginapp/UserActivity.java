package com.mehdi.loginapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView nameTxtView;
    private TextView emailTxtView;


    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    Button Logout;
    private static final String USERS = "users";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());




        emailTxtView = findViewById(R.id.email_textview);


        Logout = findViewById(R.id.buttonLogout);



        Intent in = getIntent();
        String string = in.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setTitle("logout !").
                        setMessage("you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(UserActivity.this,LoginActivity.class));
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, mail, profession, workplace, phone;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (Objects.equals(keyId.child("email").getValue(), emailTxtView)) {
                        fname = keyId.child("email").getValue(String.class);

                        break;
                    }
                }

                emailTxtView.setText(fname);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}