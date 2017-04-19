package com.edu.uestc.zhongbao_android.utils;

import android.content.Context;
import android.widget.BaseAdapter;


import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.view.PickerViewDataSource;
import com.edu.uestc.zhongbao_android.view.PickerViewDelegate;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zhu on 17/4/3.
 */

public class PickerDateUtil implements PickerViewDataSource, PickerViewDelegate{
    private Context context;

    public PickerDateUtil(Context context) {
        this.context = context;
    }

    @Override
    public int numberOfColumns() {
        return 3;
    }

    @Override
    public String[] dataSorceIn(int column) {
        String [] strings;
        switch (column) {
            case 0:
                strings = getHundredYears();
                break;
            case 1:
                strings = getAllMonths();
                break;
            default:
                strings = getAllDays(getDaysCount());
                break;
        }
        return strings;
    }

    @Override
    public int numberOfVisibleCell() {
        return 3;
    }

    @Override
    public int cellHeight() {
        return (int)(60* Constant.getDensity(context));
    }

    private int getDaysCount() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return getDaysCount(year, 1);
    }

    private int getDaysCount(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//先指定年份
        calendar.set(Calendar.MONTH, month - 1);//再指定月份 Java月份从0开始算
        int daysCountOfMonth = calendar.getActualMaximum(Calendar.DATE);//获取指定年份中指定月份有几天
        return daysCountOfMonth;
    }

    private String[] getHundredYears() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String[] years = new String[100];
        for (int i = 0; i<100; i++) {
            years[i] = "" + (year - i);
        }
        return years;
    }

    private String[] getAllMonths() {
        String[] months = new String[12];
        for (int i= 0; i<12; i++) {
            months[i] = ""+(i+1);
        }
        return months;
    }

    private String[] getAllDays(int day) {
        String[] days = new String[day];
        for (int i= 0; i<day; i++) {
            days[i] = ""+(i+1);
        }
        return days;
    }

    @Override
    public void selectedArrChanged(int index, int[] selectedArr, List<String[]> dataList, BaseAdapter[] adapters) {
        if (index != 2) {//年月变了
            int curYear = Integer.parseInt(dataList.get(0)[selectedArr[0]]);
            int curMonth = Integer.parseInt(dataList.get(1)[selectedArr[1]]);
            int daysCount = getDaysCount(curYear, curMonth);
            if (daysCount != dataList.get(2).length) {//天数不同
                dataList.remove(2);
                dataList.add(getAllDays(daysCount));
            }

        }
    }
}
