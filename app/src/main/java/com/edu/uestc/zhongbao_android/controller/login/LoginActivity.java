package com.edu.uestc.zhongbao_android.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.base.BaseSwipeActivity;
import com.edu.uestc.zhongbao_android.controller.login.register.RegisterActivity;
import com.edu.uestc.zhongbao_android.model.UserModel;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.utils.UserManager;
import com.edu.uestc.zhongbao_android.utils.cache.ZhuHttpWithCacheManager;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/1.
 */

public class LoginActivity extends BaseActivity {

    NetworkUtil networkUtil;

    @BindView(R.id.userEdit)
    EditText userEdit;

    @BindView(R.id.pwdEdit)
    EditText pwdEdit;

    @BindView(R.id.loginBtn)
    Button loginBtn;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(userEdit.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(pwdEdit.getWindowToken(), 0);
        }
        return true;
    }

    @OnClick(R.id.loginBtn)
    void loginButtonClick(Button sender) {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.loginNetwork(String.valueOf(userEdit.getText()), String.valueOf(pwdEdit.getText()));
    }

    @OnClick(R.id.registButton)
    void registerButtonClick(Button sender) {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();
        AndroidBug5497Workaround.assistActivity(mContext);

        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                UserModel model = (UserModel)object;
                UserManager.shareManager(mContext).setHasLogin(true);
                UserManager.shareManager(mContext).setHeadpic(model.headpic);
                UserManager.shareManager(mContext).setToken(model.token);
                UserManager.shareManager(mContext).setBirthday(model.birthday);
                UserManager.shareManager(mContext).setNickname(model.nickname);
                UserManager.shareManager(mContext).setGender(model.gender);
                UserManager.shareManager(mContext).setPhoneNum(String.valueOf(userEdit.getText()));
                UserManager.shareManager(mContext).setPassword(String.valueOf(pwdEdit.getText()));
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "登录成功");
                LoginActivity.this.finishAfterTransition();
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
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userEdit.getText().length()>0 && pwdEdit.getText().length()>0) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        userEdit.addTextChangedListener(textWatcher);
        pwdEdit.addTextChangedListener(textWatcher);
        loginBtn.setEnabled(false);
        userEdit.setText("17713574326");
        pwdEdit.setText("123456");
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseDialogFragment.dismissDialog();
    }
}
