package com.scnu.learningboundless.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by WuchangI on 2018/12/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initArgs(getIntent().getExtras());

        int layoutId = getContentLayoutId();
        setContentView(layoutId);

        initWidget();

        initData();
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
    }


    /**
     * 相关参数初始化
     * @param bundle
     */
    protected void initArgs(Bundle bundle) {
    }


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化界面控件
     */
    protected void initWidget() {
        initTransparentStatusBar();
        ButterKnife.bind(this);
    }

    /**
     * 初始化相关数据
     */
    protected void initData() {
    }


    /**
     * 初始化透明状态栏
     */
    private void initTransparentStatusBar() {
        // 实现透明状态栏效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}
