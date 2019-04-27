package com.example.springhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    String name;
    String firstName;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = ((EditText) findViewById(R.id.name)).getText().toString();
        firstName = ((EditText) findViewById(R.id.firstname)).getText().toString();

        signUp = findViewById(R.id.confirm2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,Hero.class);
                startActivity(intent);
            }
        });
    }
}
