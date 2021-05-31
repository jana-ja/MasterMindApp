package com.example.mastermind.model;

public class Ergebnis {

    private boolean okay;
    private int correctColors;
    private int correctPlaces;

    public Ergebnis() {
    }

    public boolean isOkay() {
        return okay;
    }

    public int getCorrectColors() {
        return correctColors;
    }

    public int getCorrectPlaces() {
        return correctPlaces;
    }

    public void setOkay(boolean okay) {
        this.okay = okay;
    }

    public void setCorrectColors(int correctColors) {
        this.correctColors = correctColors;
    }

    public void setCorrectPlaces(int correctPlaces) {
        this.correctPlaces = correctPlaces;
    }
}
