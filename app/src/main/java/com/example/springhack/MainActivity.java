package com.example.springhack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.annotation.NonNull;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private String password;
    private String login;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn = findViewById(R.id.signIn);
        Button signUp = findViewById(R.id.signUp);

        login = ((EditText) findViewById(R.id.Email)).getText().toString();
        password = ((EditText) findViewById(R.id.Password)).getText().toString();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = database.getReference();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.child("users").child(user.getUid()).child("hero").getValue(String.class)).equals("false")) {
                            Toast.makeText(getApplicationContext(),"Нужен персонаж",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Успешно",Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singInUser();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void singInUser() {
        login = ((EditText) findViewById(R.id.Email)).getText().toString();
        password = ((EditText) findViewById(R.id.Password)).getText().toString();
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // sing in

                } else {
                    //sing out
                }
            }
        };

        mAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()  {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    myRef = database.getReference();
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.child("users").child(user.getUid()).child("hero").getValue(String.class)).equals("false")) {
                                myRef.child("users").child(user.getUid()).child("hero").setValue("true");
                                Intent intent = new Intent(MainActivity.this, SignUp.class);
                                intent.putExtra("PARAM", 1);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "У вас  уже есть персонаж", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Авторизация провалена", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}

