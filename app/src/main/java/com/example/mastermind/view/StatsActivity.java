package com.example.mastermind.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;
import com.example.mastermind.model.Stats;

import java.util.List;

public class StatsActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    TextView valueStarted, textWon, textLost, textQuit;
    Button btnResetStats;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        this.valueStarted = findViewById(R.id.value_started);
        this.textWon = findViewById(R.id.value_won);
        this.textLost = findViewById(R.id.value_lost);
        this.textQuit = findViewById(R.id.value_quit);

        this.btnResetStats = findViewById(R.id.button_reset_stats);
        this.btnResetStats.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);//TODO hard coded strings
                builder.setMessage("Wirklich die Statistiken löschen?").setPositiveButton("Ja", this)
                        .setNegativeButton("Nein", this).show();
        });

        loadStats();



    }

    private void resetStats() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        List<String> allStatsKeys = Stats.getAllStatsKeys(this);
        allStatsKeys.forEach(key -> editor.putInt(key, 0));
        editor.apply();

        loadStats();
    }

    private void loadStats() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);

        // TODO auch das stats objekt verwenden? ne glaube nicht kp
        int numberStarted = sharedPref.getInt(getString(R.string.stats_started_key), 0);
        int numberWon = sharedPref.getInt(getString(R.string.stats_won_key), 0);
        int numberLost = sharedPref.getInt(getString(R.string.stats_lost_key), 0);
//        int numberQuit = sharedPref.getInt(getString(R.string.stats_quit_key), 0);
        int numberQuit = numberStarted - numberWon - numberLost;

        //TODO hat Stats objekt einen nutzen?

        valueStarted.setText(String.valueOf(numberStarted));
        textWon.setText(String.valueOf(numberWon));
        textLost.setText(String.valueOf(numberLost));
        textQuit.setText(String.valueOf(numberQuit));

        //TODO etwas zum stats resetten
        //TODO schön machen
        //TODO durchschnittliche versuche gebraucht bei gewonnen

        //TODO stats pro spielmodus??
        //TODO warum gridlayout nicht vertikale trennung in der mitte????


    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                resetStats();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }
    }
}
