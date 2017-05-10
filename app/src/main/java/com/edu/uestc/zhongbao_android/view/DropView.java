package com.edu.uestc.zhongbao_android.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 17/4/6.
 */

public class DropView extends ConstraintLayout {

    protected DropViewDataSource dataSource;
    protected DropViewDelegate delegate;

    protected FrameLayout contentFrame;
    protected FrameLayout coverFrame;
    protected LinearLayout dropMenus;
    protected ListView dropList;
    protected ViewGroup.LayoutParams dropListLayoutParams;
    protected DropListAdapter adapter;

    public int curSelected;
    public int[] selectedArr;//当前选中结果
    protected int totalMenus;
    protected DropViewMenu[] menuArr;
    protected List<String[]> dataList;
    protected String[] titleArr;

    protected boolean showing;
    private boolean animating;


    public DropView(Context context) {
        super(context, null);
    }

    public DropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_drop, this);
        contentFrame = (FrameLayout)this.findViewById(R.id.frame_content);
        coverFrame = (FrameLayout)this.findViewById(R.id.frame_cover);
        dropMenus = (LinearLayout)this.findViewById(R.id.drop_menus);
        dropList = (ListView)this.findViewById(R.id.drop_list);
        dropListLayoutParams = dropList.getLayoutParams();
        dataList = new ArrayList<String[]>();
        showing = false;
        curSelected = -1;
        setupDropList();
        coverFrame.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (showing && !animating) {
                    if (curSelected>=0 && curSelected<totalMenus) {
                        DropViewMenu lastMenu = menuArr[curSelected];
                        lastMenu.setIsSelected(false);
                    }
                    stopAnimation();
                }
                return true;
            }
        });
    }

    public void reload() {
        dataList.clear();
        for (int i=0; i<totalMenus; i++) {
            titleArr[i] = dataSource.titleForMenu(i);
            dataList.add(dataSource.dataSorceIn(i));
            DropViewMenu menu = menuArr[i];
            menu.setIsSelected(false, titleArr[i]);
        }
    }

    public void setDelegate(DropViewDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDataSource(DropViewDataSource dataSource) {
        this.dataSource = dataSource;
        totalMenus = dataSource.numberOfMeuns();
        selectedArr = new int[totalMenus];
        titleArr = new String[totalMenus];
        menuArr = new DropViewMenu[totalMenus];
        dataList.clear();
        for (int i=0; i<totalMenus; i++) {
            selectedArr[i] = 0;
            titleArr[i] = dataSource.titleForMenu(i);
            dataList.add(dataSource.dataSorceIn(i));
            DropViewMenu menu = new DropViewMenu(getContext(), titleArr[i]);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            dropMenus.addView(menu, layoutParams);

            final int index = i;
            menu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropViewMenu menu = (DropViewMenu)v;
                    if (curSelected!=index && curSelected>=0 && curSelected<totalMenus) {//选择了另外一个
                        DropViewMenu lastMenu = menuArr[curSelected];
                        lastMenu.setIsSelected(false);
                    }
                    curSelected = index;
                    menu.setIsSelected(!menu.getIsSelected());
                    adapter.notifyDataSetChanged();
                    if (menu.getIsSelected()) beginAnimation();
                    else stopAnimation();
                    dropList.requestLayout();
                }
            });
            menuArr[i] = menu;
        }
    }

    protected void setupDropList() {
        dropList.setVerticalScrollBarEnabled(false);
        adapter = new DropListAdapter();
        dropList.setAdapter(adapter);
        dropList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!showing || animating || curSelected<0 || curSelected>=totalMenus) return;
                else {
                    selectedArr[curSelected] = position;
                    String title = dataList.get(curSelected)[position];
                    menuArr[curSelected].setIsSelected(false, title);
                    adapter.notifyDataSetChanged();
                    stopAnimation();
                }
                if (delegate != null) delegate.didSelectItemWith(selectedArr);
            }
        });
    }


    public void beginAnimation() {
        if (curSelected<0 || curSelected>=totalMenus) return;
        int count = dataList.get(curSelected).length;
        int height = count<5? (int)(56*Constant.getDensity(getContext())) * count : (int)(280*Constant.getDensity(getContext()));
        dropList.setVisibility(VISIBLE);
        coverFrame.setVisibility(VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0, height).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                dropListLayoutParams.height = value;
                dropList.requestLayout();
            }
        });
        animator.start();
        animating = true;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
                showing = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void stopAnimation() {
        if (curSelected<0 || curSelected>=totalMenus) return;
        int count = dataList.get(curSelected).length;
        int height = count<5? (int)(56*Constant.getDensity(getContext())) * count : (int)(280*Constant.getDensity(getContext()));
        ValueAnimator animator = ValueAnimator.ofInt(height, 0).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                dropListLayoutParams.height = value;
                dropList.requestLayout();
            }
        });
        animator.start();
        animating = true;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
                dropList.setVisibility(GONE);
                coverFrame.setVisibility(GONE);
                showing=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    class DropListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (curSelected<0 || curSelected>=totalMenus) return 0;
            else return dataList.get(curSelected).length;
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
                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                        (int)(56*Constant.getDensity(getContext())));
                convertView.setLayoutParams(params);
                viewHolder = new BaseViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BaseViewHolder)convertView.getTag();
            }
            if (curSelected>=0 && curSelected<totalMenus) {
                if (selectedArr[curSelected] == position)
                    viewHolder.setTitle(dataList.get(curSelected)[position], getResources().getColor(R.color.colorTheme));
                else
                    viewHolder.setTitle(dataList.get(curSelected)[position], getResources().getColor(R.color.colorText));
            }
            return convertView;
        }
    }

}
