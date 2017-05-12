package com.edu.uestc.zhongbao_android.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhu on 17/4/9.
 */

public class LocusViewHolder {

    @BindView(R.id.iconView)
    ImageView iconView;

    @BindView(R.id.timeView)
    TextView timeView;

    @BindView(R.id.sportView)
    TextView sportView;

    @BindView(R.id.sessionView)
    TextView sessionView;

    Map<String, Integer> iconMap;

    public LocusViewHolder(View view) {
        ButterKnife.bind(this, view);
        iconMap = new HashMap<String, Integer>();
        iconMap.put("羽毛球", R.drawable.badminton);
        iconMap.put("乒乓球", R.drawable.pinpang);
        iconMap.put("台球", R.drawable.billiards);
        iconMap.put("篮球", R.drawable.basketball);
        iconMap.put("健身", R.drawable.fitting);
        iconMap.put("足球", R.drawable.soccer);
        iconMap.put("舞蹈", R.drawable.dancing);
        iconMap.put("游泳", R.drawable.swiming);


    }

    public void setViews(String time, String sport, String session) {
        if (iconMap.get(sport)!=null) {
            iconView.setImageResource(iconMap.get(sport));
        }
        timeView.setText(time);
        sportView.setText(sport);
        sessionView.setText(session);
    }
}
