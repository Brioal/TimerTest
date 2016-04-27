#三种定时器的使用
##Android中使用定时器的三种方法学习与整理
###按惯例是图示:
![这里写图片描述](http://img.blog.csdn.net/20160412003443942)

###因为都比较简单,所以就直接贴代码(虑去再次点击停止的操作),有个全局的`Handler`负责接收消息更新`UI`
###第一种,`Thread.sleep();`方法
```
Runnable runnable = new Runnable() {
       @Override
    public void run() {
     while (true) {
     mHandler.sendEmptyMessage(0);
  try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
       e.printStackTrace();
          }
          }
       }
     };
new Thread(runnable).start();
}
```
###第二种方法:`Handler`的`postDelay()`方法
```
final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isStart2) {
                    mHandler.sendEmptyMessage(0);
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
mHandler.postDelayed(runnable, 1000);
        }
```
###第三种:`Timer`和`TimerTask`
```
 private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    };
 timer.schedule(timerTask, 1000, 1000);
```


###总的来说第三种方法最方便,不易出错,第二种容易忘记添加出发事件.
###贴一下完整代码:
####布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.brioal.timertest.MainActivity">

    <TextView
        android:id="@+id/main_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="100dp"
        android:text="Hello World!" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/main_tv"
        android:layout_marginTop="100dp"
        android:gravity="center"
        android:orientation="vertical">

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="5dp"
            android:onClick="Method1"
            android:text="方法1:Thread"
            android:textAllCaps="false" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="5dp"
            android:onClick="Method2"
            android:text="方法2:Handler"
            android:textAllCaps="false" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="5dp"
            android:onClick="Method3"
            android:text="方法3:Task"
            android:textAllCaps="false" />
    </LinearLayout>
</RelativeLayout>

```
###`MainActivity`
```
package com.brioal.timertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

```

