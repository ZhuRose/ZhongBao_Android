package com.edu.uestc.zhongbao_android.controller.main.me.my_locus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.holder.LocusViewHolder;
import com.edu.uestc.zhongbao_android.holder.OrderViewHolder;

/**
 * Created by zhu on 17/4/9.
 */

public class MyLocusActivity extends BaseListActivity {

    LocusAdapter adapter;

    @Override
    protected void initView() {
        super.initView();
        navi.setTitle("我的轨迹");

        adapter = new LocusAdapter();
        list.setAdapter(adapter);
    }

    class LocusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 12;
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
            LocusViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_locus, null);
                holder = new LocusViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (LocusViewHolder) convertView.getTag();
            }
            return convertView;
        }
    }
}
