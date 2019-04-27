package com.example.springhack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Hero extends AppCompatActivity {
    String heroName;
    String heroInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        heroName = ((TextView) findViewById(R.id.heroName)).getText().toString();
        heroInfo = ((TextView) findViewById(R.id.heroInfo)).getText().toString();
    }
}
