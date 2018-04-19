package com.arnold.mna.abcinsurance;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class agentActivity extends AppCompatActivity {

    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave, imgImport;
    MaterialEditText edName, edPhone, edAddress, edNotes;
    String tempName, tempAddress, tempPhone, tempNotes;
    int tempId;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Agents");

        init();

        imgImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void init() {

        imgClose =  findViewById(R.id.imgClose);
        imgBack =   findViewById(R.id.imgBack);
        imgImport = findViewById(R.id.imgImport);
        imgSave =   findViewById(R.id.imgSave);

        edName =  findViewById(R.id.edName);
        edPhone = findViewById(R.id.edPhone);
        edAddress = findViewById(R.id.edAddress);
        edNotes =  findViewById(R.id.edNotes);

        txtSubHeading =  findViewById(R.id.txtSubHeading);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edName.getText().toString().trim().length() == 0) {
                    Toast.makeText(agentActivity.this, "Please enter agent details !!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (tempName == null || tempName.toString().trim().length() == 0) {
                        processSave();
                    } else {

                    }

                }
            }
        });
    }

    private void processSave() {
        setData();



        final DatabaseReference newAgentRef = mDatabase.push();

        Map<String, String> agentMap = new HashMap<>();

        agentMap.put("agent_name", tempName);
        agentMap.put("agent_email", tempAddress);
        agentMap.put("agent_mob_number", tempPhone);
        agentMap.put("agent_notes", tempNotes);

        newAgentRef.setValue(agentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(agentActivity.this, "Agent created successfully...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(agentActivity.this, "Fill all fields...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setData() {

        tempName = edName.getText().toString().trim();
        tempAddress = edAddress.getText().toString().trim();
        tempPhone = edPhone.getText().toString().trim();
        tempNotes = edNotes.getText().toString().trim();
    }


}
