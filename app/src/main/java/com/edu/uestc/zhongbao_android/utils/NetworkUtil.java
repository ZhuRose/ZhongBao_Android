package com.edu.uestc.zhongbao_android.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.model.HomeModel;
import com.edu.uestc.zhongbao_android.model.MessageModel;
import com.edu.uestc.zhongbao_android.model.SessionDetailModel;
import com.edu.uestc.zhongbao_android.model.UserModel;
import com.edu.uestc.zhongbao_android.utils.cache.ZhuHttpWithCacheManager;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhu on 17/4/12.
 */

public abstract class NetworkUtil {

    public abstract void successNetwork(Object object, String tag);
    public abstract void failedNetwork(String errorInfo, String tag);

    static final String LOGIN_SUFFIX = "/user/login/";
    static final String HOME_SUFFIX = "/home/";
    static final String MESSAGE_SUFFIX = "/news/";
    static final String SESSION_DETAIL_SUFFIX = "/detail/";

    Activity activity;
    String tag;

    public NetworkUtil(Activity activity) {
        this(activity, "Default");
    }

    public NetworkUtil(Activity activity, String tag) {
        this.activity = activity;
        this.tag = tag;
    }

    public void loginNetwork(String phoneNum, String pwd) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", phoneNum);
        map.put("password", pwd);
        ZhuHttpWithCacheManager.shareManager(activity).postRequest(LOGIN_SUFFIX, map, new ZhuHttpWithCacheManager.RequestBlock() {
            @Override
            public void succeseeBlock(JsonElement object, int status) {
                final UserModel model = ZhuHttpWithCacheManager.shareManager(activity).fromJson(object, UserModel.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successNetwork(model, tag);
                    }
                });
            }

            @Override
            public void failedBlock(final String errorInfo, int status) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        failedNetwork(errorInfo, tag);
                    }
                });
            }
        });
    }

    public void homeNetwork() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("city", UserManager.shareManager(activity).getCity());
        map.put("latitude", UserManager.shareManager(activity).getLatitude());
        map.put("longtitude", UserManager.shareManager(activity).getLongtitude());
        ZhuHttpWithCacheManager.shareManager(activity).postRequestCache(HOME_SUFFIX, map, new ZhuHttpWithCacheManager.RequestBlock() {
            @Override
            public void succeseeBlock(JsonElement object, int status) {
                final HomeModel model = ZhuHttpWithCacheManager.shareManager(activity).fromJson(object, HomeModel.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successNetwork(model, tag);
                    }
                });
            }

            @Override
            public void failedBlock(final String errorInfo, int status) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        failedNetwork(errorInfo, tag);
                    }
                });
            }
        });
    }

    public void messageNetwork(String page) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page);
        ZhuHttpWithCacheManager.shareManager(activity).postRequestCache(MESSAGE_SUFFIX, map, new ZhuHttpWithCacheManager.RequestBlock() {
            @Override
            public void succeseeBlock(JsonElement object, int status) {
                final MessageModel model = ZhuHttpWithCacheManager.shareManager(activity).fromJson(object, MessageModel.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successNetwork(model, tag);
                    }
                });
            }

            @Override
            public void failedBlock(final String errorInfo, int status) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        failedNetwork(errorInfo, tag);
                    }
                });
            }
        });
    }

    public void sessionDetailNetwork(String uuid) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uuid", uuid);
        if (UserManager.shareManager(activity).getHasLogin()) map.put("token", UserManager.shareManager(activity).getToken());
        ZhuHttpWithCacheManager.shareManager(activity).postRequest(SESSION_DETAIL_SUFFIX, map, new ZhuHttpWithCacheManager.RequestBlock() {
            @Override
            public void succeseeBlock(JsonElement object, int status) {
                final SessionDetailModel model = ZhuHttpWithCacheManager.shareManager(activity).fromJson(object, SessionDetailModel.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successNetwork(model, tag);
                    }
                });
            }

            @Override
            public void failedBlock(final String errorInfo, int status) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        failedNetwork(errorInfo, tag);
                    }
                });
            }
        });
    }


}