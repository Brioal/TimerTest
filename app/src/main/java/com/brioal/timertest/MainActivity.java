package com.brioal.timertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.brioal.timertest.util.BrioalUtil;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //收到消息后显示当前时间
            long current = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = dateFormat.format(current);
            mTv.setText(time);
        }
    };
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    };
    private Thread thread1;
    private boolean isStart1 = false;
    private boolean isStart2 = false;
    private boolean isStart3 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BrioalUtil.init(this);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.main_tv);
    }

    //Thread方法
    public void Method1(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (isStart1) {
                    mHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        if (isStart1) {
            isStart1 = false;
        } else {
            isStart1 = true;
            thread1 = new Thread(runnable);
            thread1.start();
        }
    }

    public void Method2(View view) {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isStart2) {
                    mHandler.sendEmptyMessage(0);
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        if (isStart2) {
            isStart2 = false;
        } else {
            mHandler.postDelayed(runnable, 1000);
            isStart2 = true;
        }
    }

    public void Method3(View view) {
        if (isStart3) {
            timer.cancel();
            isStart3 = false;
        } else {
            timer.schedule(timerTask, 1000, 1000);
            isStart3 = true;
        }
    }
}
