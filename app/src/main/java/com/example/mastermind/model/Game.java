package com.example.mastermind.model;

import com.example.mastermind.view.BoardCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

abstract public class Game {

    protected int currenRound;
    protected PinColor[] solution; //TODO war final, vllt was kluges überlegen mit der vererbung

    protected Settings settings;

    public Game(Settings settings){
        this.settings = settings;
        this.currenRound = 0;
    }

    public boolean checkSettingConformity(PinColor[] pinColors) {
        // TODO
//        for (BoardCell boardCell : boardCellRow) {
//            if (boardCell.getPinColor()==PinColor.EMPTY)
//                return false;
//        }
        return true;
    }

    public int getCurrenRound() {
        return currenRound;
    }

    public PinColor[] getSolution() {
        return solution;
    }


    abstract public Ergebnis nextRound(PinColor[] pinColors);

    int checkColors(PinColor[] pinColors) {
        int dingsi = 0;
        //boardcellrow kopieren
        List<PinColor> editRow = new ArrayList<>(Arrays.asList(pinColors));

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

    int checkPlaces(PinColor[] pinColors) {
        int dingsi = 0;
        for (int i = 0; i < pinColors.length; i++) {
            if(pinColors[i] == solution[i])
                dingsi++;
        }
        return dingsi;
    }



}
