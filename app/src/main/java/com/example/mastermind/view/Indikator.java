package com.example.mastermind.view;

import android.content.Context;
import android.view.Gravity;

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
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();//GridLayout.LayoutParams) boardCell.getLayoutParams();
//        params.setGravity(Gravity.CENTER);

//        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
//        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.height = (int)context.getResources().getDimension(R.dimen.board_cell);
        params.width = (int)context.getResources().getDimension(R.dimen.board_cell);
//        params.height = 50;
//        params.width = 50;
        this.setLayoutParams(params);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this, AUTO_SIZE_TEXT_TYPE_UNIFORM);
    }
}
