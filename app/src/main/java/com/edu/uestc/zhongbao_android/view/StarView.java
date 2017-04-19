package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.edu.uestc.zhongbao_android.R;

/**
 * Created by zhu on 17/3/25.
 */

public class StarView extends LinearLayout {

    public float score;
    private ImageView[] imageViews = new ImageView[5];

    public StarView(Context context) {
        super(context, null);
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);
        float density = getResources().getDisplayMetrics().density;
        for (int i=0; i<5; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.star_empty);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LayoutParams layoutParams = new LayoutParams((int)(17*density), (int)(17*density));
            if(i != 0) layoutParams.setMarginStart((int)(2*density));
            this.addView(imageView, layoutParams);
            imageViews[i] = imageView;
        }

    }

    public void setScore(float score) {
        this.score = score;
        for (int i=0; i<imageViews.length; i++) {
            ImageView imageView = imageViews[i];
            if (score<i+0.5) imageView.setImageResource(R.drawable.star_empty);
            else if (score<i+1.0) imageView.setImageResource(R.drawable.star_half);
            else imageView.setImageResource(R.drawable.star);
        }
    }


}
