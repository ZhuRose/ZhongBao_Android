package com.edu.uestc.zhongbao_android.utils.cache;

/**
 * Created by zhu on 17/4/11.
 */

public interface Cache {
    String get(final String key);
    void put(final String key, final String value);
    boolean remove(final String key);
}
