package com.example.mastermind.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.preference.PreferenceManager;

import com.example.mastermind.R;
import com.example.mastermind.model.Ergebnis;
import com.example.mastermind.model.Game;
import com.example.mastermind.model.PinColor;
import com.example.mastermind.model.Settings;
import com.example.mastermind.model.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    Game gamei;

    ConstraintLayout gameBackground;

    GridLayout griddiSolution;
    GridLayout griddiBoard;
    GridLayout griddiPins;
    Button bStartGame;
    Button bNextRound;

    List<BoardCell> solutionCells;
    BoardCell[][] boardCells;
    Indikator[][] indicators;
    List<BoardCell> pinCells;

    BoardCell selectedBoardCell;
    PinColor selectedPinColor;

    private boolean gameRunning = false;

    private Settings settings;

    private boolean firstRound;

    private final int MARGIN_VERT = 10;
    private final int MARGIN_HORI = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO großer spiel starten knopf am anfang
        //TODO trenn linien zw den dingern
        //TODO fragezeichen auf die dinger
        //TODO trennung brett und knöpfe
        //TODO alert design
        //TODO zeilenumbruch stats?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameBackground = findViewById(R.id.game_background);

        griddiSolution = findViewById(R.id.solution_cells);
        griddiBoard = findViewById(R.id.board_cells);

        griddiPins = findViewById(R.id.pincolor_palette);

        bStartGame = findViewById(R.id.button_start_game);
        bStartGame.setOnClickListener(v -> {
            if (gameRunning) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.alert_text_start_game)).setPositiveButton(R.string.text_alert_positive, this)
                        .setNegativeButton(R.string.text_alert_negative, this).show();
            } else {
                startGame();
            }
        });
        bNextRound = findViewById(R.id.button_next_round);
        bNextRound.setOnClickListener(v ->
                nextRound());
        bNextRound.setEnabled(false);


        firstRound = true;
        loadSettings();
    }

    private void loadSettings() {
        settings = new Settings();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        settings.setDuplicatePins(preferences.getBoolean("duplicate_pins",false));
        settings.setEmptyPins(preferences.getBoolean("empty_pins", false));
        settings.setNumberRounds(preferences.getInt("round_number",10));

    }


    private void nextRound() {
        //dem game zum überprüfen die aktuelle reihe übergeben
        int currentRound = gamei.getCurrenRound();
        Ergebnis ergebi = gamei.nextRound(boardCells[currentRound]);
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
            //TODO fehlermeldung?
        }
    }

    private void endGame(boolean won) {
        gameRunning = false;

        bNextRound.setEnabled(false);

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

    private void startGame() {
        //add to stats
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.stats_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int numberStarted = sharedPref.getInt(getString(R.string.stats_started_key), 0);
        editor.putInt(getString(R.string.stats_started_key), numberStarted + 1);
        editor.apply();

        gameRunning = true;
        gamei = new Game(settings);
        if(firstRound) {
            init();
            initView();
            firstRound = false;
        }
        resetView();
    }

    private void initView() {
        //hier die bidlschrimgröße ansehen, maße der indikatoren und boardcells berechnen, den dingern ihre größen geben


        int gesamtHeight = griddiBoard.getHeight() + griddiPins.getHeight() + griddiSolution.getHeight();

        //erst die pin palette, weil da am meisten bekannt ist:
        //höhe irrelevant, weil die höhe dieses grids zuerst festgelegt wird und weils halt so ist kB mehr
        int pinPaletteWidth = this.griddiPins.getWidth() - 2 * MARGIN_HORI;
        int widthPerPin = pinPaletteWidth / griddiPins.getColumnCount();
        int pinGridHeight = widthPerPin + 2 * MARGIN_VERT;
        //größe der boardcells in griddiPins
        for (BoardCell pinCell : this.pinCells) {
            pinCell.setLayoutParams(widthPerPin);
        }
        //höhe von griddiPins daran anpassen
        setGridLayoutParams(griddiPins, pinGridHeight);


        //dann board und solution den platz zuweisen
        //höhe
        //solution + board = solution alt + board alt
        // = gesamt - pinGridHeight
         int remainingVertSpace = gesamtHeight - pinGridHeight;
         int effectiveSpace = remainingVertSpace - 4 * MARGIN_VERT;
         //das muss ich durch anzahl reihen + 1 teilen
        int heightPerCell = effectiveSpace / (settings.getNumberRounds() + 1);

        //weite
        int boardWidth = this.griddiBoard.getWidth() - 2 * MARGIN_HORI;
        int widthPerBoardCell = boardWidth / griddiBoard.getColumnCount();

        int solutionWidth = this.griddiSolution.getWidth() - 2 * MARGIN_HORI;
        int widthPerSolutionCell = solutionWidth / griddiSolution.getColumnCount(); //wird niemals kleiner als widthPerBoardCell sein -> wird niemals minCellSize sein


        //schauen was kleiner ist und gleiche size für width und height nehmen damit es ein kreis wird
        int[] dings = {widthPerBoardCell,heightPerCell,widthPerSolutionCell};
        Arrays.sort(dings);
        int minCellSize = dings[0];
        int indicatorWidth = minCellSize;

        if (minCellSize == widthPerBoardCell){
            //begrenzt durch breite des grids
            //TODO den indikatoren weniger platz geben um die länge mehr auszunutzen?
        } else if (minCellSize == heightPerCell){
            //begrenzt durch höhe des grids / länge

            //rest des space an die indikatoren geben
            //restlicher space in der weite:
            int remainingHoriSpace = boardWidth - minCellSize * 6;
            indicatorWidth += remainingHoriSpace / 2;
        }
    

        //für solutioncells setzen
        for (BoardCell solutionCell : solutionCells) {
            solutionCell.setLayoutParams(minCellSize);
        }
        //TODO muss ich nicht auch für die indikatoren setzen die platzhalter im solution grid sind??

        //für alle boardcells und indikatoren setzen
        for (BoardCell[] boardCellRow : this.boardCells) {
            for (BoardCell boardCell : boardCellRow) {
                boardCell.setLayoutParams(minCellSize);
            }
        }
        for (Indikator[] indicatorRow : this.indicators) {
            for (Indikator indikator : indicatorRow) {
                indikator.setLayoutParams(indicatorWidth, minCellSize);
            }
        }

        //den grids so viel size geben wie sie brauchen und dann zentrieren sie sich im constraing layout weil die gechained sind
        int griddiSolutionHeight = minCellSize + 2 * MARGIN_VERT;
        int griddiBoardHeight = minCellSize * settings.getNumberRounds() + 2 * MARGIN_VERT;
        setGridLayoutParams(griddiBoard, griddiBoardHeight);
        setGridLayoutParams(griddiSolution, griddiSolutionHeight);
    }

    private void setGridLayoutParams(GridLayout griddi, int height) {
        ConstraintLayout.LayoutParams griddiPinsParams = (ConstraintLayout.LayoutParams)griddi.getLayoutParams();
        griddiPinsParams.height = height - 2 * MARGIN_VERT;
        griddiPinsParams.setMargins(MARGIN_HORI, MARGIN_VERT, MARGIN_HORI, MARGIN_VERT);
        griddi.setLayoutParams(griddiPinsParams);
    }

    private void resetView() {
        //next round button
        this.bNextRound.setEnabled(true);

        //solution griddi
        this.solutionCells.forEach(celli -> {
            celli.setPinColor(PinColor.EMPTY);
            celli.displayUnselected(this);
        });

        //board cell griddi
        for (BoardCell[] boardCellRow : boardCells) {
            for (BoardCell boardCell : boardCellRow) {
//                boardCell.setMinimumHeight(boardCell.getWidth());
                boardCell.setPinColor(PinColor.EMPTY);
                boardCell.displayUnselected(this);
            }
        }
        //indicators
        for (Indikator[] indicatorRow : indicators) {
            for (Indikator indikator : indicatorRow) {
                indikator.setText(""); //TODO
            }
        }
        //pin palette
        this.pinCells.forEach(cell ->
                cell.displayUnselected(this));

        //show current row
        highlightCurrentRow();
    }

    private void highlightCurrentRow() {
        int row = gamei.getCurrenRound();
        indicators[row][0].setTextColor(Color.BLACK);
        indicators[row][0].setText("->");
    }

    /**
     * inititalisiert Grids und fügt die BoardCells und Indikatoren hinzu
     * speichert die benötigten Objekte in Variablen
     */
    private void init() {
        //Solution Cells
        griddiSolution.setRowCount(1);
        griddiSolution.setColumnCount(6);
        solutionCells = new ArrayList<>();

        for (int j = 0; j < griddiSolution.getColumnCount(); j++) {
            if (j == 0 || j == 5) {
                //dummy damit so breit ist wie board dadrunter
                Indikator indi = new Indikator(this, 0, j);
                griddiSolution.addView(indi);
            } else {
                BoardCell boardCell = new BoardCell(this, 0, j);
                boardCell.setPinColor(PinColor.EMPTY);

                griddiSolution.addView(boardCell);
                solutionCells.add(boardCell);
            }
        }


        //Game Board
        int numberRows = settings.getNumberRounds();
        griddiBoard.setRowCount(numberRows);
        griddiBoard.setColumnCount(6);
        boardCells = new BoardCell[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 2];
        indicators = new Indikator[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 4];

        for (int i = griddiBoard.getRowCount() - 1; i >= 0; i--) {
            //gibt 10 rows
            for (int j = 0; j < griddiBoard.getColumnCount(); j++) {
                //gibt 6 columns
                if (j == 0 || j == 5) {
                    //nach links und rechts müssen text für die indikatoren
                    Indikator indi = new Indikator(this, i, j);
                    //%4 für indizierung des indikator arrays
                    indicators[i][j % 4] = indi;

                    griddiBoard.addView(indi);
                } else {
                    //über all anders in die mitte die board cells
                    BoardCell boardCell = new BoardCell(this, i, j);

                    boardCell.setOnClickListener(v -> {
                        BoardCell celli = (BoardCell) v;
                        this.selectedBoardCell = celli;
                        if (selectedPinColor != null && celli.getxPos() == gamei.getCurrenRound()) {
                            this.selectedBoardCell.setPinColor(selectedPinColor);
                            this.selectedBoardCell.displayUnselected(this);
                        } else {
                            //TODO fehlermeldung?
                        }
                    });
                    //j-1 wegen indizes vom array
                    boardCells[i][j - 1] = boardCell;
                    griddiBoard.addView(boardCell);
                }
            }
        }


        //PinColors
        int pinNumber;
        if(settings.isEmptyPins())
            pinNumber = 9;
        else
            pinNumber = 8;
        pinCells = new ArrayList<>();
        griddiPins.setForegroundGravity(1);
        griddiPins.setColumnCount(pinNumber);
        griddiPins.setRowCount(2);

        for (int j = 0; j < griddiPins.getColumnCount(); j++) {
            BoardCell boardCell = new BoardCell(this, 0, j);
            boardCell.setPinColor(PinColor.values()[j]);
            boardCell.displayUnselected(this);
            boardCell.setOnClickListener(v -> {
                BoardCell celli = (BoardCell) v;
                this.selectedPinColor = celli.pinColor;
                //die anderen unselected machen
                this.pinCells.forEach(cell ->
                        cell.displayUnselected(this));
                //dieses selected machen
                celli.displaySelected(this);
            });
            griddiPins.addView(boardCell);
            pinCells.add(boardCell);
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                startGame();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }
    }
}
