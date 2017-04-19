package com.edu.uestc.zhongbao_android.controller.main.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseFragment;
import com.edu.uestc.zhongbao_android.controller.main.home.chose_city.ChoseCityActivity;
import com.edu.uestc.zhongbao_android.controller.main.home.detail.HomeDetailActivity;
import com.edu.uestc.zhongbao_android.controller.main.message.detail.WebActivity;
import com.edu.uestc.zhongbao_android.holder.HomeViewHolder;
import com.edu.uestc.zhongbao_android.model.BannerInfoModel;
import com.edu.uestc.zhongbao_android.model.HomeInfoModel;
import com.edu.uestc.zhongbao_android.model.HomeModel;
import com.edu.uestc.zhongbao_android.utils.ImageLoadManager;
import com.edu.uestc.zhongbao_android.utils.NetworkUtil;
import com.edu.uestc.zhongbao_android.view.CycleScrollView;
import com.edu.uestc.zhongbao_android.view.CycleScrollViewDataSource;
import com.edu.uestc.zhongbao_android.view.CycleScrollViewDelegate;
import com.edu.uestc.zhongbao_android.view.HomeNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhu on 17/3/21.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.navi)
    HomeNavigationView navi;

    @BindView(R.id.menu2)
    ConstraintLayout menu;

    @BindView(R.id.refreshView)
    PtrClassicFrameLayout refreshView;

    @BindView(R.id.list)
    ListView list;

    View header;
    HomeAdapter adapter;
    List<HomeInfoModel> dataSouce;
    List<BannerInfoModel> bannerInfos;

    CycleScrollView banner;
    int bannerWidth;
    int bannerHeight;
    int naviHeight;

    static final int LocationTag = 100;

    NetworkUtil networkUtil;

    @Override
    protected int loadLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        setupNavi();
        setupHeader();
        setupList();
        getResponse();
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        dataSouce = new ArrayList<HomeInfoModel>();
        bannerInfos = new ArrayList<BannerInfoModel>();
        networkUtil = new NetworkUtil(mActivity) {
            @Override
            public void successNetwork(Object object, String tag) {
                refreshView.refreshComplete();
                HomeModel model = (HomeModel)object;
                dataSouce = model.result;
                adapter.notifyDataSetChanged();
                bannerInfos = model.bannerinfos;
                banner.reload();
            }

            @Override
            public void failedNetwork(String errorInfo, String tag) {
                refreshView.refreshComplete();
            }
        };
    }

    protected void getResponse() {
        networkUtil.homeNetwork();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChoseCityActivity.ChoseCityTag) {
            navi.setLocationTitle(data.getStringExtra("city"));
        }
    }

    public void setupNavi() {
        navi.setLocationBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mActivity, ChoseCityActivity.class), LocationTag);
            }
        });
    }

    public void setupHeader() {
        header = LayoutInflater.from(mActivity).inflate(R.layout.header_list_home, null);
        banner = (CycleScrollView) header.findViewById(R.id.banner);
        banner.setDataSource(new CycleScrollViewDataSource() {
            @Override
            public int numberOfPages() {
                return bannerInfos.size();
            }

            @Override
            public void pageAt(int index, ImageView view) {
                BannerInfoModel model = bannerInfos.get(index);
                ImageLoadManager.shareManager().displayImage(Constant.getMainImageUrl()+model.picture, view);
            }
        });
        banner.setDelegate(new CycleScrollViewDelegate() {
            @Override
            public void didSelectItemAt(int index) {
                BannerInfoModel model = bannerInfos.get(index);
                if (model.type.equals("0")) {//场馆信息
                    startActivity(new Intent(mActivity, HomeDetailActivity.class));
                } else {
                    startActivity(new Intent(mActivity, WebActivity.class));
                }
            }
        });

        float density = getResources().getDisplayMetrics().density;
        bannerWidth = getResources().getDisplayMetrics().widthPixels;
        bannerHeight = (int)(bannerWidth*94/207 + 216*density);
        naviHeight = (int)(77*density);
        Log.v("main","scroll"+bannerWidth+"/"+bannerHeight+"/"+naviHeight);
        ListView.LayoutParams layoutParams = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (bannerHeight + 36 * density));
        header.setLayoutParams(layoutParams);
        list.addHeaderView(header);
    }

    public void setupList() {
        adapter = new HomeAdapter();
//        list.setDividerHeight((int)getResources().getDisplayMetrics().density);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                    int h = getScrollY();//滚动距离

                    //在此进行你需要的操作//TODO
                    Log.v("main","scrollll"+h+"///"+mCurrentfirstVisibleItem);

                    if (h >= bannerHeight-naviHeight) {
                        navi.setBgAlpha(1.0f);
                        menu.setVisibility(View.VISIBLE);
                    } else {
                        navi.setBgAlpha((float)h/(float)(bannerHeight-naviHeight));
                        menu.setVisibility(View.GONE);
                    }
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    if (itemRecod != null) height += itemRecod.height;
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }

            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                HomeInfoModel model = dataSouce.get(position-1);
                intent.putExtra("uuid", model.uuid);
                intent.putExtra("pic", model.picture);
                startActivity(intent);
            }
        });

        refreshView.setKeepHeaderWhenRefresh(true);
        refreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getResponse();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        banner.setAnimationDuration(5);
    }

    @Override
    public void onPause() {
        super.onPause();
//        banner.setAnimationDuration(0);
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataSouce.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HomeViewHolder holder;
            HomeInfoModel model = dataSouce.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.cell_home, null);
                holder = new HomeViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (HomeViewHolder) convertView.getTag();
            }
            holder.setViews(model.picture, model.name, Float.valueOf(model.score), model.sport+" "+model.distance, model.price);
            return convertView;
        }
    }

}