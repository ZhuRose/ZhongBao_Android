package com.edu.uestc.zhongbao_android.controller.main.publish;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.base.BasePopupWindow;
import com.edu.uestc.zhongbao_android.utils.Base64Util;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.ImagePickerUtil;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.view.PickerImageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/3/21.
 */

public class PublishActivity extends BaseActivity {

    public static int IMAGE_PICKER = 500;
    private ImagePickerUtil imagePickerUtil;
    JsonArray jsonArray;
    NetworkUtil networkUtil;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyBoard();
        }
        return true;
    }

    @OnClick(R.id.cancleBtn)
    void cancleButtonClick(View sender) {
        hideKeyBoard();
        mContext.finishAfterTransition();
    }

    @OnClick(R.id.submitBtn)
    void submitButtonClick(View sender) {
        hideKeyBoard();
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        int limit = (int)(100*Constant.getDensity(mContext));
        for (String path : pickerImageView.imgPathList) {
            Bitmap bitmap = Base64Util.bestZoom(path, limit);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("picture",Base64Util.bitmapToBase64(bitmap));
            jsonArray.add(jsonObject);
        }
        networkUtil.publishSportsCityNetwork(String.valueOf(contentEdit.getText()),jsonArray.toString(),"");
    }

    @BindView(R.id.contentEdit)
    EditText contentEdit;

    @BindView(R.id.remainCountView)
    TextView remainCountView;

    @BindView(R.id.pickerImageView)
    PickerImageView pickerImageView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setEnterTransition(new Slide());
        getWindow().setReturnTransition(new Slide());
        jsonArray = new JsonArray();
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "发布成功");
                PublishActivity.this.finishAfterTransition();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), errorInfo);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remainCountView.setText(""+(110-s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pickerImageView.photoAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                imagePickerUtil = new ImagePickerUtil(false, 9-pickerImageView.imgPathList.size());
                imagePickerUtil.pushToImagePicker(mContext, IMAGE_PICKER);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem item : images) {
                    pickerImageView.imgPathList.add(item.path);
                }
                pickerImageView.layoutImg();

            }
        }
    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(contentEdit.getWindowToken(), 0);

    }
}
