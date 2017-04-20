package com.edu.uestc.zhongbao_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by zhu on 17/4/12.
 */

public class UserManager {
    private static UserManager manager = null;

    private Context context;
    private SharedPreferences preferences;

    private boolean hasLogin;

    private String headpic;
    private String token;
    private String birthday;
    private String nickname;
    private String gender;

    private String phoneNum;
    private String password;

    private String city;
    private String latitude;
    private String longtitude;

    private String headpicBase64;

    public static UserManager shareManager(Context context) {
        if (manager == null) {
            synchronized (UserManager.class) {
                if (manager == null) {
                    manager = new UserManager(context);
                }
            }
        }
        return manager;
    }

    private UserManager() {

    }

    private UserManager(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.hasLogin = preferences.getBoolean("hasLogin", false);
        Log.v("shared", ""+this.hasLogin);
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
        preferences.edit().putBoolean("hasLogin", hasLogin).commit();
    }

    public boolean getHasLogin() {
        return hasLogin;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
        preferences.edit().putString("headpic", headpic).commit();
    }

    public String getHeadpic() {
        if (headpic == null) headpic = preferences.getString("headpic", "");
        return headpic;
    }

    public void setToken(String token) {
        this.token = token;
        preferences.edit().putString("token", token).commit();
    }

    public String getToken() {
        if (token == null) token = preferences.getString("token", "");
        return token;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        preferences.edit().putString("birthday", birthday).commit();
    }

    public String getBirthday() {
        if (birthday == null) birthday = preferences.getString("birthday", "");
        return birthday;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        preferences.edit().putString("nickname", nickname).commit();
    }

    public String getNickname() {
        if (nickname == null) nickname = preferences.getString("nickname", "");
        return nickname;
    }

    public void setGender(String gender) {
        this.gender = gender;
        preferences.edit().putString("gender", gender).commit();
    }

    public String getGender() {
        if (gender == null) gender = preferences.getString("gender", "");
        return gender;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        preferences.edit().putString("phoneNum", phoneNum).commit();
    }

    public String getPhoneNum() {
        if (phoneNum == null) phoneNum = preferences.getString("phoneNum", "");
        return phoneNum;
    }

    public void setPassword(String password) {
        this.password = password;
        preferences.edit().putString("password", password).commit();
    }

    public String getPassword() {
        if (password == null) password = preferences.getString("password", "");
        return password;
    }

    public void setCity(String city) {
        this.city = city;
        preferences.edit().putString("city", city).commit();
    }

    public String getCity() {
        if (city == null) city = preferences.getString("city", "成都市");
        return city;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        preferences.edit().putString("latitude", latitude).commit();
    }

    public String getLatitude() {
        if (latitude == null) latitude = preferences.getString("latitude", "30.66667");
        return latitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
        preferences.edit().putString("longtitude", longtitude).commit();
    }

    public String getLongtitude() {
        if (longtitude == null) longtitude = preferences.getString("longtitude", "104.06667");
        return longtitude;
    }

    public void setHeadpicBase64(String headpicBase64) {
        this.headpicBase64 = headpicBase64;
        preferences.edit().putString("headpicBase64", headpicBase64).commit();
    }

    public String getHeadpicBase64() {
        if (headpicBase64 == null) headpicBase64 = preferences.getString("headpicBase64", "");
        return headpicBase64;
    }
}
