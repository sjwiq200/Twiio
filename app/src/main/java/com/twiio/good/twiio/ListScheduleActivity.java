package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twiio.good.twiio.domain.Schedule;
import com.twiio.good.twiio.thread.ListScheduleThread;

import java.util.List;

public class ListScheduleActivity extends AppCompatActivity {

    String userId;
    ListScheduleThread listScheduleThread;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            List<Schedule> list = (List<Schedule>)message.obj;
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ListScheduleActivity.this,R.layout.activity_inflatelist,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.listScheduleScroll);
            for(Schedule schedule : list){
                TextView textView = new TextView(ListScheduleActivity.this);
                textView.setText("scheduleTitle = "+schedule.getScheduleTitle()+"Country = " + schedule.getCountry());

                insertLinearLayout.addView(textView);
            }
            insertLinearLayout.setGravity(Gravity.CENTER);
            scrollView.addView(insertLinearLayout);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listschedule);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        listScheduleThread =  new ListScheduleThread(handler,userId);
        listScheduleThread.start();


    }

    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
