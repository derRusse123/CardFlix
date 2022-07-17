package com.example.cardflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPassword;
    private Button btnRegistration;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        inputEmail = findViewById(R.id.et_Email_Register);
        inputPassword = findViewById(R.id.et_Password_Register);
        inputRepeatPassword = findViewById(R.id.et_Password_Register_Repeat);
        btnRegistration = findViewById(R.id.btn_Register);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");
        myRef = FirebaseDatabase.getInstance("https://cardflix-4cfb8-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Min-length is 6
                if(inputEmail.getText().length() >= 6 && inputPassword.getText().length() >= 6){
                    String eMail = inputEmail.getText().toString();
                    String password = inputPassword.getText().toString();
                    if(!inputPassword.getText().equals(inputRepeatPassword.getText())){
                        signUp(eMail, password);
                    }
                    else{
//TODO: PopUp: passwords don't match
                    }
                }
                else{
//TODO: PopUp: password too short
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null){
            // User already signed In
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
    }

    public boolean signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            System.out.println("createUserWithEmail: success");
                            myRef.child("users").child(mAuth.getUid()).setValue("Placeholder");
                            sendEmailVerification();
                        } else {
                            // sign in fails
// TODO: Popup: task.getException()
                            System.out.println("createUserWithEmail:failed" + task.getException());
                        }
                    }
                });
        if(mAuth.getCurrentUser() != null){
            return true;
        }
        return false;
    }


    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
// TODO: Popup verification mail has been sent to user.getEmail()
                            System.out.println("Email send to " + user.getEmail());
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        else{
                            System.out.println(task.getException().toString());
                        }
                    }
                });
    }
}