package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.model.PictureModel;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.view.ShowImageView;

import java.util.List;

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

    public void setPictures(List<PictureModel> pictures) {
        imgsBgView.imgUrlList.clear();
        for (int i=0; i<pictures.size(); i++) {
            imgsBgView.imgUrlList.add(pictures.get(i).picture);
        }
        imgsBgView.layoutImg();
    }

    public void setViews(String icon, String title, String content, String time, String count, String address, List<PictureModel> pictures) {
        ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+icon, iconView);
        titleView.setText(title);
        contentView.setText(content);
        timeView.setText(time);
        commentCountView.setText(count);
        addressView.setText(address);
        setPictures(pictures);
    }
}
