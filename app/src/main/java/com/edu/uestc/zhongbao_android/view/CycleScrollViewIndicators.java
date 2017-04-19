package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.edu.uestc.zhongbao_android.R;

/**
 * Created by zhu on 17/3/14.
 */

public class CycleScrollViewIndicators extends PageController {
    protected float circleWidth;

    public CycleScrollViewIndicators(Context context) {
        super(context, null);
    }

    public CycleScrollViewIndicators(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CycleScrollViewIndicators);
        try {
            circleWidth = array.getDimension(R.styleable.CycleScrollViewIndicators_circleWidth, 8);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    protected void drawNib(Canvas canvas) {
        for (int i=0; i<this.totalPages; i++) {
            if (i == this.currentPage) {
                mPaint.setColor(this.selectedColor);
            } else  {
                mPaint.setColor(this.unselectedColor);
            }
            canvas.drawRoundRect(getPaddingLeft() + i * (circleWidth+interval), getPaddingTop(), getPaddingLeft() + circleWidth + i * (circleWidth+interval), getPaddingTop() + radius*2, radius, radius, mPaint);
        }
    }

    protected int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specModel = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specModel == MeasureSpec.EXACTLY) {
            result = specSize;
        } else  {
            result = (int)(getPaddingLeft() + getPaddingRight() + (this.totalPages * circleWidth) + this.interval *(this.totalPages-1));
            if (specModel == MeasureSpec.AT_MOST) {
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

    protected int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specModel = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specModel == MeasureSpec.EXACTLY) {
            result = specSize;
        } else  {
            result = (int)(getPaddingTop() + getPaddingBottom() + radius *2 );
            if (specModel == MeasureSpec.AT_MOST) {
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

}
