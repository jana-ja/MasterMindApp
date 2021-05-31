package com.example.mastermind.view;

import android.content.Context;

public class Indikator extends androidx.appcompat.widget.AppCompatTextView {

    private int xPos, yPos;

    public Indikator(Context context) {
        super(context);
    }

    public Indikator(Context context, int xPos, int yPos) {
        super(context);
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
