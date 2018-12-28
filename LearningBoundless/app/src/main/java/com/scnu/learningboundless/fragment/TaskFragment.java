package com.scnu.learningboundless.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.activity.CreateTaskActivity;
import com.scnu.learningboundless.adapter.TaskAdapter;
import com.scnu.learningboundless.bean.task.Task;
import com.scnu.learningboundless.db.TaskDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by WuchangI on 2018/12/14.
 */

public class TaskFragment extends Fragment {

    private Unbinder mRootUnbinder;

    @BindView(R.id.rv_mood_list)
    RecyclerView mRvMoodList;

    @BindView(R.id.tv_empty_hint)
    TextView mTvEmptyHint;

    @BindView(R.id.fab_menu)
    FloatingActionsMenu mFloatingActionsMenu;

    public static TaskAdapter mTaskAdapter;
    private TaskDao mTaskDao;

    private List<Task> mTaskList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        initWidget(rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvMoodList.setLayoutManager(linearLayoutManager);
        mRvMoodList.setAdapter(mTaskAdapter);
        mRvMoodList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mFloatingActionsMenu.collapse();
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
    }

    protected void initData() {

        mTaskDao = new TaskDao(getActivity());
        mTaskList = mTaskDao.listTasks();
        mTaskAdapter = new TaskAdapter(getActivity(), mTaskList, mTaskDao);
        //Toast.makeText(getActivity(), "sssss", Toast.LENGTH_SHORT).show();
    }

    protected void initWidget(View rootView) {

        mRootUnbinder = ButterKnife.bind(this, rootView);

        if (mTaskList != null && mTaskList.size() > 0) {
            mTvEmptyHint.setVisibility(View.INVISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvMoodList.setLayoutManager(linearLayoutManager);
        mRvMoodList.setAdapter(mTaskAdapter);
        mRvMoodList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mFloatingActionsMenu.collapse();
            }
        });
    }

    @OnClick({R.id.fab_create_card, R.id.fab_clear_all_cards})
    public void handleAllClick(View v) {
        switch (v.getId()) {
            case R.id.fab_create_card:
                CreateTaskActivity.actionStart(getActivity());
                mFloatingActionsMenu.collapse();
                break;

            case R.id.fab_clear_all_cards:
                clearAllCards();
                mFloatingActionsMenu.collapse();
                break;

            default:
                break;
        }
    }


    private void clearAllCards() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.is_clear_all));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mTaskAdapter.removeAllTasks();
                mTaskDao.removeAllTasks();
                mTvEmptyHint.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), getResources().getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), getResources().getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mRootUnbinder.unbind();
    }
}