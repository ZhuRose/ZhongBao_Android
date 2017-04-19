package com.edu.uestc.zhongbao_android.controller.main.home.detail.all_comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseListActivity;
import com.edu.uestc.zhongbao_android.holder.CommentViewHolder;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/8.
 */

public class AllCommentsActivity extends BaseListActivity {

    View header;
    CommentsAdapter adapter;

    @Override
    protected void initView() {
        super.initView();

        navi.setTitle("全部评论");

        header = LayoutInflater.from(mContext).inflate(R.layout.header_list_comments, null);
        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (84 * Constant.getDensity(mContext)));
        header.setLayoutParams(layoutParams);
        list.addHeaderView(header);

        adapter = new CommentsAdapter();
        list.setAdapter(adapter);
    }

    class CommentsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
            CommentViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_comment, parent, false);
                viewHolder = new CommentViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (CommentViewHolder)convertView.getTag();
            }
            return convertView;
        }
    }
}
