package com.example.mastermind.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.mastermind.R;
import com.example.mastermind.model.Ergebnis;
import com.example.mastermind.model.NormalGame;
import com.example.mastermind.model.PinColor;
import com.example.mastermind.model.Stats;

public class NormalGameActivity extends GameActivity{


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
    }

    @Override
    void nextRound() {
        //dem game zum überprüfen die aktuelle reihe übergeben
        int currentRound = gamei.getCurrenRound();
        PinColor[] pinColors = new PinColor[boardCells[currentRound].length];
        for (int i = 0; i < pinColors.length; i++) {
            pinColors[i] = boardCells[currentRound][i].getPinColor();
        }
        Ergebnis ergebi = gamei.nextRound(pinColors);
        if (ergebi.isOkay()) {
            //die zahlen wiedergeben
            //richtige stellen
            indicators[currentRound][0].setTextColor(Color.RED);
            indicators[currentRound][0].setText(String.valueOf(ergebi.getCorrectPlaces()));
            //richtige farben
            indicators[currentRound][1].setTextColor(Color.BLACK);
            indicators[currentRound][1].setText(String.valueOf(ergebi.getCorrectColors()));

            if (ergebi.getCorrectPlaces() == 4 || gamei.getCurrenRound() > settings.getNumberRounds()-1) {

                //wenn daws true ist hat man gewonnen, sonst muss >9 true sein und dann hat man verloren
                endGame(ergebi.getCorrectPlaces() == 4);
                return;
            }

            highlightCurrentRow();
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
            solutionCells.get(i).displayUnselected(this);
        }

        //add to stats
        Stats stats = StatsActivity.loadStatsFromPreferences(this);

        String dialogText;
        if (won) {
            dialogText = "GEWONNEN";
            int numberWon = stats.getNumberWon();
            //anzahl der rounds zu den avgRoundsPerWin dazu rechnen
            int oldAvgRounds = stats.getAvgRoundsPerWin();
            int newAvgRounds = (numberWon *  oldAvgRounds + 1 * gamei.getCurrenRound()) / (numberWon + 1); //gamei round von 0 - zB 9, beim beenden wird noch einmal erhöht also ist richtig die zahl

            stats.setNumberWon(numberWon + 1);
            stats.setAvgRoundsPerWin(newAvgRounds);

        }
        else {
            dialogText = "VERLOREN";
            int numberLost = stats.getNumberLost();
            stats.setNumberLost(numberLost);
        }

        StatsActivity.saveStatsToPreferences(this, stats);


        //TODO cool machen
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    //do things
                });
        AlertDialog alert = builder.create();
        alert.show();
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
        if (selectedPinColor != null && celli.getxPos() == gamei.getCurrenRound()) {
            this.selectedBoardCell.setPinColor(selectedPinColor);
            this.selectedBoardCell.displayUnselected(this);
        } else {
            //TODO fehlermeldung?
        }
    }
}
