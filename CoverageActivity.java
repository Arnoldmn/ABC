package com.arnold.mna.abcinsurance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CoverageActivity extends AppCompatActivity {

    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave;
    ListView listView;
    MaterialEditText edPolicyNumber,
                     edProductD,
                     edDate,
                     expDate,
                     edTerm,
                     edInsSumm,
                     edPrem,
                     edDownP,
                     edSales,
                     edDeduct,
                     edCharge,
                     edCredit,
                    edBal;
    String tempclientName, tempagentName,temppolNo, temppolType, temppolDetails, tempefftDate,
    tempexpDate, temptermLength, tempinsSummary, temppremium, tempdownPayment, tempsalesCommision,
    tempdeductible, temppayMethod, temppayFreq, tempstatus, tempcharge, tempcredit, tempbalance;

    MaterialSpinner spCType,
                    spAgent,
                    spPType,
                    spPayM,
                    spPayFrq,
                    spStatus;

    int temppolID;
    ArrayList<String> policyType;
    ArrayList<String> paymentMethod;
    ArrayList<String> paymentFreq;
    ArrayList<String> status;
    private int year;
    private int month;
    private int day;
    private DatabaseReference mDatabase;
    private FirebaseAuth fAuth;
    static final int DATE_PICKER_ID1 = 1111;
    static final int DATE_PICKER_ID2 = 2222;
    ArrayList<String> clientData;
    ArrayList<String> agentData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Policies").child(fAuth.getCurrentUser().getUid());
        init();

        policyType = new ArrayList<>();
        policyType.add("Vehicle (auto)");
        policyType.add("Property");
        policyType.add("Home");
        policyType.add("Health");
        policyType.add("Life");
        policyType.add("Other");
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, policyType);
        spPType.setAdapter(clientAdapter);
        spPType.setSelection(getSimpleIndex(spPType, temppolType));

        paymentMethod = new ArrayList<>();
        paymentMethod.add("Cash/Check");
        paymentMethod.add("Debit Card/Credit Card");
        paymentMethod.add("Direct Debit/Standing Order");
        paymentMethod.add("In-House Payroll");
        paymentMethod.add("Other");
        ArrayAdapter<String> payMethodAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, paymentMethod);
        spPayM.setAdapter(payMethodAdapter);
        spPayM.setSelection(getSimpleIndex(spPayM, temppayMethod));

        paymentFreq = new ArrayList<>();
        paymentFreq.add("01-Unique/Yearly");
        paymentFreq.add("12-Monthly");
        paymentFreq.add("06-Three-Monthly/Quarterly");
        paymentFreq.add("02-Semi-Annually/Bi-Annually");
        paymentFreq.add("24-Semi-Monthly");
        ArrayAdapter<String> payFreqAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, paymentFreq);
        spPayFrq.setAdapter(payFreqAdapter);
        spPayFrq.setSelection(getSimpleIndex(spPayFrq, temppayFreq));

        status = new ArrayList<>();
        status.add("Active");
        status.add("Inactive");
        status.add("Canceled");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, status);
        spStatus.setAdapter(statusAdapter);
        spStatus.setSelection(getSimpleIndex(spStatus, tempstatus));


        spPType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        edProductD.setText("Vehicle Type:\nMake/Model:\nMarket Price:\nPrimary use:\nYear:\nMileage:\nTransmission:\nColor:");
                        break;
                    case 1:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 2:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 3:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 4:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 5:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 6:
                        edProductD.setText("");
                        break;
                    case 7:
                        edProductD.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID1);
            }
        });

        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID2);
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTodb();
            }
        });


    }

    private void saveTodb() {
        setData();

        if (fAuth.getCurrentUser() != null){

            final DatabaseReference newPolicyRef = mDatabase.push();

            final Map policyMap = new HashMap();

            policyMap.put("policy_number", temppolNo);
            policyMap.put("policy_type", policyType);
            policyMap.put("policy_details", temppolDetails);
            policyMap.put("policy_effe_date", tempefftDate);
            policyMap.put("policy_exp_date", tempexpDate);
            policyMap.put("policy_summary", tempinsSummary);
            policyMap.put("policy_premium", temppremium);
            policyMap.put("polcy_down_payment", tempdownPayment);
            policyMap.put("policy_pay_method", temppayMethod);
            policyMap.put("policy_freq", temppayFreq);
            policyMap.put("policy_length", temptermLength);
            policyMap.put("policy_deductible", tempdeductible);

            newPolicyRef.setValue(policyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CoverageActivity.this, "Policy saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                        //Toast.makeText(CoverageActivity.this, "Fill all the fields...", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void setData() {

        /*+
        tempclientName = spCType.getSelectedItem().toString();
         */
        /*
        tempagentName = spAgent.getSelectedItem().toString();
         */
        temppolNo = edPolicyNumber.getText().toString().trim();
        temppolType = spPType.getSelectedItem().toString();
        temppolDetails = edProductD.getText().toString().trim();
        tempefftDate = edDate.getText().toString().trim();
        tempexpDate = expDate.getText().toString().trim();
        tempinsSummary = edInsSumm.getText().toString().trim();
        temppremium = edPrem.getText().toString().trim();
        tempdownPayment = edDownP.getText().toString().trim();
        tempsalesCommision = edSales.getText().toString().trim();
        temppayMethod = spPayM.getSelectedItem().toString();
        temppayFreq = spPayFrq.getSelectedItem().toString();
        temptermLength = edTerm.getText().toString().trim();
        tempdeductible = edDeduct.getText().toString().trim();

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
    //private method of your class
    private int getSimpleIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(String.valueOf(myString)));
            index =i;
            break;
        }
        return index;
    }
    private void init() {


        edPolicyNumber =  findViewById(R.id.edPolicyNumber);
        edProductD = findViewById(R.id.edProductD);
        edDate =    findViewById(R.id.edDate);
        expDate =   findViewById(R.id.expDate);
        edTerm =    findViewById(R.id.edTerm);
        edInsSumm = findViewById(R.id.edInsSumm);
        edPrem =    findViewById(R.id.edPrem);
        edDownP =   findViewById(R.id.edDownP);
        edSales =   findViewById(R.id.edSales);
        edDeduct =  findViewById(R.id.edDeduct);
        edCharge =  findViewById(R.id.edCharge);
        edCredit =  findViewById(R.id.edCredit);
        edBal =     findViewById(R.id.edBal);

        spCType =  findViewById(R.id.spCType);
        spAgent =  findViewById(R.id.spAgent);
        spPType =  findViewById(R.id.spPType);
        spPayM =   findViewById(R.id.spPayM);
        spPayFrq = findViewById(R.id.spPayFrq);
        spStatus = findViewById(R.id.spStatus);

        imgClose =  findViewById(R.id.imgClose);
        imgBack =   findViewById(R.id.imgBack);
        imgSave =   findViewById(R.id.imgSave);

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
            edDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

            StringBuilder sDate;
            StringBuilder eDate;

            sDate = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);


            if (expDate.getText().toString().trim().length() == 0) {
                expDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
                eDate = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);
            } else {
                String tempDate = expDate.getText().toString();

                int tempdays = Integer.valueOf(tempDate.substring(0, expDate.getText().toString().indexOf("-")));
                int tempmonth = Integer.valueOf(tempDate.substring(expDate.getText().toString().indexOf("-") + 1, expDate.getText().toString().lastIndexOf("-")));
                int tempyear = Integer.valueOf(tempDate.substring(expDate.getText().toString().lastIndexOf("-") + 1, expDate.length()));

                Log.e("### days", "" + tempdays);
                Log.e("### tempmonth", "" + tempmonth);
                Log.e("### tempyear", "" + tempyear);
                eDate = new StringBuilder().append(tempyear).append("-").append(tempmonth).append("-").append(tempdays);
            }


            LocalDate startDate = new LocalDate(sDate.toString().trim());
            LocalDate endDate = new LocalDate(eDate.toString().trim());

            Period period = new Period(startDate, endDate, PeriodType.months());
            Log.e("############## dif", "" + period.getMonths());

            if (period.getMonths() <= 0) {
                edTerm.setText("0");
            } else {
                edTerm.setText("" + period.getMonths());
            }

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
            expDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

            StringBuilder sDate;
            StringBuilder eDate;

            eDate = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);


            if (edDate.getText().toString().trim().length() == 0) {
                edDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
                sDate = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day);
            } else {
                String tempDate = edDate.getText().toString();

                int tempdays = Integer.valueOf(tempDate.substring(0, edDate.getText().toString().indexOf("-")));
                int tempmonth = Integer.valueOf(tempDate.substring(edDate.getText().toString().indexOf("-") + 1, edDate.getText().toString().lastIndexOf("-")));
                int tempyear = Integer.valueOf(tempDate.substring(edDate.getText().toString().lastIndexOf("-") + 1, edDate.length()));

                sDate = new StringBuilder().append(tempyear).append("-").append(tempmonth).append("-").append(tempdays);
            }


            LocalDate startDate = new LocalDate(sDate.toString().trim());
            LocalDate endDate = new LocalDate(eDate.toString().trim());

            Period period = new Period(startDate, endDate, PeriodType.months());
            Log.e("############## dif", "" + period.getMonths());

            if (period.getMonths() <= 0) {
                edTerm.setText("0");
            } else {
                edTerm.setText("" + period.getMonths());
            }


        }
    };

}
