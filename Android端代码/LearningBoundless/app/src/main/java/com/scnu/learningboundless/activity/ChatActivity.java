package com.scnu.learningboundless.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.scnu.learningboundless.R;

/**
 * 聊天界面
 */
public class ChatActivity extends FragmentActivity {

    private String mUserEMId;
    private EaseChatFragment mEaseChatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
    }


    private void initView() {
        mUserEMId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        mEaseChatFragment = new EaseChatFragment();
        mEaseChatFragment.setArguments(getIntent().getExtras());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_chat, mEaseChatFragment).commit();
    }

    /**
     * 默认情况下，得到userEMId（conversation.conversationId()）后跳转到单聊界面
     *
     * @param context
     * @param userEMId
     */
    public static void actionStart(Context context, String userEMId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userEMId);

        context.startActivity(intent);
    }

}

