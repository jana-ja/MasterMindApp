package com.example.mastermind.model;

import com.example.mastermind.view.BoardCell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {

    private int currenRound;
    private PinColor[] solution;

    public Game(){
        this.currenRound = 0;
        this.solution = chooseSolution();
    }

    public int getCurrenRound() {
        return currenRound;
    }

    public PinColor[] getSolution() {
        return solution;
    }

    private PinColor[] chooseSolution() {
        //erstmal wählen ohne zurücklegen und ohne empty
        //TODO später nach regeln
        Random rand = new Random();
        //ich brauche 4 int 0-7

        PinColor[] solution = new PinColor[4];
        Set<Integer> dings = new HashSet<>();
        while(dings.size() < 4){
            int temp = rand.nextInt(7);
            dings.add(temp);
                //TODO test funktioniert so mit keine duplikate? oder sind verschiedene interger objekte mit gleichem wert?
        }
        Iterator<Integer> iti = dings.iterator();
        for (int i = 0; i < solution.length; i++) {
            solution[i] = PinColor.values()[iti.next()];
        }
        return solution;
    }


    public Ergebnis nextRound(BoardCell[] boardCellRow){
        Ergebnis ergebi = new Ergebnis();

        //checken ob alle plätze entsprechend der regeln besetzt sind
        ergebi.setOkay(checkOkay(boardCellRow));
        //checken wie viele farben richtig
        ergebi.setCorrectColors(checkColors(boardCellRow));
        //checken wie viele plätze richtig
        ergebi.setCorrectPlaces(checkPlaces(boardCellRow));

        if(ergebi.isOkay())
            currenRound++;

        return ergebi;
    }


    private boolean checkOkay(BoardCell[] boardCellRow) {
        for (BoardCell boardCell : boardCellRow) {
            if (boardCell.getPinColor()==PinColor.EMPTY)
                return false;
        }
        return true;
    }

    private int checkColors(BoardCell[] boardCellRow) {
        int dingsi = 0;
        //boardcellrow kopieren
        List<PinColor> editRow = new ArrayList<>();
        for (BoardCell boardCell : boardCellRow) {
            editRow.add(boardCell.getPinColor());
        }

        //alle solution colors abgleichen mit der aktuellen row
        for (PinColor pinColor : solution) {
            for (PinColor color : editRow) {
                //wenn die farbe gleich ist lösch ich aus der aktuellen row damit die nciht doppelt gezählt werden falls in der solution was doppelt ist
                if(pinColor == color){
                    editRow.remove(color);
                    dingsi++;
                    break;
                }
            }
        }
        return dingsi;
    }

    private int checkPlaces(BoardCell[] boardCellRow) {
        int dingsi = 0;
        for (int i = 0; i < boardCellRow.length; i++) {
            if(boardCellRow[i].getPinColor() == solution[i])
                dingsi++;
        }
        return dingsi;
    }


}
