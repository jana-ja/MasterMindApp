package com.example.mastermind.model;

public class Stats {

    private int numberWon;
    private int numberLost;
    private int numberQuit;

    //TODO infos zu den einstellung (schwierigkeit) speichern?


    public Stats() {
    }

    public Stats(int numberWon, int numberLost, int numberQuit) {
        this.numberWon = numberWon;
        this.numberLost = numberLost;
        this.numberQuit = numberQuit;
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

    public int getNumberQuit() {
        return numberQuit;
    }

    public void setNumberQuit(int numberQuit) {
        this.numberQuit = numberQuit;
    }
}
