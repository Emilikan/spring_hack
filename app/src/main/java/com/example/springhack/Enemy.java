package com.example.springhack;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

                String team = dataSnapshot.child("users").child(userId).child("team").getValue(String.class);

                int i1 = Integer.parseInt(dataSnapshot.child("users").child(userId).child("tasks").child(number+"").child("stat").getValue(String.class));
                int i2 = Integer.parseInt(dataSnapshot.child("team").child(team).child("case").child("stat").getValue(String.class));
                int i = i1*100/i2;

                xp.setProgress(i);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button button = findViewById(R.id.button_fight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad;
                ad = new AlertDialog.Builder(Enemy.this);
                ad.setTitle("Инфо");  // заголовок
                ad.setMessage("Вы уверены, что повергли этого врага?"); // сообщение
                ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        myRef = database.getReference();
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String thisStat = dataSnapshot.child("users").child(userId).child("stat").getValue(String.class);
                                String statFromEnemy = dataSnapshot.child("users").child(userId).child("tasks").child(number).child("stat").getValue(String.class);

                                int i = Integer.parseInt(thisStat) + Integer.parseInt(statFromEnemy);

                                myRef.child("users").child(userId).child("stat").setValue(Integer.toString(i));

                                myRef.child("users").child(userId).child("tasks").child(number).child("done").setValue("true");

                                Toast.makeText(Enemy.this, "Молодец, вы все ближе к цели", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Enemy.this, Profile.class);
                                startActivity(intent);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                ad.setNegativeButton("Нет, вернуться назад", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
                ad.setCancelable(true);
                ad.show();
            }
        });
    }
}
