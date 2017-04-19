package com.edu.uestc.zhongbao_android.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.uestc.zhongbao_android.R;

/**
 * Created by zhu on 17/4/9.
 */

public class CustomToast {
    private Toast mToast;
    private CustomToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.cell_base, null);
        TextView textView = (TextView) v.findViewById(R.id.titleView);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        v.setBackgroundColor(Color.YELLOW);
        mToast.setView(v);
        mToast.setGravity(Gravity.CENTER, 0, 0);
    }

    public static CustomToast makeText(Context context, CharSequence text, int duration) {
        return new CustomToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
