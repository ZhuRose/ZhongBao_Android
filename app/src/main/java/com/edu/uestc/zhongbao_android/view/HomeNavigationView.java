package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/3/27.
 */

public class HomeNavigationView extends ConstraintLayout {
    @BindView(R.id.bgView)
    View bgView;

    @BindView(R.id.searchView)
    CardView searchView;

    @BindView(R.id.locationBtn)
    LinearLayout locationBtn;

    @BindView(R.id.locationTitle)
    TextView locationTitle;

    @BindView(R.id.mapBtn)
    Button mapButton;


    public HomeNavigationView(Context context) {
        super(context, null);
    }

    public HomeNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_navigation_home, this);
        ButterKnife.bind(this);
        setBgAlpha(0.0f);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setBgAlpha(float alpha) {
        bgView.setAlpha(alpha);
    }

    public void setSearchViewClickListener(OnClickListener listener) {
        searchView.setOnClickListener(listener);
    }

    public void setLocationBtnClickListener(OnClickListener listener) {
        locationBtn.setOnClickListener(listener);
    }

    public void setMapBtnClickListener(OnClickListener listener) {
        mapButton.setOnClickListener(listener);
    }

    public void setLocationTitle(String title) {
        locationTitle.setText(title);
    }
}
