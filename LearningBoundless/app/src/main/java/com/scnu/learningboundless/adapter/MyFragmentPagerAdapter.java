package com.scnu.learningboundless.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scnu.learningboundless.base.BaseFragment;

import java.util.List;

/**
 * Created by WuchangI on 2018/12/14.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {

        if(mFragmentList != null && mFragmentList.size() > 0)
        {
            return mFragmentList.get(position);
        }

        return null;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
