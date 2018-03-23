package ru.abelov.schemeTimeComponent.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VUnScroll extends ScrollView {

    public VUnScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VUnScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VUnScroll(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}