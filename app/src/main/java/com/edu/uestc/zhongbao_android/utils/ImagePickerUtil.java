package com.edu.uestc.zhongbao_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.List;


/**
 * Created by zhu on 17/4/4.
 */

public class ImagePickerUtil implements ImageLoader {

    private ImagePicker imagePicker;

    public ImagePickerUtil(boolean showCamera , boolean crop) {

        //图片加载
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(this);   //设置图片加载器
        imagePicker.setShowCamera(showCamera);  //显示拍照按钮
        imagePicker.setCrop(crop);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    public ImagePickerUtil(boolean showCamera, int selectLimit) {
        //图片加载
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(this);   //设置图片加载器
        imagePicker.setShowCamera(showCamera);  //显示拍照按钮
        imagePicker.setSelectLimit(selectLimit);    //选中数量限制
    }

    public void pushToImagePicker(Activity context, int IMAGE_PICKER) {
        Intent intent = new Intent(context, ImageGridActivity.class);
        context.startActivityForResult(intent, IMAGE_PICKER);
    }


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//        BitmapFactory.Options op = new BitmapFactory.Options();
//        op.inJustDecodeBounds = true;
//        int xScale = op.outWidth / width;
//        int yScale = op.outHeight / height;
//        op.inSampleSize = xScale > yScale ? xScale : yScale;
//        op.inJustDecodeBounds = false;
//        Bitmap bt = BitmapFactory.decodeFile(path, op);
//        imageView.setImageBitmap(bt);
        ImageLoadManager.shareManager().displayImage("file:///"+path, imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
