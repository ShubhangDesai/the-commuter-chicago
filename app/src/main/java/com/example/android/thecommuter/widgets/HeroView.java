package com.example.android.thecommuter.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HeroView extends ImageView {

    public HeroView(Context context) {
        super(context);
    }

    public HeroView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
}
