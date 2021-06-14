package com.example.mastermind.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.core.widget.TextViewCompat;
import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;

public class Indikator extends androidx.appcompat.widget.AppCompatTextView {

    private int xPos, yPos;

    public Indikator(Context context) {
        super(context);


    }

    public Indikator(Context context, int xPos, int yPos) {
        super(context);
        this.xPos = xPos;
        this.yPos = yPos;

        //TODO wenn ich das wegmache uentriert sich die solution nicht mehr, gute lösung finden oder so lassen?
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        this.setLayoutParams(params);

        TextViewCompat.setAutoSizeTextTypeWithDefaults(this, AUTO_SIZE_TEXT_TYPE_UNIFORM);
    }
    public void setLayoutParams(int width, int height){
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();//GridLayout.LayoutParams) boardCell.getLayoutParams();
//        params.setGravity(Gravity.CENTER);
//        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
//        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

        double dings = 0.95;

        params.height = (int)(dings * height);
        params.width = (int)(dings * width);

        int horizontalMargin = (int)((1 - dings) * width / 2);
        int verticalMargin = (int)((1- dings) * height / 2);
        params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        this.setLayoutParams(params);
    }

}
