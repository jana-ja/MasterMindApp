package com.example.mastermind.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.core.widget.TextViewCompat;
import androidx.gridlayout.widget.GridLayout;

import com.example.mastermind.R;

public class Indicator extends Cell {

    private int xPos, yPos;

    public Indicator(Context context, int xPos, int yPos) {
        super(context, xPos, yPos);

        // TODO sind hier anpassungen für image view notwendig? rakete und zahlen vs planeten
    }

    public void setNumber(int number, int color){

    }

    public void highlight(){
        // show rocket
        this.setImageResource(R.drawable.rakete);
    }

}
