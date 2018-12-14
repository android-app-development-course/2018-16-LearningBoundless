package com.scnu.learningboundless.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WuchangI on 2018/12/14.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null) {
            int layoutId = getContentLayoutId();
            View rootView = inflater.inflate(layoutId, container, false);

            initWidget(rootView);

            mRootView = rootView;
        }else {
            if(mRootView.getParent() != null) {
                ((ViewGroup)(mRootView.getParent())).removeView(mRootView);
            }
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    /**
     * 相关参数初始化
     * @param bundle
     */
    protected void initArgs(Bundle bundle) {
    }


    /**
     * 得到当前Fragment对应的布局文件的id
     *
     * @return
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化界面控件
     */
    protected void initWidget(View rootView) {
       mRootUnbinder =  ButterKnife.bind(this, rootView);
    }

    /**
     * 初始化相关数据
     */
    protected void initData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootUnbinder.unbind();
    }
}
