package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twiio.good.twiio.domain.DailyPlan;
import com.twiio.good.twiio.thread.ListDailyPlanThread;

import java.util.List;
import java.util.Map;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class ListDailyPlanActivity extends AppCompatActivity {

    ListDailyPlanThread listDailyPlanThread;
    String userId;
    int mainPlanNo;


    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            //Inflation
            Map<String, Object> map = (Map<String, Object>)message.obj;

            List<DailyPlan> list = (List<DailyPlan>)map.get("list");
            String[] cityList = (String[])map.get("cityList");
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ListDailyPlanActivity.this, R.layout.activity_inflatelist,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.listDailyPlanScroll);

            for(final DailyPlan dailyPlan : list){
                //TextView Dynamic Create
                TextView textView = new TextView(ListDailyPlanActivity.this);
                textView.setText("dailyDate = "+dailyPlan.getDailyDate()+" Country = " + dailyPlan.getDailyCountry()
                                    +" city = " + dailyPlan.getDailyCity() + " dailyPlanNo = " + dailyPlan.getDailyPlanNo());

                //Button Dynamic Create
                Button button = new Button(ListDailyPlanActivity.this);
                button.setText("DAY"+dailyPlan.getDay()+" 보기 ::");

                //Button Dynamic Click Event
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(ListMainPlanActivity.this, mainPlan.getMainPlanNo(), Toast.LENGTH_SHORT).show();
                        Intent intentDailyPlan = new Intent(ListDailyPlanActivity.this,DailyPlanActivity.class);
                        intentDailyPlan.putExtra("userId", userId);
                        intentDailyPlan.putExtra("mainPlanNo", mainPlanNo);
                        intentDailyPlan.putExtra("dailyPlanNo", dailyPlan.getDailyPlanNo());

                        startActivity(intentDailyPlan);
                    }
                });

                insertLinearLayout.addView(textView);
                insertLinearLayout.addView(button);
            }
            insertLinearLayout.setGravity(Gravity.CENTER);
            scrollView.addView(insertLinearLayout);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdailyplan);

        //===========================Intent===========================
        final Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        mainPlanNo = intent.getIntExtra("mainPlanNo",0);
        //userNo = intent.getIntExtra("userNo",0);

        //===========================Thread Start===========================
        listDailyPlanThread = new ListDailyPlanThread(handler,mainPlanNo);
        listDailyPlanThread.start();

        //===========================addMainPlan Click Event===========================
//        addMainPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentAddMainPlan = new Intent(ListDailyPlanActivity.this,AddMainPlanActivity.class);
//                intentAddMainPlan.putExtra("userId",userId);
//
//                startActivity(intentAddMainPlan);
//            }
//        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
