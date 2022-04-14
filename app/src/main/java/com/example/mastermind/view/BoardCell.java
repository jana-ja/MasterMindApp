package com.example.mastermind.view;

import android.content.Context;

import com.example.mastermind.model.PinColor;

public class BoardCell extends Cell {

    PinColor pinColor;

    public BoardCell(Context context, int xPos, int yPos) {
        super(context, xPos, yPos);
        this.pinColor = PinColor.EMPTY;
    }

    public void setPinColor(PinColor pinColor) {
        // adapt margin
        switch(pinColor){
            case SATURN:
                setMargin(0);
                break;
//            case PLUTO:
//                setMargin(0.15);
//                break;
            default:
                setMargin(0.05);
                break;
        }
        this.pinColor = pinColor;
    }

    public PinColor getPinColor() {
        return pinColor;
    }

    public void display() {
        this.setImageResource(pinColor.getImageName());
    }

}