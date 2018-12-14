package com.scnu.learningboundless.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.utils.TypefaceUtils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_splash_title)
    protected TextView mTvSplashTitle;

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

        initTypefaceOfTitle();

        // 发送2s的延时消息
        mHandler.sendMessageDelayed(Message.obtain(), 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁消息
        mHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 初始化启动页的标题的字体
     */
    private void initTypefaceOfTitle() {
        mTvSplashTitle.setTypeface(TypefaceUtils.getInstance().getTypeface1());
    }


    /**
     * 判断进入登录页面还是主页面
     */
    private void toLoginOrMain() {

        LoginActivity.actionStart(this);
    }
}
