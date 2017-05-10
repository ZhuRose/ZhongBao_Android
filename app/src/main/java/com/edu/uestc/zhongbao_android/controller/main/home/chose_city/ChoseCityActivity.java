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
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.holder.BaseViewHolder;
import com.edu.uestc.zhongbao_android.model.CityListModel;
import com.edu.uestc.zhongbao_android.model.NameModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 17/4/8.
 */

public class ChoseCityActivity extends BaseListActivity {

    ChoseCityAdapter adapter;

    public static final int ChoseCityTag = 101;
    NetworkUtil networkUtil;
    List<NameModel> dataSource;

    @Override
    protected void initData() {
        super.initData();
        dataSource = new ArrayList<NameModel>();
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                CityListModel model = (CityListModel)object;
                dataSource = model.result;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {

            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        navi.setTitle("选择城市");
        adapter = new ChoseCityAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NameModel model = dataSource.get(position);
                Intent intent = new Intent();
                intent.putExtra("city", model.name);
                setResult(ChoseCityTag, intent);
                finishAfterTransition();
            }
        });

        getResponse();
    }

    protected void getResponse() {
        networkUtil.getCityListNetwork();
    }

    class ChoseCityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataSource.size();
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
            NameModel model = dataSource.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_base, null);
                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,(int)(56* Constant.getDensity(mContext)));
                convertView.setLayoutParams(params);
                viewHolder = new BaseViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (BaseViewHolder)convertView.getTag();
            }
            viewHolder.setTitle(model.name);
            return convertView;
        }
    }
}
