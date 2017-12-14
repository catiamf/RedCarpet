package com.example.redcarpet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.redcarpet.Database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PartyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        //Variables to work with Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDescription = (EditText)findViewById(R.id.inputNickname);


        public void writeNewEvent() {
            User user = new User(
                    mDescription.getText().toString(),
                    mGender.getSelectedItem().toString(),
                    mAge.getText().toString(),
                    null);
            mDatabase.child("users").child(userId).setValue(user);
        }

    }

}
