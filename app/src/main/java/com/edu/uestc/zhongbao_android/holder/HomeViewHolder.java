package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.view.StarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/25.
 */

public class HomeViewHolder {
    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.titleView)
    TextView titleView;

    @BindView(R.id.starView)
    StarView starView;

    @BindView(R.id.scoreView)
    TextView scoreView;

    @BindView(R.id.siteView)
    TextView siteView;

    @BindView(R.id.priceView)
    TextView priceView;

    public HomeViewHolder(View view) {
        ButterKnife.bind(this,view);
    }

    public void setViews(String iconStr, String title, float score, String site, String price) {
        ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+iconStr, iconView);
        titleView.setText(title);
        starView.setScore(score);
        scoreView.setText(score + "分");
        siteView.setText(site);
        priceView.setText(price);
    }
}
