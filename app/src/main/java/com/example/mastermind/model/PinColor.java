package com.example.mastermind.model;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.mastermind.R;

public enum PinColor {
    RED, ORANGE, YELLOW, GREEN, BLUE, PINK, GREY, WHITE, EMPTY, SOLUTION;


    public int getImageName(){
        switch(this){
            case RED:
                return R.drawable.merkur;
            case ORANGE:
                return R.drawable.venus;
            case YELLOW:
                return R.drawable.erde;
            case GREEN:
                return R.drawable.mars;
            case BLUE:
                return R.drawable.jupiter;
            case PINK:
                return R.drawable.saturn;
            case GREY:
                return R.drawable.uranus;
            case WHITE:
                return R.drawable.neptun;
            case EMPTY:
                return R.drawable.empty;
            case SOLUTION:
                return R.drawable.black_hole;
            default:
                return R.drawable.empty;
        }
    }

    public int getStringId(){
        switch(this) {
            case RED:
                return R.string.merkur;
            case ORANGE:
                return R.string.venus;
            case YELLOW:
                return R.string.erde;
            case GREEN:
                return R.string.mars;
            case BLUE:
                return R.string.jupiter;
            case PINK:
                return R.string.saturn;
            case GREY:
                return R.string.uranus;
            case WHITE:
                return R.string.neptun;
            default:
                return 0;
        }
    }
}
