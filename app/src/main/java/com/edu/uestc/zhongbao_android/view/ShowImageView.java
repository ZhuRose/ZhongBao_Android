package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by zhu on 17/4/11.
 */

public class ShowImageView extends FrameLayout {

    int height;
    int margin;
    ImageView[] imgViews;
    public List<String> imgUrlList;

    public ShowImageView(Context context) {
        super(context, null);
    }

    public ShowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        float density = Constant.getDensity(context);
        int screentWidth = Constant.getScreenWidth(context);
        height = (int)(((float)screentWidth - density*126)/3);
        margin = (int)(density*12);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, height);
        layoutParams.topToBottom = R.id.contentView;
        layoutParams.leftToLeft = R.id.contentView;
        layoutParams.setMargins(0, margin, 0, 0);
        this.setLayoutParams(layoutParams);

        imgUrlList = new ArrayList<String>();

        setupImgView();
    }

    protected void setupImgView() {
        imgViews = new ImageView[9];

        int beginx = 0;
        int beginy = 0;
        for (int i =0; i<9; i++) {
            ImageView imgView = new ImageView(getContext());
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgViews[i] = imgView;

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(height, height);
            params.setMargins(beginx, beginy, 0, 0);
            this.addView(imgView, params);

            imgView.setVisibility(GONE);

            beginx += height+margin;
            if (i==2 || i==5) {
                beginx = 0;
                beginy += height+margin;
            }
        }
    }

    public void layoutImg() {
        int line = 0;
        if (imgUrlList.size()>0) line++;
        if (imgUrlList.size()>3) line++;
        if (imgUrlList.size()>6) line++;
        ConstraintLayout.LayoutParams layoutParams;
        if (line>0) {
            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, line*height +(line-1)*margin);
            layoutParams.setMargins(0, margin, 0, 0);
        }
        else {
            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, 0);
            layoutParams.setMargins(0, 0, 0, 0);
        }
        layoutParams.topToBottom = R.id.contentView;
        layoutParams.leftToLeft = R.id.contentView;
        this.setLayoutParams(layoutParams);
        this.requestLayout();

        for (int i=0; i<9; i++) {
            ImageView imgView = imgViews[i];
            if (i<imgUrlList.size()) {
                imgView.setVisibility(VISIBLE);
                imgView.setImageResource(R.drawable.home_page);
//                ImageLoadManager.shareManager().displayImage(imgUrlList.get(i), imgView);
            }
            else imgView.setVisibility(GONE);
        }
    }
}
