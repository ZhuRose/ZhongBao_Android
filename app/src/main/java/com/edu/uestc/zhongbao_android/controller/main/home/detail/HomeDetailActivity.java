package com.edu.uestc.zhongbao_android.controller.main.home.detail;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseDialogFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.all_comments.AllCommentsActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.chose_site.ChoseSiteActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.share.ShareActivity;
import com.edu.uestc.zhongbao_android.holder.ReserveViewHolder;
import com.edu.uestc.zhongbao_android.model.SessionDetailModel;
import com.edu.uestc.zhongbao_android.utils.DateUtil;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.BaseNavigationView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhu on 17/3/29.
 */

public class HomeDetailActivity extends BaseActivity {
    static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 10086;
    String uuid;
    String pic;

    NetworkUtil networkUtil;
    SessionDetailModel data;

    List<String> dateList;
    List<String> weekList;

    @OnClick({R.id.shareBtn, R.id.collectionBtn})
    void onClick(Button sender) {
        switch (sender.getId()) {
            case R.id.collectionBtn:
                sender.setSelected(!sender.isSelected());
                break;
            default:
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext, mainView, "share");
                startActivity(new Intent(mContext, ShareActivity.class), compat.toBundle());
                break;
        }
    }

    @OnClick(R.id.phoneBtn)
    void phoneButtonCLick(Button sender) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(mContext, "请授权", Toast.LENGTH_LONG);
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }else{
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }else {
            // 已经获得授权，可以打电话
            callPhone();
        }
    }

    void callPhone() {
        if (data.phone == null) return;
        // 拨号：激活系统的拨号组件
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        final String phone = data.phone;
        Uri data = Uri.parse("tel:" + phone); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }

    @OnClick(R.id.allCommentsView)
    void allCommentsViewClick(View view) {
        startActivity(new Intent(mContext, AllCommentsActivity.class));
    }

    @BindView(R.id.navi)
    BaseNavigationView navi;

    @BindView(R.id.collectionBtn)
    Button collectionBtn;

    @BindView(R.id.picBtn)
    Button picButton;

    @BindView(R.id.scroller)
    ScrollView scroller;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    ReserveAdapter adapter;

    @BindView(R.id.sportAndTimeBtn)
    TextView sportAndTimeBtn;

    @BindView(R.id.scoreView)
    TextView scoreView;

    @BindView(R.id.addressView)
    TextView addressView;

    @BindView(R.id.singleCommentView)
    View singleCommentView;

    @BindView(R.id.watchCommentsView)
    TextView watchCommentsView;

    int picHeight;
    int naviHeight;

    @BindView(R.id.mainView)
    View mainView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_detail_home;
    }

    @Override
    protected void initData() {
        super.initData();
        dateList = DateUtil.get7date();
        weekList = DateUtil.get7week();
        uuid = (String) getIntent().getExtras().get("uuid");
        pic = (String) getIntent().getExtras().get("pic");

        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                data = (SessionDetailModel)object;
                navi.setTitle(data.name);
                collectionBtn.setSelected(data.attention.equals("0"));
                sportAndTimeBtn.setText(data.sport+"("+data.service_time+")");
                scoreView.setText(data.score);
                addressView.setText(data.address);
                if (data.comments.size()>0) {
                    singleCommentView.setVisibility(View.VISIBLE);
                    watchCommentsView.setText("查看更多评论");
                } else {
                    singleCommentView.setVisibility(View.GONE);
                    watchCommentsView.setText("还没有评论");
                }

                ImageLoadManager.shareManager().loadImage(Constant.getMainImageUrl() + pic, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        picButton.setBackground(new BitmapDrawable(getResources(), loadedImage));
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {

            }
        };
        getResponse();
    }

    protected void getResponse() {
        networkUtil.sessionDetailNetwork(uuid);
    }

    @Override
    protected void initView() {
        super.initView();
        navi.setBgAlpha(0.0f);
        picHeight = Constant.getScreenWidth(mContext) *14/25;
        naviHeight = (int)(77*Constant.getDensity(mContext));
        picButton.setHeight(picHeight);
        setupScroller();
        setupRecycler();
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void setupScroller() {
        scroller.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > picHeight-naviHeight) navi.setBgAlpha(1.0f);
                else navi.setBgAlpha(((float)scrollY)/((float)(picHeight-naviHeight)));
            }
        });
    }

    protected void setupRecycler() {
        LinearLayoutManager llm = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        adapter = new ReserveAdapter();
        recyclerView.setAdapter(adapter);
    }

    class ReserveAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.cell_reserve, null);
            ReserveViewHolder viewHolder = new ReserveViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ReserveViewHolder viewHolder = (ReserveViewHolder)holder;
            viewHolder.setViews(weekList.get(position), dateList.get(position), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, ChoseSiteActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }
}
