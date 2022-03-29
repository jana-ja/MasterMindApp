package com.example.mastermind.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.gridlayout.widget.GridLayout;


public abstract class Cell extends androidx.appcompat.widget.AppCompatImageButton{
    int xPos, yPos; //achtung xpos wird von unten nach oben indiziert, x=0 ist also ganz unten, y=0 ist links

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
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        double dings = 0.95;

        params.height = (int)(dings * height);
        params.width = (int)(dings * width);

        int horizontalMargin = (int)((1 - dings) * width / 2);
        int verticalMargin = (int)((1- dings) * height / 2);
        params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        this.setLayoutParams(params);
    }

}
