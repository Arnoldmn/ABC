package com.arnold.mna.abcinsurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText uText;
    private EditText eText;
    private Button btnSignIn;
    private ProgressDialog mLogProgress;
    private FirebaseAuth mAuth;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        init();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = uText.getText().toString();
                String password = eText.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mLogProgress.setTitle("LogIn");
                    mLogProgress.setMessage("Kindly wait for details confirmation");
                    mLogProgress.setCanceledOnTouchOutside(false);
                    mLogProgress.show();

                    LoginUser(email, password);
                }
            }
        });

    }

    private void init(){

        btnSignIn = findViewById(R.id.btnLogin);
        uText = findViewById(R.id.txtUname);
        eText = findViewById(R.id.txtPword);

        mLogProgress = new ProgressDialog(this);
    }
    private void LoginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    mLogProgress.dismiss();
                    Intent Log_inten = new Intent(LoginActivity.this, startActivity.class);
                    Log_inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(Log_inten);
                    finish();
                }else {
                    mLogProgress.hide();
                    Toast.makeText(LoginActivity.this, "Wrong email or password, correct!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

