package com.example.redcarpet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
    private Spinner mSex;
    private EditText mAge;
    private Switch mShareLocation;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mPhoneNumber = (EditText)findViewById(R.id.inputPhoneNumber);
        mNicknane = (EditText)findViewById(R.id.inputNickname);
        mSex = (Spinner) findViewById(R.id.inputSex);
        mAge = (EditText)findViewById(R.id.inputAge);
        mShareLocation = (Switch) findViewById(R.id.shareLocation);
        mSave = (Button) findViewById(R.id.btnSave);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) { // User is signed in
                    userId = user.getUid();
                    mPhoneNumber.setText(user.getPhoneNumber());
                    mPhoneNumber.setEnabled(false);
                } else { // User is signed out
                    startActivity(new Intent(MainActivity.this, PhoneAuthActivity.class));
                }
            }
        };

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });

        //startActivity(new Intent(MainActivity.this, PhoneAuthActivity.class));
        createTabHost();
    }


    public class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }

    private void writeNewUser() {
        User user = new User(mNicknane.toString(), mAge.toString());

        mDatabase.child("users").child(userId).setValue(user);
    }





    public void createTabHost() {
        TabHost host = (TabHost)findViewById(R.id.tab_host);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
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
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

        //Tab 5 - Settings
        spec = host.newTabSpec("Settings");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Tab Three");
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
