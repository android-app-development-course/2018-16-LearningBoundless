package com.scnu.learningboundless.fragment;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.activity.ChatActivity;
import com.scnu.learningboundless.base.BaseFragment;

import java.util.List;

/**
 * Created by WuchangI on 2018/12/14.
 */

/**
 * 消息列表界面
 */
public class MessageFragment extends EaseConversationListFragment {

    @Override
    protected void initView()
    {
        super.initView();

        titleBar.setTitle(getResources().getString(R.string.message));

        setConversationListItemClickListener(conversation ->
        {
            // 为单聊（为默认值，不用设置类型值）
            if (conversation.getType() == EMConversation.EMConversationType.Chat)
            {
                ChatActivity.actionStart(getActivity(), conversation.conversationId());
            }
        });

        conversationList.clear();

        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
    }

    @Override
    protected void setUpView()
    {
        super.setUpView();
    }



    private EMMessageListener mEMMessageListener = new EMMessageListener()
    {
        @Override
        public void onMessageReceived(List<EMMessage> list)
        {
            // 刷新消息列表
            EaseUI.getInstance().getNotifier().notify(list);
            refresh();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list)
        {

        }

        @Override
        public void onMessageRead(List<EMMessage> list)
        {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list)
        {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list)
        {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o)
        {

        }
    };
}
