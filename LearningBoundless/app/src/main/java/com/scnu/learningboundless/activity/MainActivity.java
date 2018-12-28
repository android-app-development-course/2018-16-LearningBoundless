package com.scnu.learningboundless.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
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
        return R.layout.drawer_menu_layout;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initBottomNavigationBar();
        initViewPager();

        initDrawerLayout();
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


    /**
     * 初始化侧滑菜单
     */
    protected void initDrawerLayout(){

        //设置ToolBar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        mToolbar.setNavigationIcon(R.drawable.add); //无效
        mToolbar.setTitleTextColor(Color.WHITE);    //无效
        mToolbar.inflateMenu(R.menu.drawer);        //无效
        setSupportActionBar(mToolbar);


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.item_a:
                        msg += "Hello World!!!!!!!!!!!!";
                        break;
                    default:
                        break;
                }
                if(!msg.equals("")){
                    Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        //设置抽屉DrawerLayout
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
//        mDrawerToggle.
//        mDrawerToggle.setToolbarNavigationClickListener();

        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.navigation_header);

        View iv_headimage=headerLayout.findViewById(R.id.iv_headimage);
        iv_headimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();//关闭抽屉
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item_one:

                        break;
                    case R.id.item_two:

                        break;
                    case R.id.item_three:
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FriendsFragment()).commit();
//                        mToolbar.setTitle("附近的人");
                        break;
                    case R.id.iv_headimage:
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void actionStart(Context context, Bundle bundle) {
        context.startActivity(new Intent(context, MainActivity.class), bundle);
    }
}
