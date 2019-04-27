package com.example.springhack;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Random;

public class Hero extends AppCompatActivity {
    TextView heroName;
    TextView heroInfo;
    String name;
    String info;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        heroName =  findViewById(R.id.heroName);
        heroInfo =  findViewById(R.id.heroInfo);

        final Random random = new Random();
        final String numberOfHero = random.nextInt(2)+"";
        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("hero").child(numberOfHero).child("name").getValue(String.class);
                info = dataSnapshot.child("hero").child(numberOfHero).child("info").getValue(String.class);
                heroName.setText(name);
                heroInfo.setText(info);
                myRef.child("users").child("hero").child("name").setValue(name);
                myRef.child("users").child("hero").child("info").setValue(info);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



    }




}


