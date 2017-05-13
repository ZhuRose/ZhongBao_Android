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
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail.OrderDetailActivity;
import com.edu.uestc.zhongbao_android.holder.SetNumViewHolder;
import com.edu.uestc.zhongbao_android.holder.SetPriceViewHolder;
import com.edu.uestc.zhongbao_android.holder.WeekViewHolder;
import com.edu.uestc.zhongbao_android.model.ChoseSiteModel;
import com.edu.uestc.zhongbao_android.model.SitePriceModel;
import com.edu.uestc.zhongbao_android.utils.DateUtil;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.UserManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/6.
 */

public class ChoseSiteActivity extends BaseActivity {

    @BindView(R.id.weekRecycler)
    RecyclerView weekRecycler;

    WeekAdapter weekAdapter;

    String uuid;
    int curSelected;
    WeekViewHolder curWeekViewHolder;

    List<String> dateList;
    List<String> weekList;

    NetworkUtil networkUtil;
    String curDate;
    List<SitePriceModel> dataSource;
    int column;
    float totalPrice;
    Set<Integer> priceArr;

    @BindView(R.id.setRecycler)
    RecyclerView setRecycler;
    SetAdapter setAdapter;

    @BindView(R.id.priceView)
    TextView priceView;

    @OnClick(R.id.paymentBtn)
    void paymentButtonClick(View sender) {
        if (!UserManager.shareManager(mContext).getHasLogin()) {
            Intent intent = new Intent();
            intent.setAction("login");
            mContext.sendBroadcast(intent);
            return;
        }
//        startActivity(new Intent(mContext, OrderDetailActivity.class));
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_site_chose;
    }

    @Override
    protected void initData() {
        super.initData();
        uuid = getIntent().getStringExtra("uuid");
        curSelected = getIntent().getIntExtra("position", 0);
        dateList = DateUtil.get7date();
        weekList = DateUtil.get7week();
        curDate = dateList.get(curSelected);
        totalPrice = 0.00f;
        priceArr = new HashSet<Integer>();
        column = 0;
        dataSource = new ArrayList<SitePriceModel>();
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.dismissDialog();
                ChoseSiteModel model = (ChoseSiteModel)object;
                dataSource.clear();
                dataSource.addAll(model.info);
                column = Integer.parseInt(model.column);
                setAdapter.notifyDataSetChanged();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), "网络错误");
            }
        };
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

        getResponse();
    }

    protected void getResponse() {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.reserveNetwork(uuid, curDate);
    }

    protected String getFormatPriceStr() {
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        if (totalPrice>0.0f) return decimalFormat.format(totalPrice);//format 返回的是字符串
        else return "0.00";
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
            viewHolder.setViews(weekList.get(position), dateList.get(position));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected != position) {
                        if (curWeekViewHolder != null) curWeekViewHolder.setIsSelected(false);
                        viewHolder.setIsSelected(true);
                        curWeekViewHolder = viewHolder;
                        curSelected = position;
                        priceArr.clear();
                        totalPrice = 0.0f;
                        priceView.setText(getFormatPriceStr());
                        curDate = dateList.get(position);
                        getResponse();
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
                int currentColumn = (int)(position/25);
                int currentRow = (int)(position%25) - 1;
                int currentIndex = (int)(column * currentRow) + currentColumn;
                SitePriceModel model = dataSource.get(currentIndex);
                priceViewHolder.setViews(model.price, model.state.equals("0"));
                final SetPriceViewHolder viewHolder = priceViewHolder;
                final float setPrice = Float.parseFloat(model.price);
                final int setNum = Integer.parseInt(model.number);
                priceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.setIsSelected(!viewHolder.getIsSelected());
                        if (viewHolder.getIsSelected()) {
                            priceArr.add(setNum);
                            totalPrice += setPrice;
                        } else {
                            priceArr.remove(setNum);
                            totalPrice -= setPrice;
                        }
                        priceView.setText(getFormatPriceStr());
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
            return dataSource.size()+column;
        }

    }
}
