package com.scnu.learningboundless.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.scnu.learningboundless.R;
import com.gao.jiefly.nubiatimer.Timer;


public class TimerActivity extends AppCompatActivity {

    Timer timer;
    FloatingActionButton buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timer = (Timer) findViewById(R.id.timer);
        buttonStop = (FloatingActionButton) findViewById(R.id.btn_stop_timer);

        timer.setTime(0,0,10);  // 设置任务时间

        setTimeUpAction();  // 设置计时结束动作
        setTimerStopAction();  // 设置计时中断动作
        timer.startTimer();
    }

    /**
     * 这里设置计时结束后的动作
     */
    private void timeUpAction(){
        Toast.makeText(getApplicationContext(), "time up", Toast.LENGTH_SHORT).show();
    }

    /**
     * 这里计时中止后的动作
     */
    private void timerStopAction(){
        finish();
    }


    /**
     * 按下回退按键触发计时中止动作
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timerStopAction();
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


}
