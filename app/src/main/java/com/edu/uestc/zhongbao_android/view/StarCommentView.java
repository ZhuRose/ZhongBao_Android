package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/10.
 */

public class StarCommentView extends LinearLayout {
    float density;
    ImageView[] starViews;

    public int grades;

    public StarCommentView(Context context) {
        super(context, null);
    }

    public StarCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);
        grades = 0;
        density = Constant.getDensity(context);
        starViews = new ImageView[5];
        for (int i=0; i<5; i++) {
            ImageView starView = new ImageView(context);
            starView.setImageResource(R.drawable.star_empty);
            starView.setScaleType(ImageView.ScaleType.FIT_XY);
            starViews[i] = starView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(21*density), (int)(21*density));
            params.setMarginEnd((int)(3*density));
            this.addView(starView, params);
        }
    }

    protected void setStarsFromGrades(float touchGrades) {
        grades = 0;
        for(int i=0; i<5; i++) {
            ImageView starView = starViews[i];
            if (touchGrades<i+0.4) {
                starView.setImageResource(R.drawable.star_empty);
            } else {
                starView.setImageResource(R.drawable.star);
                grades++;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float touchGrades = event.getX()/(24*density);
            setStarsFromGrades(touchGrades);
            return true;
        }
        return false;
    }
}
