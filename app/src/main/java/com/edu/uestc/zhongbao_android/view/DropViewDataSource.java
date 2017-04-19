package com.edu.uestc.zhongbao_android.view;

/**
 * Created by zhu on 17/4/6.
 */

public interface DropViewDataSource {
    public int numberOfMeuns();
    public String titleForMenu(int menuNum);
    public String[] dataSorceIn(int menuNum);
}
