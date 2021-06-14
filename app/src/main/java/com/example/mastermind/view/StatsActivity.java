package com.example.mastermind.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;

public class StatsActivity extends AppCompatActivity {

    TextView valueStarted, textWon, textLost, textQuit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        this.valueStarted = findViewById(R.id.value_started);
        this.textWon = findViewById(R.id.value_won);
        this.textLost = findViewById(R.id.value_lost);
        this.textQuit = findViewById(R.id.value_quit);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);

        int numberStarted = sharedPref.getInt(getString(R.string.stats_started_key), 0);
        int numberWon = sharedPref.getInt(getString(R.string.stats_won_key), 0);
        int numberLost = sharedPref.getInt(getString(R.string.stats_lost_key), 0);
        int numberQuit = sharedPref.getInt(getString(R.string.stats_quit_key), 0);

        //TODO hat Stats objekt einen nutzen?

        valueStarted.setText(String.valueOf(numberStarted));
        textWon.setText(String.valueOf(numberWon));
        textLost.setText(String.valueOf(numberLost));
        textQuit.setText(String.valueOf(numberQuit));

        //TODO etwas zum stats resetten
        //TODO schön machen
        //TODO stats gestartete games
        //TODO beendete games



    }


}
