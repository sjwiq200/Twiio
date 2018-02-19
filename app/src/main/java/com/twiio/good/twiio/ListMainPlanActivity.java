package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.thread.ListMainPlanThread;

import java.util.List;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class ListMainPlanActivity extends AppCompatActivity {

    ListMainPlanThread listMainPlanThread;
    String userId;

    Button addMainPlan;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            //Inflation
//            List<MainPlan> list = (List<MainPlan>)message.obj;
//            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ListMainPlanActivity.this, R.layout.activity_inflatelist,null); //new Layout
//            ScrollView scrollView = (ScrollView)findViewById(R.id.listMainPlanScroll);
//
//            for(final MainPlan mainPlan : list){
//                //TextView Dynamic Create
//                TextView textView = new TextView(ListMainPlanActivity.this);
//                textView.setText("mainPlanName = "+mainPlan.getPlanTitle()+" Country = " + mainPlan.getCountry()
//                                    +" tripDate = " + mainPlan.getDepartureDate() + "~" + mainPlan.getArrivalDate());
//
//                //Button Dynamic Create
//                Button button = new Button(ListMainPlanActivity.this);
//                button.setText(mainPlan.getMainPlanNo()+"보기");
//
//                //Button Dynamic Click Event
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //Toast.makeText(ListMainPlanActivity.this, mainPlan.getMainPlanNo(), Toast.LENGTH_SHORT).show();
//                        Intent intentMainPlan = new Intent(ListMainPlanActivity.this,ListDailyPlanActivity.class);
//                        intentMainPlan.putExtra("userId", userId);
//                        intentMainPlan.putExtra("mainPlanNo", mainPlan.getMainPlanNo());
//
//                        startActivity(intentMainPlan);
//                    }
//                });
//
//                insertLinearLayout.addView(textView);
//                insertLinearLayout.addView(button);
//            }
//            insertLinearLayout.setGravity(Gravity.CENTER);
//            scrollView.addView(insertLinearLayout);

            List<MainPlan> list = (List<MainPlan>)message.obj;
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ListMainPlanActivity.this, R.layout.activity_inflatelist,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.listMainPlanScroll);

            for(final MainPlan mainPlan : list){
                LinearLayout customLinearLayout = (LinearLayout)View.inflate(ListMainPlanActivity.this, R.layout.activity_custom_inflate_listmainplan,null); //new Layout
                LinearLayout mainLinear = (LinearLayout)customLinearLayout.findViewById(R.id.mainPlanMainLinear);
                LinearLayout firstLinear = (LinearLayout)customLinearLayout.findViewById(R.id.mainPlanFirstLinear);
                LinearLayout secondLinear = (LinearLayout)customLinearLayout.findViewById(R.id.mainPlanSecondLinear);
                RelativeLayout relativeLayout = (RelativeLayout)customLinearLayout.findViewById(R.id.mainPlanRelat);
                ImageView imageView = (ImageView)customLinearLayout.findViewById(R.id.image);
                TextView title = (TextView)customLinearLayout.findViewById(R.id.titleName);
                TextView date = (TextView)customLinearLayout.findViewById(R.id.date);
                TextView country = (TextView)customLinearLayout.findViewById(R.id.country);
                TextView city = (TextView)customLinearLayout.findViewById(R.id.city);

                title.setText(mainPlan.getPlanTitle());
                date.setText(mainPlan.getDepartureDate()+"");
                country.setText(mainPlan.getCountry());
                city.setText(mainPlan.getCity());
                //imageView.setImageURI(mainPlan.getMainThumbnail());
                System.out.println("썸네일:::"+mainPlan.getMainThumbnail());
                System.out.println("나라나라:::"+mainPlan.getCountry());

                //Button Dynamic Click Event
                mainLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(ListMainPlanActivity.this, mainPlan.getMainPlanNo(), Toast.LENGTH_SHORT).show();
                        Intent intentMainPlan = new Intent(ListMainPlanActivity.this,ListDailyPlanActivity.class);
                        intentMainPlan.putExtra("userId", userId);
                        intentMainPlan.putExtra("mainPlanNo", mainPlan.getMainPlanNo());

                        startActivity(intentMainPlan);
                    }
                });
                ((ViewGroup)imageView.getParent()).removeView(imageView);
                ((ViewGroup)title.getParent()).removeView(title);
                ((ViewGroup)date.getParent()).removeView(date);
                ((ViewGroup)country.getParent()).removeView(country);
                ((ViewGroup)city.getParent()).removeView(city);
                ((ViewGroup)relativeLayout.getParent()).removeView(relativeLayout);
                ((ViewGroup)firstLinear.getParent()).removeView(firstLinear);
                ((ViewGroup)secondLinear.getParent()).removeView(secondLinear);

                firstLinear.addView(imageView);
                relativeLayout.addView(title);
                relativeLayout.addView(date);
                relativeLayout.addView(country);
                relativeLayout.addView(city);
                secondLinear.addView(relativeLayout);
                mainLinear.addView(firstLinear);
                mainLinear.addView(secondLinear);

                ViewGroup parent = (ViewGroup) mainLinear.getParent();
                if(parent != null){
                    parent.removeView(mainLinear);
                }

                insertLinearLayout.addView(mainLinear);
            }
            insertLinearLayout.setGravity(Gravity.CENTER);
            scrollView.addView(insertLinearLayout);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmainplan);

        //===========================Layout===========================
        addMainPlan = (Button)findViewById(R.id.addMainPlanButton);

        //===========================Intent===========================
        final Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        //userNo = intent.getIntExtra("userNo",0);

        //===========================Thread Start===========================
        listMainPlanThread = new ListMainPlanThread(handler,userId);
        listMainPlanThread.start();

        //===========================addMainPlan Click Event===========================
        addMainPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddMainPlan = new Intent(ListMainPlanActivity.this,AddMainPlanActivity.class);
                intentAddMainPlan.putExtra("userId",userId);

                startActivity(intentAddMainPlan);
            }
        });

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
