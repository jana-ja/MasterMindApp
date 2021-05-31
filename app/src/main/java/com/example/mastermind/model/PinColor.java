package com.example.mastermind.model;

import android.graphics.Color;

public enum PinColor {
    RED, ORANGE, YELLOW, GREEN, BLUE, PINK, GREY, WHITE, EMPTY;

    public int getColor(){
        switch(this){
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case ORANGE:
                return Color.rgb(255,165,0);
            case PINK:
                return Color.rgb(255,105,180);
            case RED:
                return Color.RED;
            case WHITE:
                return Color.WHITE;
            case GREY:
                return Color.GRAY;
            case YELLOW:
                return Color.YELLOW;
            case EMPTY:
                return Color.BLACK;
            default:
                return Color.TRANSPARENT;
        }
    }
}
