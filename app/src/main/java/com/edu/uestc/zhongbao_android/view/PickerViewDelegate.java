package com.edu.uestc.zhongbao_android.view;


import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhu on 17/4/3.
 */

public interface PickerViewDelegate {
    public void selectedArrChanged(int index, int[] selectedArr, List<String[]> dataList, BaseAdapter[] adapters);
}
