package com.edu.uestc.zhongbao_android.controller.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.login.LoginActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.HomeFragment;
import com.edu.uestc.zhongbao_android.controller.main.me.MeFragment;
import com.edu.uestc.zhongbao_android.controller.main.message.MessageFragment;
import com.edu.uestc.zhongbao_android.controller.main.publish.PublishActivity;
import com.edu.uestc.zhongbao_android.controller.main.sport.SportFragment;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.UserManager;

import butterknife.BindView;

/**
 * Created by zhu on 17/3/21.
 */

public class MainActivity extends BaseActivity {

    //退出时的时间
    private long mExitTime;

    private MainBroadcastReceiver broadcastReceiver;

    @BindView(R.id.tab)
    FragmentTabHost tabHost;

    String[] titleArr = {"首页", "资讯", "", "运动圈", "我的"};
    int[] iconArr = {R.drawable.tab_home, R.drawable.tab_message, R.drawable.tab_publish, R.drawable.tab_sport, R.drawable.tab_me};
    Class[] fragmentArr = {HomeFragment.class, MessageFragment.class, Fragment.class, SportFragment.class, MeFragment.class};

    @Override
    protected int loadLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setEnterTransition(null);
        registerBroadcast();
        ImageLoadManager.shareManager().setupImageLoader(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        setupTabhost();
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean firstLogin = UserManager.shareManager(mContext).getHasLogin();
        Log.v("main", ""+firstLogin);
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("login");
        broadcastReceiver = new MainBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void setupTabhost() {
        tabHost.setup(mContext, getSupportFragmentManager(), R.id.frame_content);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });
        for (int i=0; i<fragmentArr.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(titleArr[i]).setIndicator(getView(i));
            tabHost.addTab(tabSpec, fragmentArr[i], null);
        }
        tabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("main", "tap publish");
                if (!UserManager.shareManager(mContext).getHasLogin()) {
                    Intent intent = new Intent();
                    intent.setAction("login");
                    mContext.sendBroadcast(intent);
                    return;
                }
                startActivity(new Intent(mContext, PublishActivity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(mContext).toBundle());
            }
        });


    }

    protected View getView(int index) {
        View view;
        if (index == 2){
            view = LayoutInflater.from(mContext).inflate(R.layout.tab_publish,null);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.tab_content,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.tab_icon);
            imageView.setImageResource(iconArr[index]);
            TextView textView = (TextView)view.findViewById(R.id.tab_textview);
            textView.setText(titleArr[index]);
        }

        return view;
    }


    class MainBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("main", "hahahaha");
//            Activity activity = ((MainApplication)getApplication()).currentActivity();
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出订场帝", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finishAfterTransition();
            System.exit(0);
        }
    }
}
