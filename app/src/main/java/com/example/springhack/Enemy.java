package com.example.springhack;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Enemy extends AppCompatActivity {
    private ImageView imageView;
    private TextView heroName;
    private TextView heroInfo;
    private TextView taskInfo;
    private TextView href;
    private ProgressBar xp;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private String userId;

    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemy);

        imageView = findViewById(R.id.logo);
        heroInfo = findViewById(R.id.heroInfo);
        heroName = findViewById(R.id.heroName);
        taskInfo = findViewById(R.id.taskInfo);
        href = findViewById(R.id.link);
        xp = findViewById(R.id.progressBar2);

        number = getIntent().getStringExtra("numberOfTask");

        Toast.makeText(Enemy.this, number, Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Enemy.this);
        userId = preferences.getString("id", null);


        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                heroInfo.setText("Информация о герое: " + dataSnapshot.child("users").child(userId).child("tasks").child(number).child("info").getValue(String.class));
                heroName.setText("Имя героя: " + dataSnapshot.child("users").child(userId).child("tasks").child(number).child("name").getValue(String.class));
                taskInfo.setText("Задача: " + dataSnapshot.child("users").child(userId).child("tasks").child(number).child("taskInfo").getValue(String.class));
                href.setText("Ссылка: " + dataSnapshot.child("users").child(userId).child("tasks").child(number).child("href").getValue(String.class));
                String imageUri = dataSnapshot.child("users").child(userId).child("tasks").child(number).child("id_image").getValue(String.class);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                if(imageUri!=null) {
                    StorageReference storageRef = storage.getReferenceFromUrl(imageUri);
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(Enemy.this).load(uri).into(imageView);
                        }
                    });
                }

                xp.setProgress(Integer.parseInt(dataSnapshot.child("users").child(userId).child("tasks").child(number).child("stat").getValue(String.class)));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
