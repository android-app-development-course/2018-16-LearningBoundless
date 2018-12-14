package com.scnu.learningboundless.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.utils.TypefaceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login_title)
    protected TextView mTvLoginTitle;

    @BindView(R.id.et_user_name)
    protected EditText mEtUserName;

    @BindView(R.id.et_password)
    protected EditText mEtPassword;

    @BindView(R.id.btn_login)
    protected Button mBtnLogin;

    @BindView(R.id.fab_plus)
    protected FloatingActionButton mFabPlus;

    @BindView(R.id.cv_login)
    protected CardView mCvLogin;


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initTypefaceOfTitle();
    }


    /**
     * 初始化登录界面的标题的字体
     */
    private void initTypefaceOfTitle() {
        mTvLoginTitle.setTypeface(TypefaceUtils.getInstance().getTypeface1());
    }


    @OnClick({R.id.btn_login, R.id.fab_plus})
    public void handleAllClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:
                login();
                break;

            case R.id.fab_plus:

                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mFabPlus, mFabPlus.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());

                break;

            default:
                break;
        }
    }


    /**
     * 执行登录操作
     */
    private void login() {
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);


        String userName = mEtUserName.getText().toString();
        String password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, getResources().getString(R.string.not_empty_user_name_or_password), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent, oc2.toBundle());

    }


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mFabPlus.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFabPlus.setVisibility(View.VISIBLE);
    }
}
