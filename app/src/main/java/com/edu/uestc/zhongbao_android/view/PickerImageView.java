package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by zhu on 17/4/10.
 */

public class PickerImageView extends FrameLayout {
    int height;
    int margin;
    SelectImgView[] imgViews;
    public List<String> imgPathList;

    public Button photoAddBtn;
    FrameLayout.LayoutParams photoParams;

    public PickerImageView(Context context) {
        super(context, null);
    }

    public PickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        float density = Constant.getDensity(context);
        int screentWidth = Constant.getScreenWidth(context);
        height = (int)(((float)screentWidth - density*96)/5);
        margin = (int)(density*12);

        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + margin*4);
        mainParams.setMargins(0,margin/12,0,0);
        this.setLayoutParams(mainParams);

        imgPathList = new ArrayList<String>();
        photoAddBtn = new Button(context);
        photoAddBtn.setBackgroundResource(R.drawable.photo_add);
        photoParams = new FrameLayout.LayoutParams(height, height);
        photoParams.setMargins(margin*2, margin*2, 0, 0);
        this.addView(photoAddBtn, photoParams);

        setupImgView();
    }

    public void setupImgView() {
        imgViews = new SelectImgView[9];

        int beginx = margin*2;
        int beginy = margin*2;
        for (int i =0; i<9; i++) {
            SelectImgView imgView = new SelectImgView(getContext());
            imgViews[i] = imgView;
            final int index = i;
            imgView.deleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPathList.remove(index);
                    layoutImg();
                }
            });

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(height, height);
            params.setMargins(beginx, beginy, 0, 0);
            this.addView(imgView, params);

            imgView.setVisibility(GONE);

            beginx += height+margin;
            if (i==4) {
                beginx = margin*2;
                beginy += height+margin;
            }
        }
    }

    public void layoutImg() {
        int beginx = margin*2;
        int beginy = margin*2;

        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + margin*4 + (height+margin)* (imgPathList.size()/5));
        mainParams.setMargins(0,margin/12,0,0);
        this.setLayoutParams(mainParams);
        this.requestLayout();

        for (int i=0; i<9; i++) {
            SelectImgView imgView = imgViews[i];
            if (i<imgPathList.size()) {
                imgView.setVisibility(VISIBLE);
                ImageLoadManager.shareManager().displayImage("file:///"+imgPathList.get(i), imgView.imgView);
                beginx += height+margin;
                if (i==4) {
                    beginx = margin*2;
                    beginy += height+margin;
                }
            }
            else imgView.setVisibility(GONE);
        }

        if (imgPathList.size()==9) photoAddBtn.setVisibility(GONE);
        else {
            photoAddBtn.setVisibility(VISIBLE);
            photoParams.setMargins(beginx, beginy, 0, 0);
            photoAddBtn.requestLayout();
        }
    }

}
