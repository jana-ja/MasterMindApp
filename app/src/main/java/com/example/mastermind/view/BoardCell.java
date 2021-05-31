package com.example.mastermind.view;

import android.content.Context;

import com.example.mastermind.model.PinColor;

public class BoardCell extends androidx.appcompat.widget.AppCompatImageButton {

    int xPos, yPos;
    PinColor pinColor;

    public BoardCell(Context context, int xPos, int yPos) {
        super(context);

        this.xPos = xPos;
        this.yPos = yPos;
        this.pinColor = PinColor.EMPTY;

//        displayColor();
    }

    public void setGridPos(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setPinColor(PinColor pinColor){
        this.pinColor = pinColor;
//        displayColor(); //TODO wieder rein??
    }

    public void displayColor(){
        this.setBackgroundColor(pinColor.getColor());
    }

    public PinColor getPinColor() {
        return pinColor;
    }
}
