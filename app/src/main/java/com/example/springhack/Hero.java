package com.example.springhack;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Random;

public class Hero extends AppCompatActivity {
    private TextView heroName;
    private TextView heroInfo;
    private String name;
    private String info;
    private String imageId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        heroName =  findViewById(R.id.heroName);
        heroInfo =  findViewById(R.id.heroInfo);
        imageView = findViewById(R.id.imageView2);

        user = mAuth.getInstance().getCurrentUser();

        final Random random = new Random();
        final String numberOfHero = random.nextInt(2)+"";
        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("hero").child(numberOfHero).child("name").getValue(String.class);
                info = dataSnapshot.child("hero").child(numberOfHero).child("info").getValue(String.class);
                imageId = dataSnapshot.child("hero").child(numberOfHero).child("info").getValue(String.class);
                heroName.setText(name);
                heroInfo.setText(info);
                myRef.child("users").child("hero").child("name").setValue(name);
                myRef.child("users").child("hero").child("info").setValue(info);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(imageId);
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(Hero.this).load(uri).into(imageView);
                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
