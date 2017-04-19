package com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail.OrderDetailActivity;
import com.edu.uestc.zhongbao_android.holder.SetNumViewHolder;
import com.edu.uestc.zhongbao_android.holder.SetPriceViewHolder;
import com.edu.uestc.zhongbao_android.holder.WeekViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/6.
 */

public class ChoseSiteActivity extends BaseActivity {

    @BindView(R.id.weekRecycler)
    RecyclerView weekRecycler;

    WeekAdapter weekAdapter;

    int curSelected;
    WeekViewHolder curWeekViewHolder;

    @BindView(R.id.setRecycler)
    RecyclerView setRecycler;
    SetAdapter setAdapter;

    @OnClick(R.id.paymentBtn)
    void paymentButtonClick(View sender) {
        startActivity(new Intent(mContext, OrderDetailActivity.class));
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_site_chose;
    }

    @Override
    protected void initData() {
        super.initData();
        curSelected = 0;
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager llm = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        weekRecycler.setLayoutManager(llm);
        weekAdapter = new WeekAdapter();
        weekRecycler.setAdapter(weekAdapter);
        weekRecycler.addItemDecoration(new WeekDecoration(mContext));

//        new StaggeredGridLayoutManager()
        GridLayoutManager glm = new GridLayoutManager(mContext, 345, GridLayoutManager.HORIZONTAL, false);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position%25 == 0) return 9;
                else return 14;
            }
        });
        setRecycler.setLayoutManager(glm);
        setAdapter = new SetAdapter();
        setRecycler.setAdapter(setAdapter);
    }

    class WeekAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.cell_week, null);
            WeekViewHolder viewHolder = new WeekViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final WeekViewHolder viewHolder = (WeekViewHolder)holder;
            if (position == curSelected) {
                viewHolder.setIsSelected(true);
                curWeekViewHolder = viewHolder;
            }
            else viewHolder.setIsSelected(false);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected != position) {
                        if (curWeekViewHolder != null) curWeekViewHolder.setIsSelected(false);
                        viewHolder.setIsSelected(true);
                        curWeekViewHolder = viewHolder;
                        curSelected = position;
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }

    class WeekDecoration extends RecyclerView.ItemDecoration {
        private Context mContext;
        private Drawable mDivider;

        public WeekDecoration(Context context) {
            mContext = context;
            mDivider = getResources().getDrawable(R.drawable.line_week);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            drawVerticalLine(c, parent, state);
        }

        //画竖线
        public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state){
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++){
                final View child = parent.getChildAt(i);

                //获得child的布局信息
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    class SetAdapter extends RecyclerView.Adapter {
        final int TypeSet_Num = 0;
        final int TypeSet_Price = 1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder;
            View view;
            if (viewType == TypeSet_Num) {
                view = LayoutInflater.from(mContext).inflate(R.layout.cell_num_set, parent, false);
                viewHolder = new SetNumViewHolder(view);
            } else  {
                view = LayoutInflater.from(mContext).inflate(R.layout.cell_price_set, parent, false);
                viewHolder = new SetPriceViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof SetNumViewHolder) {
                SetNumViewHolder numViewHolder = (SetNumViewHolder) holder;
                numViewHolder.setNum(position/25 +1);
            } else {
                SetPriceViewHolder priceViewHolder = (SetPriceViewHolder)holder;
                boolean enable = position%7 == 0? false :true;
                priceViewHolder.setViews(""+position, enable);
                final SetPriceViewHolder viewHolder = priceViewHolder;
                priceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.setIsSelected(!viewHolder.getIsSelected());
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position%25 == 0) return TypeSet_Num;
            else return TypeSet_Price;
        }

        @Override
        public int getItemCount() {
            return 250;
        }

    }
}
