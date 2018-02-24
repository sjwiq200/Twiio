package com.twiio.good.twiio;

import android.content.Context;
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

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.Schedule;
import com.twiio.good.twiio.thread.ListScheduleThread;

import java.util.List;
import java.util.Properties;

public class ListScheduleActivity extends AppCompatActivity {

    String userId;
    ListScheduleThread listScheduleThread;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){


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

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        listScheduleThread =  new ListScheduleThread(handler,userId,TWIIOurl);
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
