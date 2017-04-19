package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;

/**
 * Created by zhu on 17/4/10.
 */

public class SelectImgView extends FrameLayout {
    public ImageView imgView;
    public Button deleteBtn;

    public SelectImgView(Context context) {
        super(context, null);
        setupView(context);
    }

    public SelectImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);

    }

    public void setupView(Context context) {
        imgView = new ImageView(context);
        imgView.setImageResource(R.drawable.home_page);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        FrameLayout.LayoutParams imgViewParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(imgView, imgViewParams);
        deleteBtn = new Button(context);
        deleteBtn.setBackgroundResource(R.drawable.photo_delete);
        float density = Constant.getDensity(context);
        FrameLayout.LayoutParams deleteBtnParams = new FrameLayout.LayoutParams((int)(density*26), (int)(density*26));
        deleteBtnParams.gravity = Gravity.RIGHT;
        this.addView(deleteBtn, deleteBtnParams);
    }
}
