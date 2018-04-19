package com.arnold.mna.abcinsurance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnold.mna.abcinsurance.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private TextView mUserFName,
            mUserMidName,
            mUserLName,
            mUserPNumber,
            mUserEmail,
            mUserMCondition;
    private ImageView img;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserFName = findViewById(R.id.userFName);
        mUserMidName = findViewById(R.id.userMidName);
        mUserLName = findViewById(R.id.userLastName);
        mUserPNumber = findViewById(R.id.userPhoneNumber);
        mUserEmail = findViewById(R.id.userEmail);
        mUserMCondition = findViewById(R.id.userMedical);
        img = findViewById(R.id.logImg);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Customers").child(currentUser.getUid());
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            User mUser = new User();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String mUserfname = dataSnapshot.child("firstName").getValue().toString();
                    String mUserMidname = dataSnapshot.child("middleName").getValue().toString();
                    String mUserLasname = dataSnapshot.child("lastName").getValue().toString();
                    String mUserPhone = dataSnapshot.child("mobileNumber").getValue().toString();
                    String mEmail = dataSnapshot.child("email").getValue().toString();
                    String mUserMed = dataSnapshot.child("medicalCondition").getValue().toString();

                    mUserFName.setText(mUserfname);
                    mUserMidName.setText(mUserMidname);
                    mUserLName.setText(mUserLasname);
                    mUserPNumber.setText(mUserPhone);
                    mUserEmail.setText(mEmail);
                    mUserMCondition.setText(mUserMed);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
