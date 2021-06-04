package com.example.mastermind.view;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;
import com.example.mastermind.model.PinColor;

public class BoardCell extends androidx.appcompat.widget.AppCompatImageButton {

    int xPos, yPos; //achtung xpos wird von unten nach oben indiziert, x=0 ist also ganz unten, y=0 ist links
    PinColor pinColor;

    public BoardCell(Context context){
        super(context);
    }
    public BoardCell(Context context, int xPos, int yPos) {
        super(context);

        this.xPos = xPos;
        this.yPos = yPos;
        this.pinColor = PinColor.EMPTY;


        GridLayout.LayoutParams params = new GridLayout.LayoutParams();//GridLayout.LayoutParams) boardCell.getLayoutParams();
//        params.setGravity(Gravity.CENTER);
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
//
//
//        params.height = (int)context.getResources().getDimension(R.dimen.board_cell);
//        params.width = (int)context.getResources().getDimension(R.dimen.board_cell);
        this.setLayoutParams(params);
        this.setScaleType(ImageView.ScaleType.CENTER);

        displayUnselected(context);


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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
}
