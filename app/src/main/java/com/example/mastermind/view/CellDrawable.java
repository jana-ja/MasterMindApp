package com.example.mastermind.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class CellDrawable extends ShapeDrawable {

    private Paint paint;

    public CellDrawable(Color color) {
        this.setShape(new OvalShape());
    }

//    @Override
//    public void draw(Canvas canvas) {
//        int height = getBounds().height();
//        int width = getBounds().width();
//        RectF rect = new RectF(0.0f, 0.0f, width, height);
//        canvas.drawRoundRect(rect, 30, 30, paint);
//        canvas.drawCircle(0,0,);
//    }
//
//    @Override
//    public void setAlpha(int alpha) {
//        paint.setAlpha(alpha);
//    }
//
//    @Override
//    public void setColorFilter(ColorFilter cf) {
//        paint.setColorFilter(cf);
//    }
//
//    @Override
//    public int getOpacity() {
//        return PixelFormat.TRANSLUCENT;
//    }

}
