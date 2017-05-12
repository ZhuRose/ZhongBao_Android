package com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.order_detail;

import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.model.OrderDetailModel;
import com.edu.uestc.zhongbao_android.model.OrderModel;
import com.edu.uestc.zhongbao_android.model.SportTimeModel;
import com.edu.uestc.zhongbao_android.utils.AndroidBug5497Workaround;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.utils.SoftKeyboardUtil;
import com.edu.uestc.zhongbao_android.utils.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/4/10.
 */

public class OrderDetailActivity extends BaseActivity {

    String uuid;
    int type;
    OrderDetailModel model;
    NetworkUtil networkUtil;
    Map<String, Integer> iconMap;

    @BindView(R.id.bottomView)
    ConstraintLayout bottomView;

    @BindView(R.id.priceView)
    TextView priceView;

    @BindView(R.id.sessionIconView)
    ImageView sessionIconView;
    @BindView(R.id.sessionNameView)
    TextView sessionNameView;
    @BindView(R.id.timeView)
    TextView timeView;

    @BindView(R.id.timeListView)
    TextView timeListView;
    @BindView(R.id.numberListView)
    TextView numberListView;
    @BindView(R.id.priceListView)
    TextView priceListView;

    @BindView(R.id.orderText)
    TextView orderText;
    @BindView(R.id.pwdText)
    TextView pwdText;

    @BindView(R.id.phoneEdit)
    EditText phoneEdit;
    @BindView(R.id.contactEdit)
    EditText contactEdit;

    @BindView(R.id.paymentBtn)
    Button paymentBtn;

    @OnClick(R.id.paymentBtn)
    void paymentBtnClick(View sender) {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
    }

    @Override
    protected int loadLayout() {
        return R.layout.activity_detail_order;
    }

    @Override
    protected void initData() {
        super.initData();
        uuid = getIntent().getStringExtra("uuid");
        type = getIntent().getIntExtra("status", 0);
        iconMap = new HashMap<String, Integer>();
        iconMap.put("羽毛球", R.drawable.badminton);
        iconMap.put("乒乓球", R.drawable.pinpang);
        iconMap.put("台球", R.drawable.billiards);
        iconMap.put("篮球", R.drawable.basketball);
        iconMap.put("健身", R.drawable.fitting);
        iconMap.put("足球", R.drawable.soccer);
        iconMap.put("舞蹈", R.drawable.dancing);
        iconMap.put("游泳", R.drawable.swiming);
        AndroidBug5497Workaround.assistActivity(mContext);
        SoftKeyboardUtil.observeSoftKeyboard(mContext, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
                Log.v("info", "keyboard"+softKeybardHeight+visible);
                ConstraintLayout.LayoutParams params =  (ConstraintLayout.LayoutParams)bottomView.getLayoutParams();
                if (!visible) {
                    params.setMargins(0, 0, 0, Constant.getVirtualBarHeight(mContext));
                } else {
                    params.setMargins(0, 0, 0, 0);
                }
                bottomView.requestLayout();

            }
        });
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                BaseDialogFragment.dismissDialog();
                model = (OrderDetailModel) object;
                setViews();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                BaseDialogFragment.showFailed(getSupportFragmentManager(), "网络错误");
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        phoneEdit.setText(UserManager.shareManager(mContext).getPhoneNum());
        contactEdit.setText(UserManager.shareManager(mContext).getNickname());
        if (type == 0) paymentBtn.setText("立即付款");
        else paymentBtn.setText("申请退款");
        getResponse();
    }

    public void getResponse() {
        BaseDialogFragment.showLoading(getSupportFragmentManager());
        networkUtil.orderDetailNetwork(uuid);
    }

    protected void setViews() {
        if (iconMap.get(model.sport) != null) {
            sessionIconView.setImageResource(iconMap.get(model.sport));
        }
        priceView.setText(model.price);
        sessionNameView.setText(model.stadium);
        timeView.setText(model.date);
        StringBuilder timeSb = new StringBuilder();
        StringBuilder numberSb = new StringBuilder();
        StringBuilder priceSb = new StringBuilder();
        for (SportTimeModel sModel : model.sport_time) {
            timeSb.append(sModel.time+"\n");
            numberSb.append(sModel.place+"\n");
            priceSb.append("¥ "+sModel.fee+"\n");
        }
        timeSb.deleteCharAt(timeSb.length()-1);
        numberSb.deleteCharAt(numberSb.length()-1);
        priceSb.deleteCharAt(priceSb.length()-1);
        timeListView.setText(String.valueOf(timeSb));
        numberListView.setText(String.valueOf(numberSb));
        priceListView.setText(String.valueOf(priceSb));
        orderText.setText(model.order_number);
        pwdText.setText(model.order_pwd);
    }

//    public List<SportTimeModel> getSportTimeList(String sport_time) {
//        return new Gson().fromJson(sport_time, new TypeToken<List<SportTimeModel>>(){}.getType());
//    }
}
