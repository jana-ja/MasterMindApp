package com.example.mastermind.model;

public class ReverseGame extends Game{

    public ReverseGame(Settings settings) {
        super(settings);
        // TODO get solution from user input
    }

    public void setSolution(PinColor[] solution){
        this.solution = solution;
    }

    @Override
    public PinRow nextRound(PinColor[] pinColors) {
        PinRow ergebi = new PinRow();

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
