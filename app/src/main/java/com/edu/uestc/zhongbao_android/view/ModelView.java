package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/28.
 */

public class ModelView extends ConstraintLayout {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    public ModelView(Context context) {
        super(context, null);
    }

    public ModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_model, this);
        ButterKnife.bind(this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ModelView);
        try {
            iconView.setImageResource(array.getResourceId(R.styleable.ModelView_modelIcon, R.drawable.order));
            titleView.setText(array.getString(R.styleable.ModelView_modelTitle));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    public void setTitle(String title) {
        iconView.setVisibility(GONE);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)titleView.getLayoutParams();
        layoutParams.setMarginStart(20);
    }
}
