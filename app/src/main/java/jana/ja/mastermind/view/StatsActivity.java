package jana.ja.mastermind.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jana.ja.mastermind.R;
import jana.ja.mastermind.model.Stats;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView valueStarted, valueWon, valueLost, valueQuit, valueAvgRounds, valueShortestTime, valueLongestTime, valueAvgTime;
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
        this.valueShortestTime = findViewById(R.id.value_shortest_time);
        this.valueLongestTime = findViewById(R.id.value_longest_time);
        this.valueAvgTime = findViewById(R.id.value_avg_time);

        this.btnResetStats = findViewById(R.id.button_reset_stats);
        this.btnResetStats.setOnClickListener(v -> {
                CustomAlertDialog dialog = new CustomAlertDialog(getString(R.string.alert_text_reset_stats), this);
                dialog.show(getSupportFragmentManager(), "reset_stats_alert");
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
        // time
        editor.putLong(context.getString(R.string.stats_shortest_time_key), stats.getShortestTime());
        editor.putLong(context.getString(R.string.stats_longest_time_key), stats.getLongestTime());
        editor.putLong(context.getString(R.string.stats_avg_time_key), stats.getAvgTime());

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
        // time
        stats.setLongestTime(sharedPref.getLong(context.getString(R.string.stats_shortest_time_key), Long.MIN_VALUE));
        stats.setShortestTime(sharedPref.getLong(context.getString(R.string.stats_longest_time_key), Long.MAX_VALUE));
        stats.setAvgTime(sharedPref.getLong(context.getString(R.string.stats_avg_time_key), 0));

        return stats;
    }

    private void resetStats() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        List<String> allStatsKeys = getAllStatsKeys();
        allStatsKeys.forEach(key -> editor.putInt(key, 0));
        editor.putLong(getString(R.string.stats_shortest_time_key), Long.MAX_VALUE);
        editor.putLong(getString(R.string.stats_longest_time_key), Long.MIN_VALUE);
        editor.putLong(getString(R.string.stats_avg_time_key), 0);
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

        // time
        valueShortestTime.setText(convertTime(stats.getShortestTime()));
        valueLongestTime.setText(convertTime(stats.getLongestTime()));
        valueAvgTime.setText(convertTime(stats.getAvgTime()));

        //TODO stats pro spielmodus??

    }

    private String convertTime(long time) {
        if(time == Long.MAX_VALUE || time == Long.MIN_VALUE || time == 0)
            return "-";
        long sek = time / 1000;
        int min = (int)(sek / 60);
        sek = sek % 60;
        if(sek < 10)
            return min + ":0" +sek;
        else
            return min + ":" + sek;
    }

    private List<String> getAllStatsKeys(){
        ArrayList<String> allKeys = new ArrayList<>();

        allKeys.add(getString(R.string.stats_started_key));
        allKeys.add(getString(R.string.stats_won_key));
        allKeys.add(getString(R.string.stats_lost_key));
        allKeys.add(getString(R.string.stats_avg_rounds_key));
//        allKeys.add(getString(R.string.stats_shortest_time_key));
//        allKeys.add(getString(R.string.stats_longest_time_key));
//        allKeys.add(getString(R.string.stats_avg_time_key));


        return allKeys;

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_positive) {
            resetStats();
        }
    }
}
