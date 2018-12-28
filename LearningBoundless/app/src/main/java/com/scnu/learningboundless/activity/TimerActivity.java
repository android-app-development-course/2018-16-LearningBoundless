package com.scnu.learningboundless.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gao.jiefly.nubiatimer.Timer;
import com.scnu.learningboundless.R;
import com.scnu.learningboundless.bean.task.Task;
import com.scnu.learningboundless.db.TaskDao;


public class TimerActivity extends AppCompatActivity {

    Timer timer;
    FloatingActionButton buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timer = (Timer) findViewById(R.id.timer);
        buttonStop = (FloatingActionButton) findViewById(R.id.btn_stop_timer);
//        timer.setTime(0,0,10);  // 设置任务时间
        setTime();
        setTimeUpAction();  // 设置计时结束动作
        setTimerStopAction();  // 设置计时中断动作
        timer.startTimer();
    }

    /**
     * 这里设置计时结束后的动作
     */
    private void timeUpAction(){
        Toast.makeText(getApplicationContext(), "time up", Toast.LENGTH_SHORT).show();
        String startTime = getIntent().getStringExtra("startTime");
        TaskDao taskDao = new TaskDao(this);
        Task task = taskDao.findTaskByDate(startTime);
        task.setIs_finish(1);
        taskDao.updateTask(startTime, task);
        finish();
    }

    /**
     * 这里计时中止后的动作
     */
    private void timerStopAction(){
        finish();
    }

    /**
     * 添加计时结束动作
     */
    private void setTimeUpAction(){
        timer.setOnTimeUpListener(new Timer.OnTimeUpListener() {
            @Override
            public void onTimeUp() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeUpAction();
                    }
                });

            }
        });
    }

    private void setTimerStopAction(){
        buttonStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                timerStopAction();
            }
        });
    }

    public static void actionStart(Context context, long date, String startTime) {
        Intent intent = new Intent(context, TimerActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("startTime", startTime);
        context.startActivity(intent);
        Log.d("sean", "actionStart: "+String.valueOf(date));
        Toast.makeText(context, String.valueOf(date), Toast.LENGTH_SHORT);
    }

    public void setTime(){
        long time = getIntent().getLongExtra("date", 1000);
        time = time / 1000;
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600);
        this.timer.setTime(h,m,s);
    }
}