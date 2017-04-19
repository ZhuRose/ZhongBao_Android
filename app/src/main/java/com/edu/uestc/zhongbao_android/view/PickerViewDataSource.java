package com.edu.uestc.zhongbao_android.view;

import butterknife.Optional;

/**
 * Created by zhu on 17/4/2.
 */

public interface PickerViewDataSource {
    public int numberOfColumns();
    public String[] dataSorceIn(int column);
    public int numberOfVisibleCell();
    public int cellHeight();
}
