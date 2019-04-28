package com.example.springhack;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileTeamlid extends AppCompatActivity {
    String heroName;
    String heroInfo;
    Button fight;
    TextView tv_enemy;
    TextView tv_smallBoss;
    TextView tv_bigBoss;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teamlid);



        heroName = ((TextView) findViewById(R.id.heroName)).getText().toString();
        heroInfo = ((TextView) findViewById(R.id.heroInfo)).getText().toString();

        CardView card = findViewById(R.id.card_view);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileTeamlid.this, Artefact.class);
                startActivity(intent);
            }
        });
        Button Case = findViewById(R.id.case1);
        Case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileTeamlid.this, Tasks.class);
                startActivity(intent);
            }
        });

        fight = findViewById(R.id.button_fight);
        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileTeamlid.this);
                final View view = View.inflate(ProfileTeamlid.this, R.layout.dialog_select_boss, null);
                builder.setView(view);
                final AlertDialog show = builder.show();
                tv_enemy = view.findViewById(R.id.enemy);
                tv_smallBoss = view.findViewById(R.id.enemySmallBoss);
                tv_bigBoss = view.findViewById(R.id.enemyBigBoss);

                tv_bigBoss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ProfileTeamlid.this, BigBoss.class);
                        startActivity(intent);
                        show.dismiss();
                    }
                });
                tv_smallBoss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ProfileTeamlid.this, SmallBoss.class);
                        startActivity(intent);
                        show.dismiss();
                    }
                });
                tv_enemy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ProfileTeamlid.this, Enemy.class);
                        startActivity(intent);
                        show.dismiss();
                    }
                });

            }
        });
    }

}