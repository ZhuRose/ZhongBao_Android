package com.edu.uestc.zhongbao_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.net.URL;

/**
 * Created by zhu on 17/4/4.
 */

public class ImageLoadManager {
    private static ImageLoadManager manager = null;
    public ImageLoader imageLoader;

    public static ImageLoadManager shareManager() {
        if (manager==null) manager = new ImageLoadManager();
        return manager;
    }

    public void setupImageLoader(Context context) {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);
    }

    private ImageLoadManager() {

    }

    public void displayImage(String uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .build();
        imageLoader.displayImage(uri, imageView, options);
    }

    public void loadImage(String uri, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .build();
        imageLoader.loadImage(uri, options, listener);
    }

    public String getCacheSize() throws Exception {
        long cacheSize = CleanMessageUtil.getFolderSize(imageLoader.getDiskCache().getDirectory());
        return CleanMessageUtil.getFormatSize(cacheSize);
    }

    public void clearCache() {
        imageLoader.clearDiskCache();
    }

}
