package com.edu.uestc.zhongbao_android.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zhu on 17/4/12.
 */

public class DateUtil {

    private static String mYear; // 当前年
    private static String mMonth; // 月
    private static String mDay;
    private static String mWay;


    /**
     * 获取当前日期几月几号
     */
    public static String getDateString() {


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        if(Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(Integer.parseInt(mMonth)))){
            mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(Integer.parseInt(mMonth))));
        }
        return mMonth + "月" + mDay + "日";
    }


    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String StringData() {


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        if(Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(Integer.parseInt(mMonth)))){
            mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(Integer.parseInt(mMonth))));
        }
        return mYear + "-" + (mMonth.length()==1?"0"+mMonth:mMonth) + "-" + (mDay.length()==1?"0"+mDay:mDay);
    }


    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {


            c.setTime(format.parse(time));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "周六";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "周日";
        }
        return Week;
    }


    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getSevendate() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));


        for (int i = 0; i < 7; i++) {
            mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            if(Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1))){
                mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1)));
            }
            String date = mMonth + "月" + mDay + "日";
            dates.add(date);
        }
        return dates;
    }


    /**
     * 获取今天往后一周的集合
     */
    public static List<String> get7week() {
        String week = "";
        List<String> weeksList = new ArrayList<String>();
        List<String> dateList = get7date();
        for (String s : dateList) {
            if (s.equals(StringData())) {
                week = "今天";
            } else {
                week = getWeek(s);
            }
            weeksList.add(week);
        }
        return weeksList;
    }


    public static List<String> get7date() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        String date = sim.format(c.getTime());
        dates.add(date);
        for (int i = 0; i < 6; i++) {
            c.add(java.util.Calendar.DAY_OF_MONTH, 1);
            date = sim.format(c.getTime());
            dates.add(date);
        }
        return dates;
    }

    /**得到当年当月的最大日期**/
    public static int MaxDayFromDay_OF_MONTH(int year,int month){
        Calendar time=Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }
}
