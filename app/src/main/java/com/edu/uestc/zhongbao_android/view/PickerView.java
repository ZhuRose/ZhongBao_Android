package com.edu.uestc.zhongbao_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 17/4/2.
 */

public class PickerView extends ConstraintLayout {
    protected PickerViewDataSource dataSource;
    protected PickerViewDelegate delegate;

    public int defaultTextColor = R.color.colorText;
    public int selectedTextColor = R.color.colorTheme;

    public int[] selectedArr;//当前选中结果

    protected int totalColums;
    protected int visibleCells;
    protected List<String[]> dataList;

    protected View bgView;
    protected LinearLayout linear;

    protected int cellHeight;

    PickerAdapter[] adapters;


    public PickerView(Context context) {
        super(context, null);
    }

    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_picker, this);
        bgView = findViewById(R.id.bgView);
        linear = (LinearLayout)findViewById(R.id.linear);
        dataList = new ArrayList<String[]>();
    }

    public void setDataSource(PickerViewDataSource dataSource) {
        this.dataSource = dataSource;

        totalColums = dataSource.numberOfColumns();
        selectedArr = new int[totalColums];

        visibleCells = dataSource.numberOfVisibleCell();
        cellHeight = dataSource.cellHeight();
        bgView.getLayoutParams().height = cellHeight;
        setupListViews();
    }

    public void setDelegate(PickerViewDelegate delegate) {
        this.delegate = delegate;
    }

    protected void setupListViews() {

        adapters = new PickerAdapter[totalColums];
        dataList.clear();
        for(int i=0; i<totalColums; i++) {
            selectedArr[i] = 0;
            dataList.add(dataSource.dataSorceIn(i));
            final ListView listView = new ListView(getContext());
            listView.setVerticalScrollBarEnabled(false);
            //添加headview
            View headView = new View(getContext());
            ListView.LayoutParams headParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,cellHeight);
            headView.setLayoutParams(headParams);
            listView.addHeaderView(headView);
            //添加footview
            View footView = new View(getContext());
            ListView.LayoutParams footParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,cellHeight);
            footView.setLayoutParams(footParams);
            listView.addFooterView(footView);
            //创建适配器
            adapters[i] = createAdapter(listView, i);
            //创建监听器
            createListener(listView, i);
            //添加进linear
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            linear.addView(listView, layoutParams);
        }
    }

    protected PickerAdapter createAdapter(ListView listView, int i) {
        final int index = i;
        PickerAdapter adapter = new PickerAdapter(index);
        listView.setAdapter(adapter);
        return adapter;
    }

    protected void createListener(final ListView listView, int i) {
        final int index = i;
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.v("picker", ""+selectedArr[index]);
                    listView.setSelection(selectedArr[index]);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                    int h = getScrollY();//滚动距离

                    //在此进行你需要的操作//TODO
                    int cur = Math.round((float) h/(float) cellHeight);
                    if (cur != selectedArr[index]) {
                        selectedArr[index] = cur;
                        if (delegate != null) delegate.selectedArrChanged(index, selectedArr, dataList, adapters);
                        adapters[index].notifyDataSetChanged();
                    }
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    if (itemRecod != null) height += itemRecod.height;
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }

            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });
    }


    public String getSelectedStringInColumn(int column) {
        return dataList.get(column)[selectedArr[column]];
    }

    public String[] getSelectedStrings() {
        String[] strings = new String[totalColums];
        for (int i=0; i<totalColums; i++) {
            strings[i] = getSelectedStringInColumn(i);
        }
        return strings;
    }

    class PickerAdapter extends BaseAdapter {
        int index;

        public PickerAdapter(int index) {
            this.index = index;
        }

        @Override
        public int getCount() {
            return dataList.get(index).length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BaseViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_base, null);
                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,cellHeight);
                convertView.setLayoutParams(params);
                viewHolder = new BaseViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BaseViewHolder)convertView.getTag();
            }
            if (position == selectedArr[index]) viewHolder.setTitle(dataList.get(index)[position], getContext().getResources().getColor(selectedTextColor));
            else viewHolder.setTitle(dataList.get(index)[position], getContext().getResources().getColor(defaultTextColor));
            return convertView;
        }

        public void updateSingleRow(ListView listView, int position) {
            if (listView != null) {
                int start = listView.getFirstVisiblePosition();
                int last = listView.getLastVisiblePosition();
                if (position>=start && position<=last) {
                    getView(position, listView.getChildAt(position-start+1), listView);
                }
            }
        }
    }


}
