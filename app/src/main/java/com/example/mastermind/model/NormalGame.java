package com.example.mastermind.model;

import com.example.mastermind.view.BoardCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NormalGame extends Game{

    public NormalGame(Settings settings){
        super(settings);
        this.solution = chooseSolution();
    }


    private PinColor[] chooseSolution() {
        //doppelt: ohne zurücklegen
        boolean zuruecklegen = settings.isDuplicatePins();
        //empty: aus 9 farben wählen
        int bound;
        if(settings.isEmptyPins())
            bound = 9;
        else
            bound = 8;


        Random rand = new Random();
        //ich brauche 4 int 0-bound

        PinColor[] solution = new PinColor[4];
        List<Integer> dings = new ArrayList<>();
        while(dings.size() < 4){
            int temp = rand.nextInt(bound);
            if(zuruecklegen) {
                //einfach nehmen
                dings.add(temp);
            } else {
                //schauen ob die zahl schon drin ist, keine duplikate
                if(!dings.contains(temp)){
                    dings.add(temp);
                }
            }
        }
        Iterator<Integer> iti = dings.iterator();
        for (int i = 0; i < solution.length; i++) {
            solution[i] = PinColor.values()[iti.next()];
        }
        return solution;
    }

    @Override
    public Ergebnis nextRound(PinColor[] pinColors){
        Ergebnis ergebi = new Ergebnis();

        //checken ob alle plätze entsprechend der regeln besetzt sind
        //TODO
        ergebi.setOkay(checkSettingConformity(pinColors));
        //checken wie viele farben richtig
        ergebi.setCorrectColors(checkColors(pinColors));
        //checken wie viele plätze richtig
        ergebi.setCorrectPlaces(checkPlaces(pinColors));

        if(ergebi.isOkay())
            currenRound++;

        return ergebi;
    }


}
