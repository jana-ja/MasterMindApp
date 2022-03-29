package com.example.mastermind.model;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.mastermind.R;

public enum PinColor {
    RED, ORANGE, YELLOW, GREEN, BLUE, PINK, GREY, WHITE, EMPTY, SOLUTION;

    public int getColor(Context context){
        switch(this){
            case BLUE:
                return ContextCompat.getColor(context, R.color.blue_pin);
            case GREEN:
                return ContextCompat.getColor(context, R.color.green_pin);
            case ORANGE:
                return ContextCompat.getColor(context, R.color.orange_pin);
            case PINK:
                return ContextCompat.getColor(context, R.color.pink_pin);
            case RED:
                return ContextCompat.getColor(context, R.color.red_pin);
            case WHITE:
                return ContextCompat.getColor(context, R.color.white_pin);
            case GREY:
                return ContextCompat.getColor(context, R.color.grey_pin);
            case YELLOW:
                return ContextCompat.getColor(context, R.color.yellow_pin);
            case EMPTY:
                return ContextCompat.getColor(context, R.color.empty_pin);
            default:
                return Color.TRANSPARENT;
        }
    }

    public int getImageName(){
        switch(this){
            case BLUE:
                return R.drawable.merkur;
            case GREEN:
                return R.drawable.venus;
            case ORANGE:
                return R.drawable.erde;
            case PINK:
                return R.drawable.mars;
            case RED:
                return R.drawable.jupiter;
            case WHITE:
                return R.drawable.saturn;
            case GREY:
                return R.drawable.uranus;
            case YELLOW:
                return R.drawable.neptun;
            case EMPTY:
                return R.drawable.empty2; //TODO für empty, schwrazes loch oder so?
            default:
                return R.drawable.empty; //TODO iwas kluges machen
        }
    }
}
