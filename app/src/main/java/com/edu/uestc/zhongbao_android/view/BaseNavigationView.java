package com.edu.uestc.zhongbao_android.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/25.
 */

public class BaseNavigationView extends ConstraintLayout {
    @BindView(R.id.navigation_bgView)
    View bgView;

    @BindView(R.id.navigation_backbutton)
    Button backButton;

    @BindView(R.id.navigation_titleview)
    TextView titleView;


    public BaseNavigationView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_navigation, this);
        ButterKnife.bind(this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseNavigationView);
        try {
            titleView.setText(array.getString(R.styleable.BaseNavigationView_navigationtitle));
            backButton.setVisibility(array.getBoolean(R.styleable.BaseNavigationView_backButtonHidden, false)?GONE:VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }


        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =  (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(((Activity)context).getWindow().getDecorView().getWindowToken(),
                            0);
                }
                ((Activity)context).finishAfterTransition();
            }
        });

    }

    public BaseNavigationView(Context context) {
        super(context, null);
    }

    public void hideBackButton(boolean isHidden) {
        if (isHidden) backButton.setVisibility(GONE);
        else backButton.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setBgAlpha(float alpha) {
        bgView.setAlpha(alpha);
    }

}
