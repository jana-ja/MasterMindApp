package com.example.mastermind.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;


public abstract class Cell extends androidx.appcompat.widget.AppCompatImageButton{
    int xPos, yPos; //achtung xpos wird von unten nach oben indiziert, x=0 ist also ganz unten, y=0 ist links
    int width, height;

    public Cell(Context context, int xPos, int yPos) {
        super(context);

        this.xPos = xPos;
        this.yPos = yPos;


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

    public void setLayoutParams(int width, int height){
        setLayoutParams(width, height, 0.95);
    }

    public void setLayoutParams(int width, int height, double percent){
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        this.width = width;
        this.height = height;

        params.height = (int)(percent * height);
        params.width = (int)(percent * width);

        int horizontalMargin = (int)((1 - percent) * width / 2);
        int verticalMargin = (int)((1- percent) * height / 2);
        params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        this.setLayoutParams(params);
    }

    /**
     * can only be used after init with setLayoutParams
     * @param percent
     */
    public void setMargin(double percent){
        setLayoutParams(width,height,1.0-percent);
    }

}
