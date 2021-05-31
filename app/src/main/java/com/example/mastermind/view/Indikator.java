package com.example.mastermind.view;

import android.content.Context;
import android.widget.TextView;

public class Indikator extends androidx.appcompat.widget.AppCompatTextView {

    private int xPos, yPos;

    public Indikator(Context context, int xPos, int yPos) {
        super(context);
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
