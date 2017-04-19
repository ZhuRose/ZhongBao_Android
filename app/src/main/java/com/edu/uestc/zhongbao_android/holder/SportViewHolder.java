package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.view.ShowImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/11.
 */

public class SportViewHolder {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.contentView)
    TextView contentView;

    @BindView(R.id.timeView)
    TextView timeView;

    @BindView(R.id.imgsBgView)
    ShowImageView imgsBgView;

    @BindView(R.id.commentCountView)
    TextView commentCountView;

    @BindView(R.id.addressView)
    TextView addressView;

    public SportViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setViews(int num) {
        imgsBgView.imgUrlList.clear();
        for (int i=0; i<num; i++) {
            imgsBgView.imgUrlList.add("hehe"+i);
        }
        imgsBgView.layoutImg();
    }
}
