package com.example.mastermind.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mastermind.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlay, btnPlayAI, btnHowtoplay, btnSettings, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.button_play);
        btnPlayAI = findViewById(R.id.button_play_ai);
        btnHowtoplay = findViewById(R.id.button_how_to_play);
        btnSettings = findViewById(R.id.button_settings);
        btnStats = findViewById(R.id.button_stats);

        btnPlay.setOnClickListener(this);
        btnHowtoplay.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnStats.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("ai_mode", false)) {
            btnPlayAI.setOnClickListener(this);
            btnPlayAI.setVisibility(View.VISIBLE);
        } else {
            btnPlayAI.setOnClickListener(null);
            btnPlayAI.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_play) {
            Intent intiPlay = new Intent(this, NormalGameActivity.class);
            startActivity(intiPlay);
        } else if (id == R.id.button_how_to_play) {
            Intent intiHowtoplay = new Intent(this, HowToPlayActivity.class);
            startActivity(intiHowtoplay);
        } else if (id == R.id.button_play_ai) {
            Intent intiPlayReverse = new Intent(this, ReverseGameActivity.class);
            startActivity(intiPlayReverse);
        } else if (id == R.id.button_settings) {
            Intent intiSettings = new Intent(this, SettingsActivity.class);
            startActivity(intiSettings);
        } else if (id == R.id.button_stats) {
            Intent intiStats = new Intent(this, StatsActivity.class);
            startActivity(intiStats);
        }
    }
}