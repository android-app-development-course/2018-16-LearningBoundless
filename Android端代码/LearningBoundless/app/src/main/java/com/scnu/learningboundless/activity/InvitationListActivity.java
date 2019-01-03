package com.scnu.learningboundless.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.adapter.InvitationListViewAdapter;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.bean.InvitationInfo;
import com.scnu.learningboundless.constant.Constant;
import com.scnu.learningboundless.utils.Model;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InvitationListActivity extends BaseActivity {
    @BindView(R.id.lv_invitation_list)
    ListView mLvInvitationList;

    private InvitationListViewAdapter mInvitationListViewAdapter;

    private LocalBroadcastManager mLBM;

    /***
     * 监听好友邀请信息的变化
     */
    private BroadcastReceiver mInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLvInvitationList();
        }
    };

    private InvitationListViewAdapter.OnInviteListener mOnInviteListener = new InvitationListViewAdapter.OnInviteListener() {
        @Override
        public void onAccept(InvitationInfo invitationInfo) {
            // 通知环信服务器已经接受了好友邀请
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invitationInfo.getUserName());

                        Model.getInstance().getFriendAndInvitationManager().
                                getInvitationInfoDao().updateInvitationStatus(invitationInfo.getUserName(),
                                InvitationInfo.InvitationStatus.INVITE_ACCEPT);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InvitationListActivity.this, getResources().getString(R.string.accept_a_invitation), Toast.LENGTH_SHORT).show();

                                refreshLvInvitationList();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InvitationListActivity.this, getResources().getString(R.string.accept_a_invitation_failure), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onReject(InvitationInfo invitationInfo) {
            // 通知环信服务器已经拒绝了好友邀请
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invitationInfo.getUserName());

                        Model.getInstance().getFriendAndInvitationManager().getInvitationInfoDao().deleteInvitationByUserName(invitationInfo.getUserName());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InvitationListActivity.this, getResources().getString(R.string.reject_a_invitation), Toast.LENGTH_SHORT).show();

                                refreshLvInvitationList();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InvitationListActivity.this, getResources().getString(R.string.reject_a_invitation_failure), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });
        }
    };



    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_invitation_list;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        initLvInvitationList();

        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(mInviteChangeReceiver, new IntentFilter(Constant.FRIEND_INVITE_CHANGED));
    }

    @OnClick({R.id.iv_back, R.id.tv_back})
    public void handleAllClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_back:
                finish();
                break;

            default:
                break;
        }
    }


    private void initLvInvitationList() {
        mInvitationListViewAdapter = new InvitationListViewAdapter(this, mOnInviteListener);
        mLvInvitationList.setAdapter(mInvitationListViewAdapter);

        refreshLvInvitationList();
    }


    /**
     * 刷新邀请信息列表
     */
    private void refreshLvInvitationList() {
        List<InvitationInfo> invitationInfoList = Model.getInstance().getFriendAndInvitationManager().getInvitationInfoDao().getInvitationList();

        mInvitationListViewAdapter.refresh(invitationInfoList);
    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, InvitationListActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(mInviteChangeReceiver);
    }
}
