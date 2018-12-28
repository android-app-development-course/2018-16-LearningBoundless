package com.scnu.learningboundless.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.activity.AddNewFriendActivity;
import com.scnu.learningboundless.activity.ChatActivity;
import com.scnu.learningboundless.activity.InvitationListActivity;
import com.scnu.learningboundless.bean.FriendInfo;
import com.scnu.learningboundless.constant.Constant;
import com.scnu.learningboundless.utils.Model;
import com.scnu.learningboundless.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WuchangI on 2018/12/14.
 */

public class FriendsFragment extends EaseContactListFragment {

    private ImageView mIvFriendRedDot;
    private LinearLayout mLlNewFriend;
    private LocalBroadcastManager mLBM;
    private String userName;

    private BroadcastReceiver mFriendInviteChangeReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // 显示红点
            mIvFriendRedDot.setVisibility(View.VISIBLE);
            SPUtils.getInstance().save(SPUtils.IS_NEW_INVITE, true);
        }
    };

    private BroadcastReceiver mFriendChangeReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            refreshFriendList();
        }
    };

    @Override
    protected void initView()
    {
        super.initView();

//        titleBar.setTitle(getResources().getString(R.string.my_friend));
//        titleBar.setRightImageResource(R.drawable.add);
//
        titleBar.setVisibility(View.GONE);
////
//        query.setVisibility(View.GONE);


        View friendListFragmentHeader = View.inflate(getActivity(), R.layout.fragment_contact_list_header, null);
        listView.addHeaderView(friendListFragmentHeader);

        mIvFriendRedDot = (ImageView) friendListFragmentHeader.findViewById(R.id.iv_friend_red_dot);
        mLlNewFriend = (LinearLayout) friendListFragmentHeader.findViewById(R.id.ll_new_friend);
    }


    @Override
    protected void setUpView()
    {
        super.setUpView();

        setContactListItemClickListener(new EaseContactListItemClickListener()
        {
            @Override
            public void onListItemClicked(EaseUser user)
            {
                if (user == null)
                {
                    return;
                }

                ChatActivity.actionStart(getActivity(), user.getUsername());
            }
        });

//        titleBar.setRightLayoutClickListener(v ->
//
//            AddNewFriendActivity.actionStart(FriendsFragment.this.getActivity())
//        );

        boolean isNewInvite = SPUtils.getInstance().getBoolean(SPUtils.IS_NEW_INVITE, false);

        mIvFriendRedDot.setVisibility(isNewInvite ? View.VISIBLE : View.INVISIBLE);

        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(mFriendInviteChangeReceiver, new IntentFilter(Constant.FRIEND_INVITE_CHANGED));
        mLBM.registerReceiver(mFriendChangeReceiver, new IntentFilter(Constant.FRIEND_CHANGED));


        mLlNewFriend.setOnClickListener(v ->
        {
            mIvFriendRedDot.setVisibility(View.INVISIBLE);
            SPUtils.getInstance().save(SPUtils.IS_NEW_INVITE, false);

            InvitationListActivity.actionStart(getActivity());
        });


        getFriendListFromEM();

        // 为好友列表listView注册上下文菜单
        registerForContextMenu(listView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

        EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);
        userName = easeUser.getUsername();

        // 加载删除联系人的上下文菜单的界面布局
        getActivity().getMenuInflater().inflate(R.menu.friend_delete, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.friend_delete)
        {
            deleteFriend();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    // 删除选中的好友
    private void deleteFriend()
    {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    EMClient.getInstance().contactManager().deleteContact(userName);

                    Model.getInstance().getFriendAndInvitationManager().getFriendInfoDao().deleteFriendByUserName(userName);

                    if (getActivity() == null)
                    {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast.makeText(getActivity(), getResources().getString(R.string.delete) + userName + getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();

                            refreshFriendList();
                        }
                    });
                }
                catch (HyphenateException e)
                {
                    e.printStackTrace();

                    if (getActivity() == null)
                    {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast.makeText(getActivity(), getResources().getString(R.string.delete) + userName + getResources().getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    /**
     * 去环信服务器获取所有好友信息
     */
    private void getFriendListFromEM()
    {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    // 当前用户的所有好友的环信id的列表
                    List<String> EMidList = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    if (EMidList != null && EMidList.size() >= 0)
                    {
                        List<FriendInfo> friendList = new ArrayList<>();

                        for (String EMId : EMidList)
                        {
                            FriendInfo friendInfo = new FriendInfo(EMId);
                            friendList.add(friendInfo);
                        }

                        Model.getInstance().getFriendAndInvitationManager().getFriendInfoDao().saveFriendList(friendList);

                        if (getActivity() == null)
                        {
                            return;
                        }

                        getActivity().runOnUiThread(() -> refreshFriendList());
                    }
                }
                catch (HyphenateException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    private void refreshFriendList()
    {
        List<FriendInfo> friendInfoList = Model.getInstance().getFriendAndInvitationManager().getFriendInfoDao().getFriendList();

        if (contactList != null && contactList.size() >= 0)
        {
            Map<String, EaseUser> friendListMap = new HashMap<>();

            for (FriendInfo friendInfo : friendInfoList)
            {
                EaseUser easeUser = new EaseUser(friendInfo.getUserName());
                friendListMap.put(friendInfo.getUserName(), easeUser);
            }

            setContactsMap(friendListMap);

            refresh();
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mLBM.unregisterReceiver(mFriendInviteChangeReceiver);
        mLBM.unregisterReceiver(mFriendChangeReceiver);
    }


}
