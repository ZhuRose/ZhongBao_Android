package com.edu.uestc.zhongbao_android.controller.login.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.CountDownButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhu on 17/4/7.
 */

public class RegisterActivity extends BaseActivity {

    NetworkUtil networkUtil;
    NetworkUtil vertifyNetworkUtil;

    @BindView(R.id.userEdit)
    EditText userEdit;

    @BindView(R.id.pwdEdit)
    EditText pwdEdit;

    @BindView(R.id.pwdAgainEdit)
    EditText pwdAgainEdit;

    @BindView(R.id.vertifyEdit)
    EditText vertifyEdit;

    @BindView(R.id.registBtn)
    Button registButton;

    @BindView(R.id.agreeButton)
    CheckBox agreeButton;

    boolean isAgree;

    @OnTouch(R.id.contentLayout)
    public boolean touch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(userEdit.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(pwdEdit.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(pwdAgainEdit.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(vertifyEdit.getWindowToken(), 0);
        }
        return true;
    }

    @OnClick(R.id.registBtn)
    void registButtonClick(Button sender) {
        if (!String.valueOf(pwdEdit.getText()).equals(String.valueOf(pwdAgainEdit.getText()))) {
            BaseDialogFragment.showFailed(getSupportFragmentManager(), "两次密码不一致");
            return;
        }
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.registerNetwork(String.valueOf(userEdit.getText()), String.valueOf(pwdEdit.getText()), String.valueOf(vertifyEdit.getText()));
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.countDownBtn)
    public void countDownButtonClick(CountDownButton sender) {
        if (String.valueOf(userEdit.getText()).trim().isEmpty()) {
            BaseDialogFragment.showFailed(getSupportFragmentManager(), "请输入手机号码");
            return;
        }
        if (!isPhoneNumber(String.valueOf(userEdit.getText()).trim())) {
            BaseDialogFragment.showFailed(getSupportFragmentManager(), "手机号码格式不正确");
            return;
        }
        sender.beginCountDown();
        vertifyNetworkUtil.getVariNetwork(String.valueOf(userEdit.getText()).trim());
    }

    @Override
    protected void initData() {
        super.initData();
        isAgree = false;
        AndroidBug5497Workaround.assistActivity(mContext);

        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "注册成功");
                RegisterActivity.this.finishAfterTransition();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), errorInfo);
            }
        };

        vertifyNetworkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.showSuccess(getSupportFragmentManager(), "验证码已发送，请注意查收");
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
                if (userEdit.getText().length()>0 && pwdEdit.getText().length()>0 && pwdAgainEdit.getText().length()>0 && vertifyEdit.getText().length()>0 && isAgree) {
                    registButton.setEnabled(true);
                } else {
                    registButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        userEdit.addTextChangedListener(textWatcher);
        pwdEdit.addTextChangedListener(textWatcher);
        pwdAgainEdit.addTextChangedListener(textWatcher);
        vertifyEdit.addTextChangedListener(textWatcher);
        agreeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree = isChecked;
                if (userEdit.getText().length()>0 && pwdEdit.getText().length()>0 && pwdAgainEdit.getText().length()>0 && vertifyEdit.getText().length()>0 && isAgree) {
                    registButton.setEnabled(true);
                } else {
                    registButton.setEnabled(false);
                }
            }
        });
        registButton.setEnabled(false);
    }

    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseDialogFragment.dismissDialog();
    }
}
