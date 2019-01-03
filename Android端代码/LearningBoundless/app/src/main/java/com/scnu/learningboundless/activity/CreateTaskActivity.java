package com.scnu.learningboundless.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scnu.learningboundless.R;
import com.scnu.learningboundless.bean.task.Task;
import com.scnu.learningboundless.db.TaskDao;
import com.scnu.learningboundless.fragment.TaskFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends AppCompatActivity {

    //开始,结束时间
    @BindView(R.id.et_start_time)
    EditText mTvStart;

    @BindView(R.id.et_end_time)
    EditText mTvEnd;

    @BindView(R.id.et_content)
    EditText mEtContent;

    @BindView(R.id.et_priority)
    EditText mEtPriority;

    @BindView(R.id.btn_save)
    Button mBtnSave;

    private static int mStartYear;
    private static int mStartMonth;
    private static int mStartDay;
    private static int mStartHour;
    private static int mStartMinute;

    private static int mEndYear;
    private static int mEndMonth;
    private static int mEndDay;
    private static int mEndHour;
    private static int mEndMinute;

    private boolean isEditContent = false;
    private boolean isEditCard = false;

    private TaskDao mTaskDao;

    private String mContent;
    private String mStartDate;
    private String mEndDate;
    private int mPriority=1;
    private int mFinish=0;

    static {
        Calendar calendar = Calendar.getInstance();
        mStartYear = calendar.get(Calendar.YEAR);
        mStartMonth = calendar.get(Calendar.MONTH);
        mStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        mStartHour= calendar.get(Calendar.HOUR_OF_DAY);
        mStartMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_create);

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        mTvStart.setCursorVisible(false);
        mTvStart.setFocusable(false);
        mTvStart.setFocusableInTouchMode(false);

        mTvEnd.setCursorVisible(false);
        mTvEnd.setFocusable(false);
        mTvEnd.setFocusableInTouchMode(false);

        mTaskDao = new TaskDao(this);

        String startdata = mStartYear+"-"+(mStartMonth + 1)+"-"+mStartDay+" "+mStartHour+":" +mStartMinute;
        mTvStart.setText(startdata);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            String date = getIntent().getStringExtra("date");

            // 进入编辑界面
            if (date != null) {
                isEditCard = true;

                actionBar.setTitle(getResources().getString(R.string.edit_card));

                Task task = mTaskDao.findTaskByDate(date);

                mTvStart.setText(task.getStartTime());
                mTvEnd.setText(task.getEndTime());
                mEtContent.setText(task.getContent());
                mEtPriority.setText(task.getPriority()+"");
            }
            // 进入创建界面
            else {
                actionBar.setTitle(getResources().getString(R.string.create_card));
            }
        }
    }


    @OnClick({R.id.et_start_time, R.id.et_end_time, R.id.btn_save, R.id.iv_back, R.id.tv_back})
    public void handleAllClick(View v) {
        switch (v.getId()) {
            case R.id.et_start_time:
                selectDate(true);
                break;

            case R.id.et_end_time:
                selectDate(false);
                break;

            case R.id.btn_save:
                saveMoodCard();
                break;

            case R.id.iv_back:
                finish();

            case R.id.tv_back:
                finish();

            default:
                break;
        }
    }


    private void selectDate(final boolean isStartTime) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

               if(isStartTime){
                   mStartYear = year;
                   mStartMonth = month;
                   mStartDay = dayOfMonth;
                   selectTime(true);
               }else {
                   mEndYear = year;
                   mEndMonth = month;
                   mEndDay = dayOfMonth;
                   selectTime(false);
               }

            }
        }, mStartYear, mStartMonth, mStartDay);

        datePickerDialog.show();
    }

    private void selectTime(final boolean isStartTime) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){

                if(isStartTime){
                    mStartHour = hourOfDay;
                    mStartMinute = minute;
                    String startData = mStartYear+"-"+(mStartMonth + 1)+"-"+mStartDay+" "+mStartHour+":" +mStartMinute;
                    mTvStart.setText(startData);
                }else {
                    mEndHour = hourOfDay;
                    mEndMinute = minute;
                    String endData = mEndYear+"-"+(mEndMonth + 1)+"-"+mEndDay+" "+mEndHour+":" +mEndMinute;
                    mTvEnd.setText(endData);
                }
            }
        }, mStartHour, mStartMinute ,true);

        timePickerDialog.show();
    }


    private void saveMoodCard() {
        if(mTvStart.getText().toString().equals(mTvEnd.getText().toString())){
            Toast.makeText(this, getResources().getString(R.string.time_similar), Toast.LENGTH_SHORT).show();
            return;
        }
        // 当前为心情卡片创建界面
        if (!isEditCard) {

            isEditContent = "".equals( mEtContent.getText().toString() );
            if (isEditContent) {
                Toast.makeText(this, getResources().getString(R.string.please_input_content), Toast.LENGTH_SHORT).show();
                return;
            }

            mStartDate=mTvStart.getText().toString();
            mEndDate=mTvEnd.getText().toString();
//            mStartDate = mStartYear+"-"+(mStartMonth + 1)+"-"+mStartDay+" "+mStartHour+":" +mStartMinute;
//            mEndDate = mStartYear+"-"+(mStartMonth + 1)+"-"+mStartDay+" "+mStartHour+":" +mStartMinute;
            mContent = mEtContent.getText().toString();
            String num = mEtPriority.getText().toString();
            if( ! "".equals(num)){
                mPriority = Integer.valueOf(num);
            }

            final Task task = new Task(mContent, mStartDate, mEndDate,mPriority,mFinish);

            // 日期 mDate 已有数据
            if (mTaskDao.findTaskByDate(mStartDate) != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(mStartDate + " " + getResources().getString(R.string.is_override));
                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTaskDao.addTask(task);
                        TaskFragment.mTaskAdapter.replaceTask(task);
                        Toast.makeText(CreateTaskActivity.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CreateTaskActivity.this, getResources().getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            } else {
                mTaskDao.addTask(task);
                TaskFragment.mTaskAdapter.addTask(task);
                Toast.makeText(this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();

                finish();
            }
        }
        // 当前为心情卡片编辑界面
        else {
            mStartDate = mTvStart.getText().toString();
            mEndDate = mTvEnd.getText().toString();
            mContent = mEtContent.getText().toString();
            mPriority = Integer.valueOf( mEtPriority.getText().toString() );
            final Task task = new Task(mContent, mStartDate, mEndDate,mPriority,0);

            // 日期 date 已有数据
            if (mTaskDao.findTaskByDate(mStartDate) != null && !mStartDate.equals(getIntent().getStringExtra("date"))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(mStartDate + " " + getResources().getString(R.string.existed_data));
                builder.setPositiveButton(getResources().getString(R.string.ok), null);
                builder.show();
            } else {
                mTaskDao.updateTask(getIntent().getStringExtra("date"),task);
                TaskFragment.mTaskAdapter.updateTask(getIntent().getStringExtra("date"), task);
                Toast.makeText(CreateTaskActivity.this, getResources().getString(R.string.modify_success), Toast.LENGTH_SHORT).show();

                finish();
            }
        }
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

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, CreateTaskActivity.class));
    }

    public static void actionEditStart(Context context, String date) {
        Intent intent = new Intent(context, CreateTaskActivity.class);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }
}
