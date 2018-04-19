package com.arnold.mna.abcinsurance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arnold.mna.abcinsurance.model.Policies;
import com.arnold.mna.abcinsurance.model.User;
import com.arnold.mna.abcinsurance.view.PoliciesViewHoler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PoliciesActivity extends AppCompatActivity {

    private RecyclerView mPoliciesList;
    private FirebaseAuth fAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabasePolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        mPoliciesList = findViewById(R.id.policiesDetails);

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        mDatabasePolicies = FirebaseDatabase.getInstance().getReference().child("Policies").child(currentUser.getUid());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);


        mPoliciesList.setHasFixedSize(true);
        mPoliciesList.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Policies, PoliciesViewHoler> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Policies, PoliciesViewHoler>(
                        Policies.class,
                        R.layout.simple_list_item_1,
                        PoliciesViewHoler.class,
                        mDatabasePolicies
                ) {
                    @Override
                    protected void populateViewHolder(PoliciesViewHoler viewHolder, Policies model, int position) {

                        viewHolder.setpolicy_number(model.getPolicy_number());
                        viewHolder.setpolicy_property(model.getPolicy_details());
                    }

                };
        mPoliciesList.setAdapter(firebaseRecyclerAdapter);
    }
}
