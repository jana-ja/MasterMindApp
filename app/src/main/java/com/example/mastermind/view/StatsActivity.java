package com.example.mastermind.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;
import com.example.mastermind.model.Stats;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    TextView valueStarted, valueWon, valueLost, valueQuit, valueAvgRounds;
    Button btnResetStats;
    Stats stats;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        this.valueStarted = findViewById(R.id.value_started);
        this.valueWon = findViewById(R.id.value_won);
        this.valueLost = findViewById(R.id.value_lost);
        this.valueQuit = findViewById(R.id.value_quit);
        this.valueAvgRounds = findViewById(R.id.value_avg_rounds);

        this.btnResetStats = findViewById(R.id.button_reset_stats);
        this.btnResetStats.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.alert_text_reset_stats)).setPositiveButton(R.string.text_alert_positive, this)
                        .setNegativeButton(R.string.text_alert_negative, this).show();
        });

        stats = loadStatsFromPreferences(this);
        loadStatsToView();



    }

    public static void saveStatsToPreferences(Context context, Stats stats) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(context.getString(R.string.stats_started_key), stats.getNumberStarted());
        editor.putInt(context.getString(R.string.stats_won_key), stats.getNumberWon());
        editor.putInt(context.getString(R.string.stats_lost_key), stats.getNumberLost());
        editor.putInt(context.getString(R.string.stats_avg_rounds_key), stats.getAvgRoundsPerWin());

        editor.apply();

    }

    public static Stats loadStatsFromPreferences(Context context) {
        Stats stats = new Stats();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        stats.setNumberStarted(sharedPref.getInt(context.getString(R.string.stats_started_key), 0));
        stats.setNumberWon(sharedPref.getInt(context.getString(R.string.stats_won_key), 0));
        stats.setNumberLost(sharedPref.getInt(context.getString(R.string.stats_lost_key), 0));
        stats.setAvgRoundsPerWin(sharedPref.getInt(context.getString(R.string.stats_avg_rounds_key), 0));

        return stats;
    }

    private void resetStats() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        List<String> allStatsKeys = getAllStatsKeys();
        allStatsKeys.forEach(key -> editor.putInt(key, 0));
        editor.apply();

        stats = loadStatsFromPreferences(this);
        loadStatsToView();
    }

    private void loadStatsToView() {

        int numberStarted = stats.getNumberStarted();
        int numberWon = stats.getNumberWon();
        int numberLost = stats.getNumberLost();
        int numberQuit = numberStarted - numberWon - numberLost;
        int avgRounds = stats.getAvgRoundsPerWin();

        valueStarted.setText(String.valueOf(numberStarted));
        valueWon.setText(String.valueOf(numberWon));
        valueLost.setText(String.valueOf(numberLost));
        valueQuit.setText(String.valueOf(numberQuit));
        valueAvgRounds.setText(String.valueOf(avgRounds));


        //TODO stats pro spielmodus??

    }

    private List<String> getAllStatsKeys(){
        ArrayList<String> allKeys = new ArrayList<>();

        allKeys.add(getString(R.string.stats_started_key));
        allKeys.add(getString(R.string.stats_won_key));
        allKeys.add(getString(R.string.stats_lost_key));
        allKeys.add(getString(R.string.stats_avg_rounds_key));

        return allKeys;

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
