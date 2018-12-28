package com.scnu.learningboundless.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.bean.task.Task;
import com.scnu.learningboundless.db.TaskDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_finish)
    TextView mTvFinish;   //tv_start_year

    @BindView(R.id.tv_start_time)
    TextView mTvStart;

    @BindView(R.id.tv_end_time)
    TextView mTvEnd;

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @BindView(R.id.tv_priority)
    TextView mTvPriority;

    private TaskDao mTaskDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_detail);

        initView();
    }


    private void initView() {
        ButterKnife.bind(this);

        mTaskDao = new TaskDao(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.your_mood));
        }

        String date = getIntent().getStringExtra("date");
        Task task = mTaskDao.findTaskByDate(date);

        if(task.getIs_finish()!=0){
            mTvFinish.setText("任务已完成");
        }
        else{
            mTvFinish.setText("任务未完成");
        }

        mTvStart.setText(task.getStartTime());
        mTvEnd.setText(task.getEndTime());
        mTvContent.setText(task.getContent());
        mTvPriority.setText(task.getPriority()+"");
    }

    public static void actionStart(Context context, String date){
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }
}
