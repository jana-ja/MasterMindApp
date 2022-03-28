package com.example.mastermind.view;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.mastermind.R;
import com.example.mastermind.model.PinRow;
import com.example.mastermind.model.GameAI;
import com.example.mastermind.model.PinColor;
import com.example.mastermind.model.ReverseGame;

import java.util.Locale;


public class ReverseGameActivity extends GameActivity {

    boolean userSolutionReady;
    GameAI ai;
    PinRow lastErgebi;

    public void makeToast(PinColor color, boolean positive){
        String bla = positive? "" : "nicht ";
        String text = color.toString().toLowerCase(Locale.ROOT) + " ist " + bla + "in der Lösung";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    void startGame() {
        //TODO später auch andere spiel modi implementieren?
        settings.setDuplicatePins(false);
        settings.setEmptyPins(false);

        gamei = new ReverseGame(settings);

        super.startGame();

        userSolutionReady = false;
        ai = new GameAI(settings);
        ai.setGameActivity(this);
    }

    @Override
    void nextRound() {
        PinColor[] step;
        // first round: init solution from user input
        if(!userSolutionReady) {
            // get solution from user input and check if no setting rule is verletzt
            PinColor[] pinColors = new PinColor[solutionCells.size()];
            for (int i = 0; i < pinColors.length; i++) {
                pinColors[i] = solutionCells.get(i).getPinColor();
            }
            if (!gamei.checkSettingConformity(pinColors)) {
                this.notOkayErrorMessage();
                return;
            }
            //set solution of gamei
            ((ReverseGame) gamei).setSolution(new PinColor[]{solutionCells.get(0).getPinColor(), solutionCells.get(1).getPinColor(), solutionCells.get(2).getPinColor(), solutionCells.get(3).getPinColor(),});

            userSolutionReady = true;

            //TODO vllt pin palette deaktivieren?

            step = ai.firstStep();
        } else {
            // user input is set as solution and is okay with settings

            step = ai.nextStep(lastErgebi);
        }
        //display step
        int currentRound = gamei.getCurrenRound();
        PinRow ergebi = gamei.nextRound(step);
        lastErgebi = ergebi;
        if (ergebi.isOkay()) { //TODO brauch ich hier?
            //display step
            for (int i = 0; i < boardCells[currentRound].length; i++) {
                boardCells[currentRound][i].setPinColor(step[i]);
                boardCells[currentRound][i].displayUnselected(this);
            }



            //die zahlen wiedergeben
//            //richtige stellen
//            indicators[currentRound][0].setTextColor(Color.RED);
//            indicators[currentRound][0].setText(String.valueOf(ergebi.getCorrectPlaces()));
            //richtige farben
            indicators[currentRound][1].setTextColor(Color.BLACK);
            indicators[currentRound][1].setText(String.valueOf(ergebi.getCorrectColors()));



            if (ergebi.getCorrectColors() == 4 || gamei.getCurrenRound() > settings.getNumberRounds() - 1) {

                //wenn daws true ist hat man gewonnen, sonst muss >9 true sein und dann hat man verloren
                endGame(ergebi.getCorrectColors() == 4);

            }
        }

    }

    @Override
    void endGame(boolean won) {
        super.endGame(won);
        //TODO
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("lel")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    //do things
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    void initView() {
        // swap position of boardCells and solutionCells
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(gameBackground);
        //boardCells nach oben
        constraintSet.connect(R.id.board_cells, ConstraintSet.TOP, gameBackground.getId(), ConstraintSet.TOP);
        constraintSet.connect(R.id.board_cells, ConstraintSet.BOTTOM, R.id.solution_cells, ConstraintSet.TOP);
        //solutionCells darunter in die mitte
        constraintSet.connect(R.id.solution_cells, ConstraintSet.TOP, R.id.board_cells, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.solution_cells, ConstraintSet.BOTTOM, R.id.pincolor_palette, ConstraintSet.TOP);
        // pin dinger an solution cells machen
        constraintSet.connect(R.id.pincolor_palette, ConstraintSet.TOP, R.id.solution_cells, ConstraintSet.BOTTOM);
        constraintSet.applyTo(gameBackground);
        super.initView();
    }

    @Override
    protected void onClickSolutionCell(View v) {
        this.selectedBoardCell = (BoardCell) v;
        if (selectedPinColor != null && !userSolutionReady) {
            this.selectedBoardCell.setPinColor(selectedPinColor);
            this.selectedBoardCell.displayUnselected(this);
        } else {
            //TODO fehlermeldung?
        }
    }

    @Override
    protected void onClickBoardCell(View v) {
        //macht hier nichts
    }
}
