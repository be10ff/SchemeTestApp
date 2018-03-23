package ru.abelov.schemeTimeComponent.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class HUnScroll extends HorizontalScrollView {

    public HUnScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HUnScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HUnScroll(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}