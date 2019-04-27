package com.example.springhack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmallBoss extends AppCompatActivity {
    TextView tv_textName;
    TextView tv_hero_info;
    Button go_kik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_small_boss);

        tv_textName = findViewById(R.id.heroName);
        //tv_textName.setText();
        tv_hero_info = findViewById(R.id.heroInfo);
        //tv_hero_info.setText();
        go_kik = findViewById(R.id.button_fight);
        go_kik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
