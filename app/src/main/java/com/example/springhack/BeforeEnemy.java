package com.example.springhack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BeforeEnemy extends AppCompatActivity {

    private ListView listView;
    private List<String> alltask;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_enemy);

        listView = findViewById(R.id.listView);
        alltask = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(BeforeEnemy.this);
        userId = preferences.getString("id", null);

        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String counterStr = dataSnapshot.child("users").child(userId).child("tasks").child("counter").getValue(String.class);
                for(int i = 0; i < Integer.parseInt(counterStr)+1; i++){
                    if((dataSnapshot.child("users").child(userId).child("tasks").child(i+"").child("done").getValue(String.class).equals("false")))
                    alltask.add(dataSnapshot.child("users").child(userId).child("tasks").child(i+"").child("taskInfo").getValue(String.class));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(BeforeEnemy.this, android.R.layout.simple_list_item_1, alltask);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Intent intent = new Intent(BeforeEnemy.this, Enemy.class);
                intent.putExtra("numberOfTask", position + "");
                startActivity(intent);

            }
        });
    }
}
