package com.example.mastermind.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.preference.PreferenceManager;

import com.example.mastermind.R;
import com.example.mastermind.model.Game;
import com.example.mastermind.model.PinColor;
import com.example.mastermind.model.Settings;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class GameActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    Game gamei;

    ConstraintLayout gameBackground;

    GridLayout griddiSolution;
    GridLayout griddiBoard;
    GridLayout griddiPins;
    Button bStartGame;
    Button bNextRound;

    List<BoardCell> solutionCells;
    BoardCell[][] boardCells;
    Indicator[][] indicators;
    List<BoardCell> pinCells;

    BoardCell selectedBoardCell;
    PinColor selectedPinColor;

    private boolean gameRunning = false;

    protected Settings settings;

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
                        .setNegativeButton(R.string.text_alert_negative, (dialog, which) -> {

                        }).show();
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


    public void notOkayErrorMessage(){
        String text = "Die Eingabe entspricht nicht den Einstellungen.";

        final Snackbar snackBar = Snackbar.make(gameBackground, text, Snackbar.LENGTH_SHORT);
        snackBar.setAction("ok", v -> {
            snackBar.dismiss();
        });
        snackBar.show();

//        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
//        toast.show();
    }

    abstract void nextRound();

    // need to override in subclass
    void endGame(boolean won){
        gameRunning = false;
        bNextRound.setEnabled(false);
    }

    void startGame() {
        gameRunning = true;

        if(firstRound) {
            init();
            initView();
            firstRound = false;
        }
        resetView();
    }

    void initView() {
        //hier die bidlschrimgröße ansehen, maße der indikatoren und boardcells berechnen, den dingern ihre größen geben


        int gesamtHeight = griddiBoard.getHeight() + griddiPins.getHeight() + griddiSolution.getHeight();

        //erst die pin palette, weil da am meisten bekannt ist:
        //höhe irrelevant, weil die höhe dieses grids zuerst festgelegt wird und weils halt so ist kB mehr
        int pinPaletteWidth = this.griddiPins.getWidth() - 2 * MARGIN_HORI;
        int widthPerPin = pinPaletteWidth / griddiPins.getColumnCount();
        int pinGridHeight = widthPerPin + 2 * MARGIN_VERT;
        //größe der boardcells in griddiPins
        for (BoardCell pinCell : this.pinCells) {
            pinCell.setLayoutParams(widthPerPin, widthPerPin);
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
            solutionCell.setLayoutParams(minCellSize, minCellSize);
        }
        //TODO muss ich nicht auch für die indikatoren setzen die platzhalter im solution grid sind??

        //für alle boardcells und indikatoren setzen
        for (BoardCell[] boardCellRow : this.boardCells) {
            for (BoardCell boardCell : boardCellRow) {
                boardCell.setLayoutParams(minCellSize, minCellSize);
            }
        }
        for (Indicator[] indicatorRow : this.indicators) {
            for (Indicator indicator : indicatorRow) {
                indicator.setLayoutParams(indicatorWidth, minCellSize);
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

    // when a new game is started
    void resetView() {
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
//        for (Indikator[] indicatorRow : indicators) {
//            for (Indikator indikator : indicatorRow) {
//                indikator.setNumber(""); //TODO
//            }
//        }
        //pin palette
        this.pinCells.forEach(cell ->
                cell.displayUnselected(this));

    }

    void highlightCurrentRow() {
        int row = gamei.getCurrenRound();
        indicators[row][0].highlight();
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
                Indicator indi = new Indicator(this, 0, j);
                griddiSolution.addView(indi);
            } else {
                BoardCell boardCell = new BoardCell(this, 0, j);
                boardCell.setPinColor(PinColor.EMPTY);
                boardCell.setOnClickListener(this::onClickSolutionCell);

                griddiSolution.addView(boardCell);
                solutionCells.add(boardCell);
            }
        }


        //Game Board
        int numberRows = settings.getNumberRounds();
        griddiBoard.setRowCount(numberRows);
        griddiBoard.setColumnCount(6);
        boardCells = new BoardCell[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 2];
        indicators = new Indicator[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 4];

        for (int i = griddiBoard.getRowCount() - 1; i >= 0; i--) {
            //gibt 10 rows
            for (int j = 0; j < griddiBoard.getColumnCount(); j++) {
                //gibt 6 columns
                if (j == 0 || j == 5) {
                    //nach links und rechts müssen text für die indikatoren
                    Indicator indi = new Indicator(this, i, j);
                    //%4 für indizierung des indikator arrays
                    indicators[i][j % 4] = indi;

                    griddiBoard.addView(indi);
                } else {
                    //über all anders in die mitte die board cells
                    BoardCell boardCell = new BoardCell(this, i, j);
                    boardCell.setOnClickListener(this::onClickBoardCell);
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

    protected abstract void onClickSolutionCell(View v);

    protected abstract void onClickBoardCell(View v);

    void onclickSolutionCell(){

    }

    // für dialog wenn man neues spiel macht obwohl grade eins läuft
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
