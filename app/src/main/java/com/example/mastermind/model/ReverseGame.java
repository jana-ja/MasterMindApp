package com.example.mastermind.model;

import com.example.mastermind.view.BoardCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseGame extends Game{

    public ReverseGame(Settings settings) {
        super(settings);
        // TODO get solution from user input
    }

    public void setSolution(PinColor[] solution){
        this.solution = solution;
    }

    @Override
    public Ergebnis nextRound(PinColor[] pinColors) {
        Ergebnis ergebi = new Ergebnis();

        //checken ob alle plätze entsprechend der regeln besetzt sind
        //TODO
        ergebi.setOkay(true);
        //checken wie viele farben richtig
        ergebi.setCorrectColors(checkColors(pinColors));
        //checken wie viele plätze richtig
        ergebi.setCorrectPlaces(checkPlaces(pinColors));

        if(ergebi.isOkay())
            currenRound++;

        return ergebi;
    }


}
