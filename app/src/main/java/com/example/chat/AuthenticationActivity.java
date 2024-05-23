package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.databinding.ActivityAuthenticationBinding;
import com.example.chat.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class AuthenticationActivity extends AppCompatActivity {
ActivityAuthenticationBinding binding;
String name,email,pass;

    EditText edtname,edtEmail,edtPass;
    TextView btnSignUp,btnLogIn;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // binding =ActivityAuthenticationBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        edtname = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogIn = findViewById(R.id.logIn);
        btnSignUp = findViewById(R.id.singUp);


    btnLogIn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              email=edtEmail.getText().toString();
              pass = edtPass.getText().toString();

              login();
          }

      });
       btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                
               signUp();
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
           finish();
        }
    }

    private void login() {
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(),pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthenticationActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                });


    }




    private void signUp() {

        startActivity(new Intent(AuthenticationActivity.this,signup.class));

    }



}