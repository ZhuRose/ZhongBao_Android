package com.edu.uestc.zhongbao_android.controller.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.edu.uestc.zhongbao_android.application.Constant;
import com.liuguangqiang.swipeback.SwipeBackLayout;

/**
 * Created by zhu on 17/4/5.
 */

public class BaseSwipeBackLayout extends SwipeBackLayout {
    private View contentView;
    private int offsetX;
    private boolean isMoving;


    public void setDragEdgeOffsetX(int offsetX) {
        setDragEdge(DragEdge.LEFT);
        this.offsetX = offsetX;
    }

    public void setContentView(View view) {
        this.contentView = view;
        this.addView(view);
    }

    public BaseSwipeBackLayout(Context context) {
        super(context);
        isMoving = false;
    }

    public BaseSwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        isMoving = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("swipe", ""+event.getRawX()+"/"+event.getX()+"/"+contentView.getX());
//        InputMethodManager imm =  (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if(imm != null) {
//            imm.hideSoftInputFromWindow(((Activity)getContext()).getWindow().getDecorView().getWindowToken(),
//                    0);
//        }
        if (event.getRawX()> offsetX + contentView.getX() && !isMoving) return false;
        isMoving = true;
        boolean isWaiting = super.onTouchEvent(event);
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) isMoving=false;
        return isWaiting;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getRawX()> offsetX + contentView.getX() && !isMoving) return false;
        return super.onInterceptTouchEvent(ev);
    }

}
