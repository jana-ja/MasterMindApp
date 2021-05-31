package com.example.mastermind.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.Ergebnis;
import com.example.mastermind.model.Game;
import com.example.mastermind.model.PinColor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameActivity extends AppCompatActivity {

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
            startGame();
        });
        bNextRound = findViewById(R.id.button_next_round);
        bNextRound.setOnClickListener(v ->
                nextRound());

        init();

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
            }
        } else {
            //TODO fehlermeldung?
        }
    }

    private void endGame(boolean won) {
        bNextRound.setEnabled(false);

        PinColor[] solutionPinColors = gamei.getSolution();
        for (int i = 0; i < solutionCells.size(); i++) {
            solutionCells.get(i).setPinColor(solutionPinColors[i]);
            solutionCells.get(i).displayColor();
        }

        //TODO cool machen
        String dialogText;
        if(won)
            dialogText = "GEWONNEN";
        else
            dialogText = "VERLOREN";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startGame() {
        resetView();

        for (BoardCell[] boardCellRow : boardCells) {
            for (BoardCell boardCell : boardCellRow) {
                boardCell.displayColor();
            }
        }
        for (BoardCell celli : pinCells) {
            celli.displayColor();
        }

        gamei = new Game();

        solutionCells.forEach(cell -> {
            cell.setPinColor(PinColor.EMPTY);
            cell.displayColor();
//            cell.setBackground(getDrawable(R.drawable.clipart1853715));
//            cell.setPadding(20,20,20,20);
//            cell.setScaleType(ImageView.ScaleType.CENTER);
//            cell.setAdjustViewBounds(true);
        });

        //        PinColor[] solutionPinColors = gamei.getSolution();
//        for (int i = 0; i < solutionCells.size(); i++) {
//            solutionCells.get(i).setPinColor(solutionPinColors[i]);
//            // die solution displayen für testen
//            // TODO später dann iwie invisible oder verdeckt machen, aber schon in die dinger einspeichern
//            solutionCells.get(i).displayColor();
//        }

    }

    private void resetView() {
        //next round button
        this.bNextRound.setEnabled(true);

        //solution griddi
        this.solutionCells.forEach(celli -> {celli.setPinColor(PinColor.EMPTY); celli.displayColor();});
        //board cell griddi
        for (BoardCell[] boardCellRow : boardCells) {
            for (BoardCell boardCell : boardCellRow) {
                boardCell.setPinColor(PinColor.EMPTY);
                boardCell.displayColor();
            }
        }
        //indicators
        for (Indikator[] indicatorRow : indicators) {
            for (Indikator indikator : indicatorRow) {
                indikator.setText("");
            }
        }
    }

    private void init() {

        //Solution Cells
        griddiSolution.setUseDefaultMargins(true);
        griddiSolution.setRowCount(1);
        griddiSolution.setColumnCount(4);
        solutionCells = new ArrayList<>();

        for (int j = 0; j < griddiSolution.getColumnCount(); j++) {
            BoardCell boardCell = new BoardCell(this, 0, j);
            boardCell.setPinColor(PinColor.EMPTY);

            griddiSolution.addView(boardCell);
            solutionCells.add(boardCell);
        }


        //Game Board
        griddiBoard.setUseDefaultMargins(true);
        griddiBoard.setRowCount(10);
        griddiBoard.setColumnCount(6);
        boardCells = new BoardCell[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 2];
        indicators = new Indikator[griddiBoard.getRowCount()][griddiBoard.getColumnCount() - 4];

        for (int i = 0; i < griddiBoard.getRowCount(); i++) {
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
                            this.selectedBoardCell.displayColor();
                        } else {
                            //TODO fehlermeldung?
                        }
                    });
                    //j-1 wegen indizes vom array
                    boardCells[i][j - 1] = boardCell;

                    griddiBoard.addView(boardCell);

                    //TODO alles auf leeres bild setzen hier oder im konstruktor von board cell  oder wo auch immer das geht mit den bildern
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
            boardCell.setOnClickListener(v -> {
                BoardCell celli = (BoardCell) v;
                this.selectedPinColor = celli.pinColor;
                //TODO iwie highlighten?
            });
            griddiPins.addView(boardCell);
            pinCells.add(boardCell);
        }

    }

}
