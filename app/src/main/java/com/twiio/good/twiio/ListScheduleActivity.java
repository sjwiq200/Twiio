package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

            /*
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

            */

            List<Schedule> list = (List<Schedule>)message.obj;
            ScrollView scrollView = (ScrollView)findViewById(R.id.listScheduleScroll);
            LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ListScheduleActivity.this, R.layout.activity_inflatelist,null); //new Layout

            for(Schedule schedule : list){
                LinearLayout customLinearLayout = (LinearLayout)View.inflate(ListScheduleActivity.this, R.layout.activity_custom_inflate_schedule,null); //new Layout
                RelativeLayout relativeLayout = (RelativeLayout)customLinearLayout.findViewById(R.id.scheduleListRelat);
                RelativeLayout relativeLayout2 = (RelativeLayout)customLinearLayout.findViewById(R.id.scheduleListRelat2);
                TextView scheduleTitle = (TextView)customLinearLayout.findViewById(R.id.scheduleTitleText);
                TextView scheduleDate = (TextView)customLinearLayout.findViewById(R.id.scheduleDateText);
                TextView scheduleAdd = (TextView)customLinearLayout.findViewById(R.id.schedulelocationText);
                ImageView img = (ImageView)customLinearLayout.findViewById(R.id.schedulelocationImg);

                scheduleTitle.setText(schedule.getScheduleTitle());
                scheduleDate.setText(schedule.getScheduleDate());
                scheduleAdd.setText(schedule.getScheduleAddress());
                System.out.println("일정::"+schedule);

                ((ViewGroup)scheduleAdd.getParent()).removeView(scheduleAdd);
                ((ViewGroup)img.getParent()).removeView(img);
                relativeLayout2.addView(img);
                relativeLayout2.addView(scheduleAdd);

                ((ViewGroup)relativeLayout2.getParent()).removeView(relativeLayout2);
                ((ViewGroup)scheduleDate.getParent()).removeView(scheduleDate);
                ((ViewGroup)scheduleTitle.getParent()).removeView(scheduleTitle);
                relativeLayout.addView(scheduleDate);
                relativeLayout.addView(scheduleTitle);
                relativeLayout.addView(relativeLayout2);

                ViewGroup parent = (ViewGroup) relativeLayout.getParent();
                if(parent != null){
                    parent.removeView(relativeLayout);
                }
                System.out.println("뀨");
                insertLinearLayout.addView(relativeLayout);
                insertLinearLayout.setGravity(Gravity.CENTER);
            }
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
