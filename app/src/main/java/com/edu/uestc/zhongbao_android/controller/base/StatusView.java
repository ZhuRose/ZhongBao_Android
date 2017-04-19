package com.edu.uestc.zhongbao_android.controller.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.edu.uestc.zhongbao_android.application.Constant;

/**
 * Created by zhu on 17/4/9.
 */

public class StatusView extends View {
    public static final int Status_Success = 0;
    public static final int Status_Failed = 1;
    public static final int Status_Loading = 2;
    private int status = 0;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //画笔
    private float density;
    private int width;
    private int height;

    protected float progress;
    ValueAnimator animator;

    public StatusView(Context context) {
        super(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = Constant.getDensity(context);
        progress = 0.0f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2*density);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);// 圆角
        mPaint.setAntiAlias(true); //消除锯齿
        switch (status) {
            case Status_Success:
                drawSuccess(canvas);
                break;
            case Status_Failed:
                drawFailed(canvas);
                break;
            default:
                drawLoading(canvas);
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleLoading();
    }

    protected void drawSuccess(Canvas canvas) {
        canvas.drawLine(getPaddingLeft()+width/4, getPaddingTop()+height/2, getPaddingLeft()+width/2, getPaddingTop()+height*3/4, mPaint);
        canvas.drawLine(getPaddingLeft()+width/2, getPaddingTop()+height*3/4, getPaddingLeft()+width, getPaddingTop()+height/4, mPaint);
    }

    protected void drawFailed(Canvas canvas) {
        canvas.drawLine(getPaddingLeft()+width/4, getPaddingTop()+height/4, getPaddingLeft()+width*3/4, getPaddingTop()+height*3/4, mPaint);
        canvas.drawLine(getPaddingLeft()+width*3/4, getPaddingTop()+height/4, getPaddingLeft()+width/4, getPaddingTop()+height*3/4, mPaint);
    }

    protected void drawLoading(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        int offset = (int)density;
        int radius = width/2 - offset;

        canvas.drawCircle(getPaddingLeft()+width/2, getPaddingTop()+height/2, radius, mPaint);
        RectF oval = new RectF();
        oval.set(getPaddingLeft()+offset, getPaddingTop()+height/2-radius, getPaddingLeft()+width-offset, getPaddingTop()+height/2+radius);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(oval, -90, progress, false, mPaint);

    }

    public void setStatus(int status) {
        this.status = status;
        invalidate();
        if (status == Status_Loading) beginLoading();
        else cancleLoading();
    }

    public void beginLoading() {
        if (status != Status_Loading) {
            cancleLoading();
            return;
        }
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(2000);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(1);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setProgressFloat((float)animation.getAnimatedValue());
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.v("dialog", ""+animator.getRepeatCount());
                    animator.setRepeatCount(animator.getRepeatCount()+1);
                }
            });
        }

        animator.start();
    }

    public void cancleLoading() {
        if (animator!=null) {
            animator.cancel();
            animator = null;
        }
    }

    protected void setProgressFloat(float progress) {
        this.progress = progress * 360;
        this.invalidate();
    }
}
