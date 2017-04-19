package com.edu.uestc.zhongbao_android.view;

import android.widget.ImageView;

/**
 * Created by zhu on 17/3/12.
 */

public interface CycleScrollViewDataSource {
    public int numberOfPages();
    public void pageAt(int index, ImageView view);
}
