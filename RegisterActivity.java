package com.arnold.mna.abcinsurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText Emailtxt, fNametxt,
            mNametxt, lNametxt, mNumbertxt,
            mMedtxt, mPasswordtxt;
    String cusGender;
    private Spinner mGendertxt;
    private ImageButton imgSelect;
    private Uri imgUri = null;
    private Button btnReg;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private FirebaseAuth fAuth;
    private ProgressDialog mProgress;
    ArrayList<String> mGender;
    private static final int    GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase instances
        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mGender = new ArrayList<>();


        init();

        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = Emailtxt.getText().toString().trim();
                String userFname = fNametxt.getText().toString().trim();
                String userMname = mNametxt.getText().toString().trim();
                String userLname = lNametxt.getText().toString().trim();
                String userPhone = mNumbertxt.getText().toString().trim();
                String userPass = mPasswordtxt.getText().toString().trim();
                String userMed = mMedtxt.getText().toString().trim();

                createAccount(userEmail,
                        userPass,
                        userFname,
                        userMname,
                        userLname,
                        userPhone,
                        userMed);
            }
        });
    }


    private void createAccount(final String userEmail,
                               String userPass,
                               final String userFname,
                               final String userMname,
                               final String userLname,
                               final String userPhone,
                               final String userMed) {

        mProgress.setMessage("Saving to database");
        mProgress.show();

        fAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Customers").child(uid);

                            StorageReference filePath = mStorageRef.child("customers_image").child(imgUri.getLastPathSegment());
                            filePath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                                    HashMap<String, String> customerMap = new HashMap<>();

                                    customerMap.put("firstName", userFname);
                                    customerMap.put("middleName", userMname);
                                    customerMap.put("lastName", userLname);
                                    customerMap.put("mobileNumber", userPhone);
                                    customerMap.put("email", userEmail);
                                    customerMap.put("medicalCondition", userMed);
                                    customerMap.put("image", downloadUri.toString());

                                    mDatabase.setValue(customerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "Customer account successfully created", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, startActivity.class));
                                            }


                                        }
                                    });

                                }
                            });
                        }{
                            Toast.makeText(RegisterActivity.this, "Fill all the fiels...", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });

    }

    private void init(){

        imgSelect = findViewById(R.id.imgLog);
        Emailtxt = findViewById(R.id.txtEmail);
        fNametxt = findViewById(R.id.txtFname);
        mNametxt = findViewById(R.id.txtMname);
        lNametxt = findViewById(R.id.txtLname);
        mNumbertxt = findViewById(R.id.Nuphone);
        mGendertxt = findViewById(R.id.spinGender);
        mMedtxt = findViewById(R.id.txtMedCondition);
        mPasswordtxt = findViewById(R.id.txt_Password);
        btnReg = findViewById(R.id.buttonReg);
        mProgress = new ProgressDialog(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

             imgUri = data.getData();
            imgSelect.setImageURI(imgUri);
        }
    }
}
