package com.edu.uestc.zhongbao_android.controller.main.me.my_collection;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.HomeDetailActivity;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;

/**
 * Created by zhu on 17/4/9.
 */

public class MyCollectionActivity extends BaseListActivity {

    HomeAdapter adapter;

    @Override
    protected void initView() {
        super.initView();
        navi.setTitle("我喜欢的");
        adapter = new HomeAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, HomeDetailActivity.class));
            }
        });
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
            HomeViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_home, null);
                holder = new HomeViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (HomeViewHolder) convertView.getTag();
            }
            holder.setViews("", "标题", 3.5f, "地点", "100");
            return convertView;
        }
    }
}
