package com.edu.uestc.zhongbao_android.controller.main.sport.detail;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.all_comments.AllCommentsActivity;
import com.edu.uestc.zhongbao_android.holder.CommentViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportCommentViewHolder;
import com.edu.uestc.zhongbao_android.holder.SportViewHolder;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.view.InputView;
import com.edu.uestc.zhongbao_android.view.InputViewDelegate;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/11.
 */

public class SportDetailActivity extends BaseActivity {

    View header;
    SportCommentAdapter adapter;

    View sportCell;
    SportViewHolder sportViewHolder;

    @BindView(R.id.list)
    ListView list;

    @BindView(R.id.inputView)
    InputView inputView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_detail_sport;
    }

    @Override
    protected void initView() {
        super.initView();

        header = LayoutInflater.from(mContext).inflate(R.layout.header_list_detail_sport, null);
        sportCell = header.findViewById(R.id.sportCell);
        sportViewHolder = new SportViewHolder(sportCell);
        sportViewHolder.setViews(3);

        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(layoutParams);
        list.addHeaderView(header);

        adapter = new SportCommentAdapter();
        list.setAdapter(adapter);

        inputView.setEditText("", "请输入评论");
        inputView.setDelegate(new InputViewDelegate() {
            @Override
            public void getInputContent(String content, String tag) {
                inputView.hideKeyBoard();
                inputView.setEditText("", "请输入评论");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
        SoftKeyboardUtil.observeSoftKeyboard(mContext, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                Log.v("info", "keyboard"+softKeybardHeight+visible);
                ConstraintLayout.LayoutParams params =  (ConstraintLayout.LayoutParams)inputView.getLayoutParams();
                if (!visible) {
                    params.setMargins(0, 0, 0, Constant.getVirtualBarHeight(mContext));
                } else {
                    params.setMargins(0, 0, 0, 0);
                }
                inputView.requestLayout();

            }
        });
    }

    class SportCommentAdapter extends BaseAdapter {

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
            SportCommentViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_comment_sport, parent, false);
                viewHolder = new SportCommentViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SportCommentViewHolder)convertView.getTag();
            }
            return convertView;
        }
    }
}
