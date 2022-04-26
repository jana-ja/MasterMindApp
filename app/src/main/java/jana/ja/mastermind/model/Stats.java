package jana.ja.mastermind.model;

import jana.ja.mastermind.R;

public class Stats {

    private int numberStarted;
    private int numberWon;
    private int numberLost;
    private int avgRoundsPerWin;

    private long shortestTime;
    private long longestTime;
    private long avgTime;


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

    public long getShortestTime() {
        return shortestTime;
    }

    public void setShortestTime(long shortestTime) {
        this.shortestTime = shortestTime;
    }

    public long getLongestTime() {
        return longestTime;
    }

    public void setLongestTime(long longestTime) {
        this.longestTime = longestTime;
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(long avgTime) {
        this.avgTime = avgTime;
    }
}
