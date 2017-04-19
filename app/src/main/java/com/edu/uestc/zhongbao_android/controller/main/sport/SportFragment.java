package com.edu.uestc.zhongbao_android.controller.main.sport;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.sport.detail.SportDetailActivity;
import com.edu.uestc.zhongbao_android.holder.MessageViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportViewHolder;

import butterknife.BindView;

/**
 * Created by zhu on 17/3/21.
 */

public class SportFragment extends BaseFragment {

    @BindView(R.id.list)
    ListView listView;

    SportAdapter adapter;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_sports;
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new SportAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, SportDetailActivity.class));
            }
        });
    }

    class SportAdapter extends BaseAdapter {
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
            SportViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_sport, null);
                holder = new SportViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SportViewHolder) convertView.getTag();
            }
            if (position<=9) holder.setViews(position);
            else holder.setViews(3);
            convertView.requestLayout();
            return convertView;
        }
    }
}
