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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.thread.ListMainPlanThread;

import java.util.List;
import java.util.Properties;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class ListMainPlanActivity extends AppCompatActivity {

    ListMainPlanThread listMainPlanThread;
    String userId;

    Button addMainPlan;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;


    //========================daeun editing-===================================
//    String imageUrl = "http://192.168.0.33:8080/resources/images/thumbnail_plan/";
    String imageUrl;
    //=========================================================================

    private Handler handler = new Handler(){
        public void handleMessage(Message message){

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

                //=================================daeun editing=============================
                if(mainPlan.getMainThumbnail()!=null){
                    /////////////////*dayoung add for imageView-picasso<START> *////////////////
                    System.out.println("들어오니" + mainPlan.getMainThumbnail());
                    Picasso.with(ListMainPlanActivity.this).load(imageUrl+mainPlan.getMainThumbnail()).into(imageView);
                    //////////////*dayoung add for imageView-picasso<END> *////////////////
                }
                //=================================daeun editing=============================

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
                /////////////////*dayoung add for imageView-picasso<START> */////////////////
                if((ViewGroup)imageView.getParent() != null) {
                    ((ViewGroup) imageView.getParent()).removeView(imageView);
                    relativeLayout.addView(imageView);
                }
                /////////////// /*dayoung add for imageView-picasso<END> *//////////////////
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

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");

        imageUrl = TWIIOurl+":8080/resources/images/thumbnail_plan/";

        //===========================Layout===========================
        addMainPlan = (Button)findViewById(R.id.addMainPlanButton);

        //===========================Intent===========================
        final Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        //userNo = intent.getIntExtra("userNo",0);

        //===========================Thread Start===========================
        listMainPlanThread = new ListMainPlanThread(handler,userId,TWIIOurl);
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
