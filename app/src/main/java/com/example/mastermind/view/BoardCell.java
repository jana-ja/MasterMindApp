package com.example.mastermind.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.PinColor;

public class BoardCell extends androidx.appcompat.widget.AppCompatImageButton {

    int xPos, yPos; //achtung xpos wird von unten nach oben indiziert, x=0 ist also ganz unten, y=0 ist links
    PinColor pinColor;

    public BoardCell(Context context) {
        super(context);
    }

    public BoardCell(Context context, int xPos, int yPos) {
        super(context);

        this.xPos = xPos;
        this.yPos = yPos;
        this.pinColor = PinColor.EMPTY;


        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

        this.setLayoutParams(params);

        // to use image resources
        this.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.setAdjustViewBounds(true);
        this.setPadding(0,0,0,0);
        this.setBackgroundColor(Color.TRANSPARENT);

    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setPinColor(PinColor pinColor) {
        this.pinColor = pinColor;
    }

    public void setLayoutParams(int size){
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        double dings = 0.95;

        params.height = (int)(dings * size);
        params.width = (int)(dings * size);

        int margin = (int)((1 - dings) * size / 2);
        params.setMargins(margin, margin, margin, margin);

        this.setLayoutParams(params);
    }

    public void displayUnselected(Context context) {
        this.setImageResource(pinColor.getImageName());
    }

    public PinColor getPinColor() {
        return pinColor;
    }

    public void displaySelected(Context context) {
        // TODO was überlegen wie ich die selected darstellen will, vermutlich son shine dahinter
        displayUnselected(context);
    }
}