package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.edu.uestc.zhongbao_android.R;

/**
 * Created by zhu on 17/3/14.
 */

public class PageController extends View {
    protected float radius = 4; //半径
    protected final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //画笔
    protected float interval = radius; //间隔
    protected int totalPages = 1; //总页数
    protected int currentPage = 0; //当前页数
    protected int unselectedColor = 0xFFFFFF; //未选中颜色
    protected int selectedColor = 0x000000; //选中颜色


    public PageController(Context context) {
        super(context, null);
    }

    public PageController(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PageController);
        try {
            unselectedColor = array.getColor(R.styleable.PageController_unselectedColor, unselectedColor);
            selectedColor = array.getColor(R.styleable.PageController_selectedColor, selectedColor);
            radius = array.getDimension(R.styleable.PageController_radius, radius);
            interval = array.getDimension(R.styleable.PageController_interval, interval);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    public void setTotalPages(int total, int cur) {
        this.totalPages = total;
        this.currentPage = cur;
        invalidate();
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNib(canvas);
    }


    protected int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specModel = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specModel == MeasureSpec.EXACTLY) {
            result = specSize;
        } else  {
            result = (int)(getPaddingLeft() + getPaddingRight() + (this.totalPages * radius *2) + this.interval *(this.totalPages-1));
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


    protected void drawNib(Canvas canvas) {
        for (int i=0; i<this.totalPages; i++) {
            if (i == this.currentPage) {
                mPaint.setColor(this.selectedColor);
            } else  {
                mPaint.setColor(this.unselectedColor);
            }
            canvas.drawCircle(getPaddingLeft() + radius + i * (radius * 2 + this.interval), getPaddingTop() + radius , radius, mPaint);
        }
    }

}
