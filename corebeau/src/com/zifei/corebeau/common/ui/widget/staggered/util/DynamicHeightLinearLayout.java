package com.zifei.corebeau.common.ui.widget.staggered.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DynamicHeightLinearLayout extends LinearLayout {

    private double mHeightRatio;

    public DynamicHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightLinearLayout(Context context) {
        super(context);
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}