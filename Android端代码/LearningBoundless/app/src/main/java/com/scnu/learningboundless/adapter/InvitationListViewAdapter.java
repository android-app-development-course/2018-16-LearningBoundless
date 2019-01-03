package com.scnu.learningboundless.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.bean.InvitationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuchangI on 2018/11/21.
 */

public class InvitationListViewAdapter extends BaseAdapter {
    private Context mContext;

    private OnInviteListener mOnInviteListener;

    private List<InvitationInfo> mInvitationInfoList = new ArrayList<>();

    public InvitationListViewAdapter(Context context, OnInviteListener onInviteListener) {
        mContext = context;
        mOnInviteListener = onInviteListener;
    }

    @Override
    public int getCount() {
        return mInvitationInfoList == null ? 0 : mInvitationInfoList.size();
    }


    @Override
    public Object getItem(int position) {
        return mInvitationInfoList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = View.inflate(mContext, R.layout.item_invite, null);

            viewHolder.mTvName = convertView.findViewById(R.id.tv_name);
            viewHolder.mTvReason = convertView.findViewById(R.id.tv_reason);

            viewHolder.mBtnAccept = convertView.findViewById(R.id.btn_accept);
            viewHolder.mBtnReject = convertView.findViewById(R.id.btn_reject);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        InvitationInfo invitationInfo = mInvitationInfoList.get(position);

        viewHolder.mTvName.setText(invitationInfo.getUserName());
        viewHolder.mBtnAccept.setVisibility(View.INVISIBLE);
        viewHolder.mBtnReject.setVisibility(View.INVISIBLE);

        if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.NEW_INVITE) {
            if (invitationInfo.getReason() == null) {
                viewHolder.mTvReason.setText(mContext.getResources().getString(R.string.add_a_friend));
            } else {
                viewHolder.mTvReason.setText(invitationInfo.getReason());
            }

            viewHolder.mBtnAccept.setVisibility(View.VISIBLE);
            viewHolder.mBtnReject.setVisibility(View.VISIBLE);
        } else if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT) {
            if (invitationInfo.getReason() == null) {
                viewHolder.mTvReason.setText(mContext.getResources().getString(R.string.accept_invitation));
            } else {
                viewHolder.mTvReason.setText(invitationInfo.getReason());
            }
        } else if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {
            if (invitationInfo.getReason() == null) {
                viewHolder.mTvReason.setText(mContext.getResources().getString(R.string.invitation_accepted));
            } else {
                viewHolder.mTvReason.setText(invitationInfo.getReason());
            }
        }


        viewHolder.mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnInviteListener.onAccept(invitationInfo);
            }
        });


        viewHolder.mBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnInviteListener.onReject(invitationInfo);
            }
        });

        return convertView;
    }


    /**
     * 刷新页面数据
     */
    public void refresh(List<InvitationInfo> invitationInfoList) {
        if (invitationInfoList != null && invitationInfoList.size() >= 0) {
            mInvitationInfoList.clear();

            mInvitationInfoList.addAll(invitationInfoList);

            notifyDataSetChanged();
        }
    }


    private class ViewHolder {
        private TextView mTvName;
        private TextView mTvReason;

        private Button mBtnAccept;
        private Button mBtnReject;
    }


    public interface OnInviteListener {
        /**
         * 接受邀请按钮的点击事件
         */
        void onAccept(InvitationInfo invitationInfo);

        /**
         * 拒绝邀请按钮的点击事件
         */
        void onReject(InvitationInfo invitationInfo);

    }


}
