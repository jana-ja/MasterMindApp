package com.example.mastermind.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.Ergebnis;
import com.example.mastermind.model.Game;
import com.example.mastermind.model.PinColor;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    Game gamei;

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

    final int PIN_NUMBER = 8; //TODO durch spielsettings anpassen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        griddiSolution = findViewById(R.id.solution_cells);
        griddiBoard = findViewById(R.id.board_cells);

        griddiPins = findViewById(R.id.pincolor_palette);

        bStartGame = findViewById(R.id.button_start_game);
        bStartGame.setOnClickListener(v -> {
            if (gameRunning) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);//TODO hard coded strings
                builder.setMessage("Wirklich das aktuelle Spiel verwerfen und ein neues starten?").setPositiveButton("Ja", this)
                        .setNegativeButton("Nein", this).show();
            } else {
                startGame();
            }
        });
        bNextRound = findViewById(R.id.button_next_round);
        bNextRound.setOnClickListener(v ->
                nextRound());

        init();
        startGame();

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
            indicators[currentRound][1].setText(String.valueOf(ergebi.getCorrectColors()));

            if (ergebi.getCorrectPlaces() == 4 || gamei.getCurrenRound() > 9) {
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

        //TODO cool machen
        String dialogText;
        if (won)
            dialogText = "GEWONNEN";
        else
            dialogText = "VERLOREN";

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
        gameRunning = true;
        gamei = new Game();
        resetView();
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
                indikator.setText("0"); //TODO
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

    private void init() {

        //Solution Cells
        griddiSolution.setUseDefaultMargins(true);
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
        griddiBoard.setUseDefaultMargins(true);
        griddiBoard.setRowCount(10);
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
        pinCells = new ArrayList<>();
        griddiPins.setUseDefaultMargins(true);
        griddiPins.setForegroundGravity(1);
        griddiPins.setColumnCount(PIN_NUMBER);
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
