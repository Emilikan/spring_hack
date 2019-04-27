package com.example.springhack;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    String password;
    String login;
    Button signIn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        login = ((EditText) findViewById(R.id.Email)).getText().toString();
        password = ((EditText) findViewById(R.id.Password)).getText().toString();

        signIn = findViewById(R.id.confirm);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Profile.class);
                startActivity(intent);

            }
        });
    }
}
