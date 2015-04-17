package com.zifei.corebeau.common.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zifei.corebeau.R;

public class NavigationBar extends LinearLayout {

    private Context context;

    public TextView title;

//    private Button leftBtn;
//
    private ImageButton rightBtn;

    public TextView rightText;

    private Paint paint;

    private boolean showBackground = true;
    
    private ImageView backImageView;

    public NavigationBar(Context context) {
        super(context);
        init(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.navigation_bar, this);
        title = (TextView) findViewById(R.id.navi_bar_title);
//        leftBtn = (Button) findViewById(R.id.navi_bar_left_btn);
        rightBtn = (ImageButton) findViewById(R.id.navi_bar_right_btn);
        rightText = (TextView) findViewById(R.id.navi_bar_right_text);
        backImageView = (ImageView)findViewById(R.id.iv_bar_left);

        paint = new Paint();
    }

    private void blurBackground(Canvas canvas, int width, int height) {
        paint.setColor(Color.argb(0x99, 0xff, 0xff, 0xff));
        canvas.drawRect(0, 0, width, height, paint);
//        if (context instanceof MainTabActivity) {
//        } else {
            paint.setColor(Color.rgb(0xB2, 0xB2, 0xB2));
            paint.setStrokeWidth(1.0f);
            canvas.drawLine(0, height - 1, width, height - 1, paint);
//        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (showBackground) {
            final int width = getMeasuredWidth();
            final int height = getMeasuredHeight();
            blurBackground(canvas, width, height);
        }
        super.dispatchDraw(canvas);
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public void setLeftButton(int resId, OnClickListener l) {
//        this.leftBtn.setVisibility(View.VISIBLE);
        backImageView.setVisibility(View.VISIBLE);
//        this.leftBtn.setOnClickListener(l);
    }

    public void setRightButton(int resid, OnClickListener l) {
        this.rightBtn.setVisibility(View.VISIBLE);
        this.rightBtn.setImageResource(resid);
        this.rightBtn.setOnClickListener(l);
    }


    public void setLeftButtonVisible(boolean visible) {
        if (visible) {
//            this.leftBtn.setVisibility(View.VISIBLE);
            this.backImageView.setVisibility(View.VISIBLE);
        } else {
//            this.leftBtn.setVisibility(View.INVISIBLE);
//            this.leftBtn.setClickable(false);
            this.backImageView.setVisibility(View.GONE);
        }
    }

    public void setRightButtonVisible(boolean visible) {
        if (visible) {
            this.rightBtn.setVisibility(View.VISIBLE);
        } else {
            this.rightBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void setBackgroundEnabl(boolean enabled) {
        showBackground = enabled;
        invalidate();
    }

    public void setRightText(CharSequence str) {
        this.rightText.setText(str);
    }

    public void setRightTextVisible(boolean visible) {
        if (visible) {
            this.rightText.setVisibility(View.VISIBLE);
        } else {
            this.rightText.setVisibility(View.INVISIBLE);
        }
    }
}
