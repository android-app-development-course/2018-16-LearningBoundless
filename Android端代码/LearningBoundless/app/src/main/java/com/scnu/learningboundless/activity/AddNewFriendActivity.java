package com.scnu.learningboundless.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.base.BaseActivity;
import com.scnu.learningboundless.utils.Model;

import butterknife.BindView;
import butterknife.OnClick;

public class AddNewFriendActivity extends BaseActivity {

    @BindView(R.id.sv_new_friend_name)
    android.support.v7.widget.SearchView mSvNewFriendName;

    @BindView(R.id.tv_new_friend_name)
    TextView mTvNewFriendName;

    @BindView(R.id.ll_add_new_friend)
    LinearLayout mLlAddNewFriend;

    private String curInputText;


    /**
     * 得到当前Activity对应的布局文件的id
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_add_new_friend;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initSearchView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftKeyboard();
    }



    @OnClick({R.id.iv_back, R.id.tv_back, R.id.iv_search_new_friend, R.id.btn_add_new_friend})
    public void handleAllClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.iv_search_new_friend:
                searchNewFriend(curInputText);
                break;

            case R.id.btn_add_new_friend:
                addNewFriend();
                break;

            default:
                break;
        }
    }


    private void initSearchView() {
        mSvNewFriendName.setQueryHint(getResources().getString(R.string.input_user_name));
        mSvNewFriendName.setIconified(false);

        mSvNewFriendName.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNewFriend(curInputText);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                curInputText = newText;
                return true;
            }
        });
    }


    private void searchNewFriend(String newFriendName) {
        if (TextUtils.isEmpty(newFriendName)) {
            Toast.makeText(this, getResources().getString(R.string.not_empty_user_name), Toast.LENGTH_SHORT).show();
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去自己的服务器查询判断是否存在newFriendName用户，这里省略......

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLlAddNewFriend.setVisibility(View.VISIBLE);
                        mTvNewFriendName.setText(newFriendName);
                    }
                });
            }

        });
    }


    private void addNewFriend() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().
                            addContact(mTvNewFriendName.getText().toString(), getResources().getString(R.string.add_new_friend));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddNewFriendActivity.this, getResources().getString(R.string.send_invitation_success), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddNewFriendActivity.this, getResources().getString(R.string.send_invitation_failure) + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AddNewFriendActivity.class));
    }

}
