package com.edu.uestc.zhongbao_android.controller.main.home.chose_city;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.holder.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/8.
 */

public class ChoseCityActivity extends BaseListActivity {

    ChoseCityAdapter adapter;

    public static final int ChoseCityTag = 101;
    String[] citys = {"成都市", "重庆市", "北京"};

    @Override
    protected void initView() {
        super.initView();
        navi.setTitle("选择城市");
        adapter = new ChoseCityAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("city", citys[position]);
                setResult(ChoseCityTag, intent);
                finishAfterTransition();
            }
        });
    }

    class ChoseCityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return citys.length;
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_base, null);
                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,(int)(56* Constant.getDensity(mContext)));
                convertView.setLayoutParams(params);
                viewHolder = new BaseViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BaseViewHolder)convertView.getTag();
            }
            viewHolder.setTitle(citys[position]);
            return convertView;
        }
    }
}
