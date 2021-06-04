package com.example.mastermind.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.gridlayout.widget.GridLayout;

public class MyGridLayout extends GridLayout {


    public MyGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridLayout(Context context) {
        super(context);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.getWidth();
        this.getHeight();
        //hier weiß das ding wei breit es ist
//        GameActivity.testi(this.getWidth(), this.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.getWidth();
        this.getHeight();
    }
}
