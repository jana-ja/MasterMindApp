package com.example.mastermind.model;

import android.content.Context;

import com.example.mastermind.R;

import java.util.ArrayList;
import java.util.List;

public class Stats {

    private int numberStarted;
    private int numberWon;
    private int numberLost;
    private int avgRoundsPerWin;


    //TODO infos zu den einstellung (schwierigkeit) speichern?

    public Stats() {
    }

    public int getNumberStarted() {
        return numberStarted;
    }

    public void setNumberStarted(int numberStarted) {
        this.numberStarted = numberStarted;
    }

    public int getNumberWon() {
        return numberWon;
    }

    public void setNumberWon(int numberWon) {
        this.numberWon = numberWon;
    }

    public int getNumberLost() {
        return numberLost;
    }

    public void setNumberLost(int numberLost) {
        this.numberLost = numberLost;
    }

    public int getAvgRoundsPerWin() {
        return avgRoundsPerWin;
    }

    public void setAvgRoundsPerWin(int avgRoundsPerWin) {
        this.avgRoundsPerWin = avgRoundsPerWin;
    }
}
