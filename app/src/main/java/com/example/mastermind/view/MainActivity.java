package com.example.mastermind.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mastermind.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlay, btnSettings, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.button_play);
        btnSettings = findViewById(R.id.button_settings);
        btnStats = findViewById(R.id.button_stats);

        btnPlay.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnStats.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_play:
                Intent intiPlay = new Intent(this, GameActivity.class);
                startActivity(intiPlay);
                break;
            case R.id.button_settings:
                Intent intiSettings = new Intent(this, SettingsActivity.class);
                startActivity(intiSettings);
                break;
            case R.id.button_stats:
                Intent intiStats = new Intent(this,StatsActivity.class);
                startActivity(intiStats);
                break;
        }
    }
}