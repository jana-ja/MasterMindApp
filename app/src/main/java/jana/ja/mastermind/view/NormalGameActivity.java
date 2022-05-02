package jana.ja.mastermind.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import jana.ja.mastermind.R;
import jana.ja.mastermind.model.PinRow;
import jana.ja.mastermind.model.NormalGame;
import jana.ja.mastermind.model.PinColor;
import jana.ja.mastermind.model.Stats;

public class NormalGameActivity extends GameActivity{

    private long startTime;
    // timeElapsed stores the accumulated measured time when acitivty is paused
    private long timeElapsed = 0;


    @Override
    void startGame() {
        this.gamei = new NormalGame(settings);

        super.startGame();

        //add to stats
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int numberStarted = sharedPref.getInt(getString(R.string.stats_started_key), 0);
        editor.putInt(getString(R.string.stats_started_key), numberStarted + 1);
        editor.apply();

        // analytics
        Bundle bundle = new Bundle();
        bundle.putString("game_mode", "normal");
        bundle.putBoolean("settings_duplicates", settings.isDuplicatePins());
        bundle.putBoolean("settings_pluto", settings.isEmptyPins());
        bundle.putInt("settings_rounds", settings.getNumberRounds());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);


        startTime = SystemClock.elapsedRealtime();
        timeElapsed = 0;
    }

    @Override
    void nextRound() {
        //dem game zum überprüfen die aktuelle reihe übergeben
        int currentRound = gamei.getCurrenRound();
        PinColor[] pinColors = new PinColor[boardCells[currentRound].length];
        for (int i = 0; i < pinColors.length; i++) {
            pinColors[i] = boardCells[currentRound][i].getPinColor();
        }
        PinRow ergebi = gamei.nextRound(pinColors);
        if (ergebi.isOkay()) {
            //die zahlen wiedergeben
            //richtige stellen
            indicators[currentRound][0].setNumber(ergebi.getCorrectPlaces(), false);
            //richtige farben
            indicators[currentRound][1].setNumber(ergebi.getCorrectColors(), true);

            if (ergebi.getCorrectPlaces() == 4 || gamei.getCurrenRound() > settings.getNumberRounds()-1) {

                //wenn daws true ist hat man gewonnen, sonst muss >9 true sein und dann hat man verloren
                endGame(ergebi.getCorrectPlaces() == 4);
                return;
            }

            highlightCurrentRow();

            // analytics
            Bundle bundle = new Bundle();
            bundle.putString("game_mode", "normal");
            bundle.putBoolean("settings_duplicates", settings.isDuplicatePins());
            bundle.putBoolean("settings_pluto", settings.isEmptyPins());
            bundle.putInt("settings_rounds", settings.getNumberRounds());
            bundle.putInt("rounds", gamei.getCurrenRound());
            mFirebaseAnalytics.logEvent("next_round", bundle);
        } else {
            this.notOkayErrorMessage();
        }
    }

    @Override
    void endGame(boolean won) {
        super.endGame(won);

        PinColor[] solutionPinColors = gamei.getSolution();
        for (int i = 0; i < solutionCells.size(); i++) {
            solutionCells.get(i).setPinColor(solutionPinColors[i]);
            solutionCells.get(i).display();
        }



        //add to stats
        Stats stats = StatsActivity.loadStatsFromPreferences(this);

        long totalTime = timeElapsed + SystemClock.elapsedRealtime() - startTime;
        if (won) {
            // ------ round ------
            int oldNumberWon = stats.getNumberWon();
            //anzahl der rounds zu den avgRoundsPerWin dazu rechnen
            int oldAvgRounds = stats.getAvgRoundsPerWin();
            int newAvgRounds = (oldNumberWon *  oldAvgRounds + 1 * gamei.getCurrenRound()) / (oldNumberWon + 1); //gamei round von 0 - zB 9, beim beenden wird noch einmal erhöht also ist richtig die zahl

            stats.setNumberWon(oldNumberWon + 1);
            stats.setAvgRoundsPerWin(newAvgRounds);

            // ------ time ------

            long newAvgTime = (oldNumberWon * stats.getAvgTime() + 1 * totalTime) / (oldNumberWon + 1);
            stats.setAvgTime(newAvgTime);
            if(totalTime < stats.getShortestTime())
                stats.setShortestTime(totalTime);
            if(totalTime > stats.getLongestTime())
                stats.setLongestTime(totalTime);




        }
        else {
            int numberLost = stats.getNumberLost();
            stats.setNumberLost(numberLost);
        }

        // stats
        StatsActivity.saveStatsToPreferences(this, stats);

        // analytics
        Bundle bundle = new Bundle();
        bundle.putString("game_mode", "normal");
        bundle.putBoolean("settings_duplicates", settings.isDuplicatePins());
        bundle.putBoolean("settings_pluto", settings.isEmptyPins());
        bundle.putInt("settings_rounds", settings.getNumberRounds());
        bundle.putBoolean("won", won);
        bundle.putInt("rounds", gamei.getCurrenRound());
        bundle.putLong("time", totalTime);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END, bundle);

        NormalGameEndDialog dialog = new NormalGameEndDialog(won);
        dialog.show(getSupportFragmentManager(), "game_end_alert");

    }

    @Override
    void resetView() {
        super.resetView();
        //show current row
        highlightCurrentRow();
    }

    @Override
    protected void onClickSolutionCell(View v) {
        //macht hier nichts
    }

    @Override
    protected void onClickBoardCell(View v) {

        BoardCell celli = (BoardCell) v;
        this.selectedBoardCell = celli;
        // only do something when a pin color is selected and the board cell is in the currently active row
        if (selectedPinColor != null && celli.getxPos() == gamei.getCurrenRound()) {
            // when the specific pin color is already set -> remove it
            if(selectedBoardCell.getPinColor() == selectedPinColor)
                this.selectedBoardCell.setPinColor(PinColor.EMPTY);
            // else set this pin color
            else
                this.selectedBoardCell.setPinColor(selectedPinColor);

            this.selectedBoardCell.display();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // dont measure time when acitivty is not active
        timeElapsed += SystemClock.elapsedRealtime() - startTime;
    }

    @Override
    protected void onResume() {
        super.onResume();

        startTime = SystemClock.elapsedRealtime();
    }

}
