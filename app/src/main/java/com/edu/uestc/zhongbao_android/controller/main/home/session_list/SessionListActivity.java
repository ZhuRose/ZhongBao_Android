package com.edu.uestc.zhongbao_android.controller.main.home.session_list;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.model.NameModel;
import com.edu.uestc.zhongbao_android.model.RegionListModel;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.DropView;
import com.edu.uestc.zhongbao_android.view.DropViewDataSource;
import com.edu.uestc.zhongbao_android.view.DropViewDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/6.
 */

public class SessionListActivity extends BaseActivity {

    @BindView(R.id.dropView)
    DropView dropView;

    NetworkUtil networkUtil;
    String[] regionArr;
    String[] sportsArr;
    String[] sortArr;
    int sport;
    String[] selectedStrs;

    SessionListFragment mFragment;

    @Override
    protected int loadLayout() {
        return R.layout.activity_list_session;
    }

    @Override
    protected void initData() {
        super.initData();
        regionArr = new String[]{"全部区域"};
        sportsArr = new String[]{"所有运动", "羽毛球", "乒乓球", "台球", "篮球", "健身", "足球", "舞蹈", "游泳"};
        sortArr = new String[]{"智能排序", "离我最近", "价格最低", "人气最高", "评价最高"};
        sport = getIntent().getIntExtra("sport",0);
        selectedStrs = new String[]{"全部区域",sportsArr[sport],"智能排序"};
        networkUtil = new NetworkUtil(mContext) {
            @Override
            public void successNetwork(Object object, String tag) {
                RegionListModel model = (RegionListModel)object;
                regionArr = new String[model.districts.size()+1];
                regionArr[0] = "全部区域";
                for (int i=0; i<model.districts.size(); i++) {
                    regionArr[i+1] = model.districts.get(i).name;
                }
                dropView.reload();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {

            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        dropView.setDataSource(new DropViewDataSource() {
            @Override
            public int numberOfMeuns() {
                return 3;
            }

            @Override
            public String titleForMenu(int menuNum) {
                return selectedStrs[menuNum];
            }

            @Override
            public String[] dataSorceIn(int menuNum) {
                String[] strings;
                switch (menuNum) {
                    case 0:
                        strings = regionArr;
                        break;
                    case 1:
                        strings = sportsArr;
                        break;
                    default:
                        strings = sortArr;
                        break;
                }
                return strings;
            }
        });
        dropView.setDelegate(new DropViewDelegate() {
            @Override
            public void didSelectItemWith(int[] selectedArr) {
                String region = selectedArr[0] == 0? "" : regionArr[selectedArr[0]];
                String sport = selectedArr[1] == 0? "" : sportsArr[selectedArr[1]];
                String sort = selectedArr[2] == 0? "0" : ""+(selectedArr[2]-1);
                mFragment.setCondition(region,sport,sort);
                mFragment.getResponse();
            }
        });
        dropView.selectedArr[1] = sport;
        initFragment();
        getResponse();
    }

    protected void getResponse() {
        networkUtil.getRegionListNetwork();
    }

    protected void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragment = new SessionListFragment();
        mFragment.setCondition("",sportsArr[sport],"0");
        transaction.replace(R.id.frame_content, mFragment);
        transaction.commit();
    }

}
