package com.edu.uestc.zhongbao_android.controller.main.me.info;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.application.MainApplication;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.main.me.info.picker.PickerActivity;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.ImagePickerUtil;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.view.InputView;
import com.edu.uestc.zhongbao_android.view.InputViewDelegate;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/1.
 */

public class InfoActivity extends BaseActivity {
    public static int IMAGE_PICKER = 500;
    private ImagePickerUtil imagePickerUtil;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            editView.hideKeyBoard();
        }
        return true;
    }

    @BindView(R.id.editView)
    InputView editView;

    @BindView(R.id.headView)
    ImageView headView;

    @BindView(R.id.nameView)
    TextView nameView;

    @BindView(R.id.sexView)
    TextView sexView;

    @BindView(R.id.birthdayView)
    TextView birthdayView;

    @BindView(R.id.mainView)
    View mainView;


    @OnClick({R.id.infoView1, R.id.infoView2, R.id.infoView3, R.id.infoView4})
    public void infoViewOnClick(View view) {
        switch (view.getId()) {
            case R.id.infoView1:
                editView.hideKeyBoard();
                imagePickerUtil = new ImagePickerUtil(true, true);
                imagePickerUtil.pushToImagePicker(mContext, IMAGE_PICKER);
                break;
            case R.id.infoView2:
                editView.openKeyBoard(view);
                editView.setEditText(nameView.getText().toString(), "请输入昵称");
                break;
            default:
                editView.hideKeyBoard();
                Intent intent = new Intent(mContext, PickerActivity.class);
                intent.putExtra("type", view.getId()==R.id.infoView3?PickerActivity.Type_Sex :PickerActivity.Type_Birthday);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext, mainView, "share");
                startActivity(intent, compat.toBundle());
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem item = images.get(0);
                ImageLoadManager.shareManager().displayImage("file:///"+item.path, headView);
            }
        }
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_info_me;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);
        SoftKeyboardUtil.observeSoftKeyboard(mContext, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                Log.v("info", "keyboard"+softKeybardHeight+visible);
                if (!visible) {
                    editView.loseFocus();
                    editView.setVisibility(View.GONE);
                } else {
                    editView.setVisibility(View.VISIBLE);
                    editView.getFocus();
                }

            }
        });
        MainApplication application = (MainApplication)getApplication();
        application.setHandler(new InfoHandle());
    }

    @Override
    protected void initView() {
        super.initView();
        editView.setDelegate(new InputViewDelegate() {
            @Override
            public void getInputContent(String content, String tag) {
                nameView.setText(content);
            }
        });
        ConstraintLayout.LayoutParams params =  (ConstraintLayout.LayoutParams)mainView.getLayoutParams();
        params.setMargins(0, 0, 0, Constant.getVirtualBarHeight(mContext));
        mainView.requestLayout();
    }

    class InfoHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String[] strings = msg.getData().getStringArray("results");
            switch (msg.what) {
                case 100://生日
                    if (strings.length == 3) {
                        birthdayView.setText(strings[0]+"-"+strings[1]+"-"+strings[2]);
                    }
                    break;
                case 101://性别
                    if (strings.length == 1) {
                        sexView.setText(strings[0]);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
