package com.example.mastermind.view;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.PinColor;

public class BoardCell extends androidx.appcompat.widget.AppCompatImageButton {

    int xPos, yPos;
    PinColor pinColor;

    public BoardCell(Context context){
        super(context);
    }
    public BoardCell(Context context, int xPos, int yPos) {
        super(context);

        this.xPos = xPos;
        this.yPos = yPos;
        this.pinColor = PinColor.EMPTY;

        displayUnselected(context);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();//GridLayout.LayoutParams) boardCell.getLayoutParams();
        params.height = 100;
        params.width = 100;
        this.setLayoutParams(params);
        this.setScaleType(ImageView.ScaleType.CENTER);

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

    public void displayUnselected(Context context){
//        this.setBackgroundColor(pinColor.getColor());
        Drawable drawi = context.getDrawable(R.drawable.cell_unselected);
        drawi.setColorFilter(new LightingColorFilter(0xFFFFFFFF, pinColor.getColor(context)));
        this.setBackground(drawi);
    }

    public PinColor getPinColor() {
        return pinColor;
    }

    public void displaySelected(Context context) {
        Drawable drawi = context.getDrawable(R.drawable.cell_selected);
        drawi.setColorFilter(new LightingColorFilter(0xFFFFFFFF, pinColor.getColor(context)));
        this.setBackground(drawi);
    }
}
