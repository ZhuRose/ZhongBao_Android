package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;

import static android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zhu on 17/4/6.
 */

public class DropViewMenu extends ConstraintLayout {
    protected TextView menuTitleView;
    protected ImageView menuIconView;
    protected boolean isSelected;

    public DropViewMenu(Context context) {
        super(context, null);
    }

    public DropViewMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropViewMenu(Context context, String title) {
        super(context, null);
        float density = Constant.getDensity(context);
        //setupMenuTitleView
        menuTitleView = new TextView(context);
        menuTitleView.setId(View.generateViewId());
        ConstraintLayout.LayoutParams layoutParams1 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.leftToLeft = PARENT_ID;
        layoutParams1.rightToRight = PARENT_ID;
        layoutParams1.topToTop = PARENT_ID;
        layoutParams1.bottomToBottom = PARENT_ID;
        layoutParams1.setMarginEnd((int)(18*density));
        this.addView(menuTitleView, layoutParams1);
        menuTitleView.setText(title);
        menuTitleView.setTextColor(getResources().getColor(R.color.colorText));
        menuTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f);
        //setupMenuIconView
        menuIconView = new ImageView(context);
        menuIconView.setId(View.generateViewId());
        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams((int)(12*density), (int)(18*density));
        int aId = menuTitleView.getId();
        layoutParams2.leftToRight = menuTitleView.getId();
        layoutParams2.topToTop = PARENT_ID;
        layoutParams2.bottomToBottom = PARENT_ID;
        layoutParams2.setMarginStart((int)(3*density));
        this.addView(menuIconView, layoutParams2);
        menuIconView.setImageResource(R.drawable.menu_pull_down);
        menuIconView.setScaleType(ImageView.ScaleType.FIT_XY);
        menuIconView.setId(R.id.menuIcon);

        isSelected = false;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            menuTitleView.setTextColor(getResources().getColor(R.color.colorTheme));
            menuIconView.setImageResource(R.drawable.menu_pull_up);
        } else {
            menuTitleView.setTextColor(getResources().getColor(R.color.colorText));
            menuIconView.setImageResource(R.drawable.menu_pull_down);
        }
    }

    public void setIsSelected(boolean isSelected, String title) {
        menuTitleView.setText(title);
        setIsSelected(isSelected);
    }


    public boolean getIsSelected() {
        return isSelected;
    }
}
