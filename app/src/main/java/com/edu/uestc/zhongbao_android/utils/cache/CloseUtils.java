package com.edu.uestc.zhongbao_android.utils.cache;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhu on 17/4/11.
 */

public final class CloseUtils {
    private CloseUtils(){

    }
    /**
     * 关闭Closeable对象
     */
    public static void closeCloseable(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
