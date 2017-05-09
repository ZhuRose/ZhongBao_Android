package com.edu.uestc.zhongbao_android.utils.cache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.edu.uestc.zhongbao_android.model.BaseModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhu on 17/4/11.
 */

public class ZhuHttpWithCacheManager {

    public enum RequestType{
        GET,POST;
    }

    public interface RequestBlock {
        public void succeseeBlock(JsonElement object, int status);
        public void failedBlock(String errorInfo, int status);
    }

    private static ZhuHttpWithCacheManager manager = null;
    public static final String MAIN_URL = "http://119.23.25.36/";

    private OkHttpClient httpClient;
    private ZhuCacheManager cacheManager;
    private Gson gson;
    private Context context;

    public static ZhuHttpWithCacheManager shareManager(Context context) {
        if (manager==null) {
            synchronized (ZhuHttpWithCacheManager.class) {
                if (manager==null) {
                    manager = new ZhuHttpWithCacheManager(context);
                }
            }
        }
        return manager;
    }

    private ZhuHttpWithCacheManager() {

    }

    private ZhuHttpWithCacheManager(Context context) {
        this.context = context;
        httpClient = new OkHttpClient();
        cacheManager = ZhuCacheManager.getInstance(context);
        gson = new Gson();
    }

    public void getRequest(String url, RequestBlock block) {
        request(url, null, RequestType.GET, false, block);
    }

    public void getRequestCache(String url, RequestBlock block) {
        request(url, null, RequestType.GET, true, block);
    }

    public void postRequest(String url, Map<String, String> map, RequestBlock block) {
        request(url, map, RequestType.POST, false, block);
    }

    public void postRequestCache(String url, Map<String, String> map, RequestBlock block) {
        request(url, map, RequestType.POST, true, block);
    }

    private void request(final String url, Map<String, String> map, RequestType type, final boolean isCache, final RequestBlock block) {

        JsonElement cacheData = null;
        final String cacheKey = urlMapToString(url, map);
        if (isCache) {
            String cacheValue = cacheManager.readCache(cacheKey);
            cacheData = gson.fromJson(cacheValue, JsonElement.class);
        }

        if(!judgeConnect()) {
            if (cacheData!=null) block.succeseeBlock(cacheData, -1);
            else block.failedBlock("没有网络", -1);
            return;
        }

        okhttp3.Request request;
        switch (type) {
            case GET:
                request = new okhttp3.Request.Builder().url(MAIN_URL+url).build();
                break;
            default:
                request = new okhttp3.Request.Builder().url(MAIN_URL+url).post(createBody(map)).build();
                break;
        }
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                block.failedBlock(e.getLocalizedMessage(), -1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonElement data = handlerJson(response.body().string(), block);
                if (isCache) cacheManager.writeCache(cacheKey, gson.toJson(data));
            }
        });
    }

    private JsonElement handlerJson(String jsonStr, RequestBlock block) {
        try {
            BaseModel model = gson.fromJson(jsonStr, BaseModel.class);
            int status = Integer.parseInt(model.status);
            if (status == 0) block.succeseeBlock(model.data, status);
            else block.failedBlock(model.errorMsg, status);
            return model.data;
        } catch (Exception e) {
            block.failedBlock(e.getLocalizedMessage(), -1);
            return null;
        }

    }

    /**
     * 创造post form
     */
    private RequestBody createBody(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map == null) {
            return builder.build();
        }
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                value = URLEncoder.encode(entry.getValue(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            builder.addEncoded(key, value);
        }
        return builder.build();
    }

    /**
     * 判断网络
     */
    private boolean judgeConnect() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info!=null&&info.isConnected()) {
            //当前网络是连接的
            if (info.getState()==NetworkInfo.State.CONNECTED) {
                // 当前所连接的网络可用
                return true;
            }
        }
        return false;
    }

    /**
     *拼接网址
     */
    protected String urlMapToString(String url, Map<String, String> map) {
        if (map==null) return url;
        StringBuilder builder = new StringBuilder(url);
        builder.append('?');
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                value = URLEncoder.encode(entry.getValue(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            builder.append(key+"="+value+"&");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

}
