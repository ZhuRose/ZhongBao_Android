package com.edu.uestc.zhongbao_android.controller.main.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.session_list.SessionListActivity;
import com.edu.uestc.zhongbao_android.holder.GridViewHolder;

import butterknife.BindView;

/**
 * Created by zhu on 17/3/26.
 */

public class HomePagerFragment extends BaseFragment {

    @BindView(R.id.gridView)
    GridView gridView;

    int[] iconArr = {R.drawable.badminton, R.drawable.pinpang, R.drawable.billiards, R.drawable.basketball,
            R.drawable.fitting, R.drawable.soccer, R.drawable.dancing, R.drawable.swiming};
    String[] strArr = {"羽毛球", "乒乓球", "台球", "篮球", "健身", "足球", "舞蹈", "游泳"};

    @Override
    protected int loadLayout() {
        return R.layout.fragment_pager_home;
    }

    @Override
    protected void initView() {
        super.initView();
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 8;
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
                GridViewHolder holder;
                if (convertView==null) {
                    convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_grid, null);
                    holder = new GridViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (GridViewHolder) convertView.getTag();
                }
                holder.setViews(iconArr[position], strArr[position]);
                return convertView;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SessionListActivity.class);
                intent.putExtra("sport",position+1);
                startActivity(intent);
            }
        });
    }
}
