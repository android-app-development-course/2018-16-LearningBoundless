package com.scnu.learningboundless.activity;

import android.os.Handler;
import android.os.Message;

import com.hyphenate.chat.EMClient;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.bean.AccountInfo;
import com.scnu.learningboundless.utils.Model;

public class SplashActivity extends BaseActivity {

//    @BindView(R.id.tv_splash_title)
//    protected TextView mTvSplashTitle;

    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            // 若当前Activity已经退出，则不处理handler中的消息
            if (isFinishing()) {
                return;
            }

            // 进行界面跳转
            toLoginOrMain();
        }
    };


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

//        initTypefaceOfTitle();

        // 发送2s的延时消息
        mHandler.sendMessageDelayed(Message.obtain(), 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁消息
        mHandler.removeCallbacksAndMessages(null);
    }

//
//    /**
//     * 初始化启动页的标题的字体
//     */
//    private void initTypefaceOfTitle() {
//        mTvSplashTitle.setTypeface(TypefaceUtils.getInstance().getTypeface1());
//    }


    /**
     * 判断进入登录页面还是主页面
     */
    private void toLoginOrMain() {

        Model.getInstance().getGlobalThreadPool().execute(() ->
        {
            // 用户之前登录过
            if (EMClient.getInstance().isLoggedInBefore()) {
                // 获取当前账号用户的信息
                AccountInfo accountInfo = Model.getInstance().getAccountInfoDao().getAccountInfoByUserName(EMClient.getInstance().getCurrentUser());

                // userInfo为null时，重新登录
                if (accountInfo == null) {
                    LoginActivity.actionStart(SplashActivity.this);
                } else {
                    // 登录成功后的处理
                    Model.getInstance().loginSuccess(accountInfo);

                    MainActivity.actionStart(SplashActivity.this);
                }
            }
            // 用户之前没有登录过
            else {
                LoginActivity.actionStart(SplashActivity.this);
            }

            finish();
        });
    }
}
