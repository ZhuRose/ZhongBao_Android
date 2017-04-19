package com.edu.uestc.zhongbao_android.controller.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;

import java.util.zip.Inflater;

import static android.animation.ValueAnimator.INFINITE;

/**
 * Created by zhu on 17/4/9.
 */

public class BaseDialogFragment extends DialogFragment {

    static BaseDialogFragment dialogFragment;
    int status;
    String text;
    StatusView statusView;
    TextView textView;
    View rootView;
    boolean done = false;

    final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(2000);

    Handler mHandler;
    Runnable runnable;

    public BaseDialogFragment() {
        done = false;
        mHandler = new Handler();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog, null);
        statusView = (StatusView)rootView.findViewById(R.id.statusView);
        statusView.setStatus(status);
        if (status!=2) beginDelay();
        textView = (TextView)rootView.findViewById(R.id.textView);
        textView.setText(text);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        done = true;
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancleDelay();
    }

    public static void showSuccess(FragmentManager manager, String text) {
        showStatus(manager, "success_dialog", text, 0);
    }

    public static void showFailed(FragmentManager manager, String text) {
        showStatus(manager, "failed_dialog", text, 1);
    }

    public static void showLoading(FragmentManager manager) {
        showStatus(manager, "loading_dialog", "loading...", 2);
    }

    public static void showStatus(FragmentManager manager, String tag, String text, int status) {
        if (dialogFragment == null) {
            dialogFragment = new BaseDialogFragment();
        }
        if (!dialogFragment.done) {//还没加载完
            dialogFragment.status = status;
            dialogFragment.text = text;
            dialogFragment.show(manager, tag);
        } else {
            dialogFragment.cancleDelay();
            dialogFragment.statusView.setStatus(status);
            dialogFragment.textView.setText(text);
            if (status!=2) dialogFragment.beginDelay();
        }
    }

    protected void beginDelay() {
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    BaseDialogFragment.dismissDialog();
                }
            };
        }
        mHandler.postDelayed(runnable, 2000);
    }

    protected void cancleDelay() {
        if (runnable!=null) {
            mHandler.removeCallbacks(runnable);
            runnable = null;
        }
    }

    public static void dismissDialog(){
        if (dialogFragment!= null) {
            dialogFragment.dismiss();
        }
        dialogFragment = null;
    }

}
