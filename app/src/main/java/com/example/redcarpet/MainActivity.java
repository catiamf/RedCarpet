package com.example.redcarpet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;


import com.example.redcarpet.Database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mPhoneNumber;
    private EditText mNicknane;
    private String userId;
    private Spinner mGender;
    private EditText mAge;
    private Switch mShareLocation;
    private Button mSave;
    private Button mSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables to work with Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Variables to work with screen contents
        mPhoneNumber = (EditText)findViewById(R.id.inputPhoneNumber);
        mNicknane = (EditText)findViewById(R.id.inputNickname);
        mGender = (Spinner) findViewById(R.id.inputGender);
        mAge = (EditText)findViewById(R.id.inputAge);
        mShareLocation = (Switch) findViewById(R.id.shareLocation);
        mSave = (Button) findViewById(R.id.btnSave);
        mSignOut = (Button) findViewById(R.id.btnSignOut);

        //Procedure to work with choices list (Gender)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(adapter);

        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync((OnMapReadyCallback) MainActivity.this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) { // User is signed in
                    userId = user.getUid();
                    mPhoneNumber.setText(user.getPhoneNumber());
                    mPhoneNumber.setEnabled(false);
                } else { // User is signed out
                    openPhoneVerification();
                }
            }
        };

        createTabHost();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                openPhoneVerification();
            }
        });
    }

    public void showMap() {

    }

    public void writeNewUser() {
        User user = new User(
                    mNicknane.getText().toString(),
                    mGender.getSelectedItem().toString(),
                    mAge.getText().toString(),
                    null);
        mDatabase.child("users").child(userId).setValue(user);
    }

    public void openPhoneVerification() {
        startActivity(new Intent(MainActivity.this, PhoneAuthActivity.class));
    }

    public void createTabHost() {
        TabHost host = (TabHost)findViewById(R.id.tab_host);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Chat");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Chat");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Event");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Event");
        host.addTab(spec);

        //Tab 5 - Settings
        spec = host.newTabSpec("Settings");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Sets");
        host.addTab(spec);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
