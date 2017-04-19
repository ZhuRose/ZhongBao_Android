package com.edu.uestc.zhongbao_android.controller.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zhu on 17/3/12.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View mRootView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null) {
            mRootView = inflater.inflate(loadLayout(), container, false);
            ButterKnife.bind(this,mRootView);
            initData(getArguments());
            initView();
        }
        return mRootView;
    }

    protected abstract int loadLayout();

    protected void initView() {
    }

    protected void initData(Bundle bundle) {

    }
}
