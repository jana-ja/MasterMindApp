package com.example.mastermind.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.PinColor;

public class BoardCell extends Cell {

    int xPos, yPos; //achtung xpos wird von unten nach oben indiziert, x=0 ist also ganz unten, y=0 ist links
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

    public void displayUnselected(Context context) {
        this.setImageResource(pinColor.getImageName());
    }

    public void displaySelected(Context context) {
        // TODO was überlegen wie ich die selected darstellen will, vermutlich son shine dahinter
        displayUnselected(context);
    }
}