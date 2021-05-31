package com.example.mastermind.model;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.mastermind.R;

public enum PinColor {
    RED, ORANGE, YELLOW, GREEN, BLUE, PINK, GREY, WHITE, EMPTY;

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
}
