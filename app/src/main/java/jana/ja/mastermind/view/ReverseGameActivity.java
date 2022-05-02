package jana.ja.mastermind.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.analytics.FirebaseAnalytics;

import jana.ja.mastermind.R;
import jana.ja.mastermind.model.PinRow;
import jana.ja.mastermind.model.GameAI;
import jana.ja.mastermind.model.PinColor;
import jana.ja.mastermind.model.ReverseGame;


public class ReverseGameActivity extends GameActivity {

    boolean userSolutionReady;
    GameAI ai;
    PinRow lastErgebi;

    public void makeToast(PinColor color, boolean positive){
        String bla = positive? getString(R.string.is_in_solution) : getString(R.string.is_not_in_solution);
        String text =
                getString(color.getStringId()) + " " + bla;
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

        // analytics
        Bundle bundle = new Bundle();
        bundle.putString("game_mode", "ai");
//        bundle.putBoolean("settings_duplicates", settings.isDuplicatePins());
//        bundle.putBoolean("settings_pluto", settings.isEmptyPins());
//        bundle.putInt("settings_rounds", settings.getNumberRounds());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);
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
                // analytics
                Bundle bundle = new Bundle();
                bundle.putString("game_mode", "ai");
                mFirebaseAnalytics.logEvent("solution_input_error", bundle);
                return;
            }
            //set solution of gamei
            ((ReverseGame) gamei).setSolution(new PinColor[]{solutionCells.get(0).getPinColor(), solutionCells.get(1).getPinColor(), solutionCells.get(2).getPinColor(), solutionCells.get(3).getPinColor(),});

            userSolutionReady = true;

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
                boardCells[currentRound][i].display();
            }



            //die zahlen wiedergeben
//            //richtige stellen
//            indicators[currentRound][0].setTextColor(Color.RED);
//            indicators[currentRound][0].setText(String.valueOf(ergebi.getCorrectPlaces()));
            //richtige farben
            indicators[currentRound][1].setNumber(ergebi.getCorrectColors(), true);



            if (ergebi.getCorrectColors() == 4 || gamei.getCurrenRound() > settings.getNumberRounds() - 1) {

                //wenn daws true ist hat man gewonnen, sonst muss >9 true sein und dann hat man verloren
                endGame(ergebi.getCorrectColors() == 4);

            }
        }
        // analytics
        Bundle bundle = new Bundle();
        bundle.putString("game_mode", "ai");
//        bundle.putBoolean("settings_duplicates", settings.isDuplicatePins());
//        bundle.putBoolean("settings_pluto", settings.isEmptyPins());
//        bundle.putInt("settings_rounds", settings.getNumberRounds());
        bundle.putInt("rounds", gamei.getCurrenRound());
        mFirebaseAnalytics.logEvent("next_round", bundle);

    }

    @Override
    void endGame(boolean won) {
        super.endGame(won);

        ReverseGameEndDialog dialog = new ReverseGameEndDialog(won);
        dialog.show(getSupportFragmentManager(), "game_end_alert");

        // analytics
        Bundle bundle = new Bundle();
        bundle.putString("game_mode", "ai");
        bundle.putBoolean("won", won);
        bundle.putInt("rounds", gamei.getCurrenRound());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END, bundle);

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
        // only do something when a pin color is selected and the board cell is in the currently active row
        if (selectedPinColor != null && !userSolutionReady) {
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
    protected void onClickBoardCell(View v) {
        //macht hier nichts
    }
}
