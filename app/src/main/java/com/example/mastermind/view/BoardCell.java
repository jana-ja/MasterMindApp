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
        this.pinColor = pinColor;
    }

    public PinColor getPinColor() {
        return pinColor;
    }

    public void display() {
        this.setImageResource(pinColor.getImageName());
    }

}