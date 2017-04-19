package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

/**
 * Created by zhu on 17/4/7.
 */

public class CountDownButton extends FrameLayout {
    protected TextView textView;
    protected int time;
    Handler mhandler;
    CountDownRunnable runnable;

    public CountDownButton(Context context) {
        super(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(getResources().getColor(R.color.colorUnable));
        textView = new TextView(context);
        textView.setText("验证");
        textView.setTextColor(getResources().getColor(R.color.colorTheme));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        this.addView(textView, layoutParams);

        mhandler = new Handler();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mhandler.removeCallbacks(runnable);
    }

    public void beginCountDown() {
        time = 59;
        this.setClickable(false);
        runnable = new CountDownRunnable();
        mhandler.post(runnable);
    }

    class CountDownRunnable implements Runnable {

        @Override
        public void run() {
            if (time<=0) {//倒计时结束，关闭
                mhandler.removeCallbacks(this);
                textView.setText("验证");
                textView.setTextColor(getResources().getColor(R.color.colorTheme));
                CountDownButton.this.setClickable(true);
            } else {
                textView.setText("" + time);
                textView.setTextColor(Color.WHITE);
            }
            time--;
            mhandler.postDelayed(this, 1000);
        }
    }
}
