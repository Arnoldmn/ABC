package com.arnold.mna.abcinsurance;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class startActivity extends AppCompatActivity {

    CardView cdLogin, cdProducts, cdClaims, cdPolicies, cdAgents;
    FloatingActionButton fabSignUp;
    BottomNavigationView NavBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        cdLogin = findViewById(R.id.cv_account);

        cdAgents = findViewById(R.id.cd_agents);

        cdClaims = findViewById(R.id.cv_claims);

        cdProducts = findViewById(R.id.cv_products);

        cdPolicies = findViewById(R.id.cv_policies);

         fabSignUp = findViewById(R.id.fabClaim);




         cdAgents.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent_agent = new Intent(startActivity.this, agentActivity.class);
                 startActivity(intent_agent);
             }
         });

        cdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        cdClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_intent = new Intent(startActivity.this, ClaimActivity.class);
                startActivity(log_intent);
            }
        });

        fabSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_sign = new Intent(startActivity.this, RegisterActivity.class);
                startActivity(int_sign);
            }
        });

        cdPolicies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_policy = new Intent(startActivity.this, CoverageActivity.class);
                startActivity(intent_policy);
            }
        });

        cdProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_policy = new Intent(startActivity.this, Products.class);
                startActivity(intent_policy);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_nav, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_user){

            FirebaseAuth.getInstance().signOut();
            finish();
        }
        if (item.getItemId() == R.id.menu_info){
            Intent intent_settings = new Intent(startActivity.this, UserActivity.class);
            startActivity(intent_settings);
        }

        if (item.getItemId() == R.id.menu_policies){
            Intent intent_settings = new Intent(startActivity.this, PoliciesActivity.class);
            startActivity(intent_settings);
        }
        return true;
    }




}
