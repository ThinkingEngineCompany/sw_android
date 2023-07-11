package com.sw.beauty.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

public class StrokeTextView extends androidx.appcompat.widget.AppCompatTextView {

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final ColorStateList textColor = getTextColors();
        TextPaint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeMiter(10);
        setTextColor(0xffffffff);
        paint.setStrokeWidth(1);
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        setTextColor(textColor);
        super.onDraw(canvas);

    }
}
