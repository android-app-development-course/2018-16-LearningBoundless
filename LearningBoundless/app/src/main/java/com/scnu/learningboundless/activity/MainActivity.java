package com.scnu.learningboundless.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gao.jiefly.nubiatimer.Timer;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.adapter.MyFragmentPagerAdapter;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.base.BaseFragment;
import com.scnu.learningboundless.fragment.FriendsFragment;
import com.scnu.learningboundless.fragment.MessageFragment;
import com.scnu.learningboundless.fragment.StatisticsFragment;
import com.scnu.learningboundless.fragment.TaskFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;

    @BindView(R.id.bottom_navigation_bar)
    protected BottomNavigationBar mBottomNavigationBar;

    private List<Fragment> mFragmentList;

    private int mCurFragmentIndex;


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initBottomNavigationBar();
        initViewPager();
    }


    /**
     * 初始化底部导航栏
     */
    protected void initBottomNavigationBar() {
        mBottomNavigationBar.clearAll();

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setActiveColor(R.color.bnb_active_color);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.task, getResources().getString(R.string.task)))
                .addItem(new BottomNavigationItem(R.drawable.message, getResources().getString(R.string.message)))
                .addItem(new BottomNavigationItem(R.drawable.friends, getResources().getString(R.string.friends)))
                .addItem(new BottomNavigationItem(R.drawable.statistics, getResources().getString(R.string.statistics)))
                .setFirstSelectedPosition(0).initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mViewPager.setCurrentItem(position);
                mCurFragmentIndex = position;
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


    /**
     * 初始化翻页器
     */
    protected void initViewPager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TaskFragment());
        mFragmentList.add(new MessageFragment());
        mFragmentList.add(new FriendsFragment());
        mFragmentList.add(new StatisticsFragment());

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
                mCurFragmentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }
}
