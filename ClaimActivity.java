package com.arnold.mna.abcinsurance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ClaimActivity extends AppCompatActivity {

    TextView txtTitle;
    Button addPic, btnSubmit;
    ImageView  imageView;
    MaterialSpinner Policy, spStatus;
    ArrayList<String> policyObj;
    ArrayList<String> statusObj;
    private DatabaseReference mDatabase;
    private FirebaseAuth fAuth;
    private ProgressDialog mRegProgress;
    MaterialEditText ClaimDate, FulfillDate, Amount, AmountPaid, Notes;
    static final int DATE_PICKER_ID1 = 1111;
    static final int DATE_PICKER_ID2 = 2222;
    private int year;
    private int month;
    private int day;
    byte[] tmpImage;
    String tmpPolicy, tmpDate, tmpAmount, tmpAmountPaid, tmpStatus, tmpFulfildate, tmpNotes;
    int tmpclmID;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        mRegProgress = new ProgressDialog(this);

        final Calendar c = java.util.Calendar.getInstance();
        year = c.get(java.util.Calendar.YEAR);
        month = c.get(java.util.Calendar.MONTH);
        day = c.get(java.util.Calendar.DAY_OF_MONTH);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Claims").child(fAuth.getCurrentUser().getUid());
        init();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processData();
            }
        });

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        if (tmpPolicy == null || tmpPolicy.length() == 0) {

        } else {
            Amount.setText(tmpAmount);
            AmountPaid.setText(tmpAmountPaid);
            ClaimDate.setText(tmpDate);
            FulfillDate.setText(tmpFulfildate);
            Notes.setText(tmpNotes);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(tmpImage, 0, tmpImage.length));
        }

        ClaimDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID1);
            }
        });

        FulfillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID2);
            }
        });
    }

    private void processData() {
        setData();

        if (fAuth.getCurrentUser() != null){

            final DatabaseReference newClaimRef = mDatabase.push();

            final Map claimMap = new HashMap();

            claimMap.put("claim_date", tmpDate);
            claimMap.put("claim_amount",tmpAmount);
            claimMap.put("claim_amount_paid", tmpAmountPaid);
            claimMap.put("claim_fullfillDate", tmpFulfildate);
            claimMap.put("claim_notes", tmpNotes);
            claimMap.put("claim_status", tmpStatus);
            claimMap.put("claim_policy", tmpPolicy);

            newClaimRef.setValue(claimMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(ClaimActivity.this, "Claim saved successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ClaimActivity.this, startActivity.class));
                        finish();
                    }else {
                        Toast.makeText(ClaimActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData() {

        tmpDate = ClaimDate.getText().toString().trim();
        tmpAmount = Amount.getText().toString().trim();
        tmpAmountPaid = AmountPaid.getText().toString().trim();
        tmpFulfildate = FulfillDate.getText().toString().trim();
        tmpNotes = Notes.getText().toString().trim();

        tmpStatus = spStatus.getSelectedItem().toString().trim();
        //tmpPolicy = Policy.getSelectedItem().toString().trim();

        if (!TextUtils.isEmpty(tmpDate)
                || !TextUtils.isEmpty(tmpAmount)
                || !TextUtils.isEmpty(tmpAmountPaid)
                || !TextUtils.isEmpty(tmpFulfildate)
                || !TextUtils.isEmpty(tmpNotes)
                || !TextUtils.isEmpty(tmpStatus)){

            mRegProgress.setTitle("Registering....");
            mRegProgress.setMessage("Kindly wait while your account is created...");
            mRegProgress.setCanceledOnTouchOutside(false);
            mRegProgress.show();
        }

    }
    private void showToast(String s) {
        Toast.makeText(ClaimActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void checkValidation() {
        if (Policy.getSelectedItem().toString().length() == 0 || Policy.getSelectedItem().toString().equals("Policy")) {
            showToast("Select Policy");
        } else if (ClaimDate.getText().toString().length() == 0) {
            showToast("Select Date");
        } else if (Amount.getText().toString().length() == 0 || Amount.getText().toString().equals("0.0")) {
            showToast("Enter Amount");
        } else if (spStatus.getSelectedItem().toString().length() == 0 || spStatus.getSelectedItem().toString().equals("Status")) {
            showToast("Select Status");
        } else if (spStatus.getSelectedItem().toString().equals("Fulfilled")) {
            if (FulfillDate.getText().toString().length() == 0) {
                showToast("Select Fullfill date");
            } else {
                if (tmpPolicy == null || tmpPolicy.toString().length() == 0) {
                    processData();
                } else {

                }
            }
        } else {
        }
    }
    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ClaimActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if (items[which].equals("Choose from Gallery")){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                }
            }
        });

        builder.show();
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        imageView.setImageBitmap(thumbnail);
        tmpImage = Functions.returnBas64Image(thumbnail);
        Log.e("tmpImage", tmpImage.toString());
        //selectImage = PrefUtils.returnBas64Image(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri imageUri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            imageView.setImageBitmap(bitmap);
            tmpImage = Functions.returnBas64Image(bitmap);
            Log.e("tmpImage", tmpImage.toString());
            // selectImage = PrefUtils.returnBas64Image(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID1:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener1, year, month, day);
            case DATE_PICKER_ID2:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener2, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            ClaimDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

        }
    };

    private DatePickerDialog.OnDateSetListener pickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            FulfillDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

        }
    };

    private void init(){

        ClaimDate = findViewById(R.id.edClaimDate);
        FulfillDate = findViewById(R.id.edFulfillDate);
        Amount = findViewById(R.id.edAmount);
        AmountPaid = findViewById(R.id.edFulfillDate);
        Notes = findViewById(R.id.edNotes);
        Policy = findViewById(R.id.spPolicy);
        spStatus = findViewById(R.id.spStatus);
        addPic = findViewById(R.id.addPic);
        btnSubmit = findViewById(R.id.btnSubmitClaim);

        statusObj = new ArrayList<>();
        statusObj.add("fullySettled");
        statusObj.add("In progress...");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, statusObj);
        spStatus.setAdapter(statusAdapter);
        spStatus.setSelection(getIndex(spStatus, tmpStatus));

    }

    private void fetchPolicyData(){

        ArrayAdapter<String> policyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, policyObj);
        Policy.setAdapter(policyAdapter);
        Policy.setSelection(getIndex(Policy, tmpPolicy));
    }

    private int getIndex(MaterialSpinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++ ){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(String.valueOf(myString)));
            index =i;
            break;
        }

        return index;
    }

}