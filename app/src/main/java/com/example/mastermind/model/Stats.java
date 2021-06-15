package com.example.mastermind.model;

import android.content.Context;

import com.example.mastermind.R;

import java.util.ArrayList;
import java.util.List;

public class Stats {

    private int numberWon;
    private int numberLost;
    private int numberQuit;


    //TODO infos zu den einstellung (schwierigkeit) speichern?

    //TODO das hier richtig machen

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

    public static List<String> getAllStatsKeys(Context context){
        ArrayList<String> allKeys = new ArrayList<>();

        allKeys.add(context.getString(R.string.stats_started_key));
        allKeys.add(context.getString(R.string.stats_won_key));
        allKeys.add(context.getString(R.string.stats_lost_key));

        return allKeys;

    }
}
