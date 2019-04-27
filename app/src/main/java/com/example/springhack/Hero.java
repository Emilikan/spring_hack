package com.example.springhack;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Hero extends AppCompatActivity {
    private TextView heroName;
    private TextView heroInfo;
    private String name;
    private String info;
    private String imageUri;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        heroName =  findViewById(R.id.heroName);
        heroInfo =  findViewById(R.id.heroInfo);
        user = mAuth.getInstance().getCurrentUser();

        final Random random = new Random();

        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = Integer.parseInt(dataSnapshot.child("hero").child("counter").getValue(String.class));
                final String numberOfHero = random.nextInt(count+1)+"";
                name = dataSnapshot.child("hero").child(numberOfHero).child("name").getValue(String.class);
                info = dataSnapshot.child("hero").child(numberOfHero).child("info").getValue(String.class);
                imageUri = dataSnapshot.child("hero").child(numberOfHero).child("id_image").getValue(String.class);

                heroName.setText(name);
                heroInfo.setText(info);
                myRef.child("users").child(user.getUid()).child("hero_main").child("name").setValue(name);
                myRef.child("users").child(user.getUid()).child("hero_main").child("info").setValue(info);
                myRef.child("users").child(user.getUid()).child("hero_main").child("id_image").setValue(imageUri);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
