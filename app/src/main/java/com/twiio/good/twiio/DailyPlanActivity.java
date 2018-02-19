package com.twiio.good.twiio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twiio.good.twiio.domain.DailyPlan;
import com.twiio.good.twiio.domain.PlanContent;
import com.twiio.good.twiio.thread.AddTextThread;
import com.twiio.good.twiio.thread.DailyPlanThread;

import java.util.List;
import java.util.Map;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class DailyPlanActivity extends AppCompatActivity {

    String userId;
    int mainPlanNo;
    int dailyPlanNo;
    DailyPlanThread dailyPlanThread;
    Boolean check = false;

    private Button mButton;
    final Context c = this;
    EditText textInput;
    String imageUrl = "http://192.168.0.45:8080/resources/images/dailyPlanContent/";

    DailyPlan dailyPlan;

    TextView date;
    TextView country;
    TextView city;
    TextView mainDay;
    String day;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //Inflation
//            Map<String, Object> map = (Map<String, Object>)msg.obj;
//
//            //ObjectMapper objectMapperResult = new ObjectMapper();
//            dailyPlan = (DailyPlan)map.get("dailyPlan");
//            //DailyPlan dailyPlan = objectMapperResult.readValue(((Map<String, Object>) msg.obj).get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});
//
//            List<PlanContent> list = (List<PlanContent>) map.get("list");
//            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(DailyPlanActivity.this,R.layout.activity_inflatelist,null); //new Layout
//            ScrollView scrollView = (ScrollView)findViewById(R.id.dailyPlanScroll);
//
//            TextView textView = new TextView(DailyPlanActivity.this);
//            textView.setText("DAY"+dailyPlan.getDay()+"  Date :: " + dailyPlan.getDailyDate()+"  Country :: "+dailyPlan.getDailyCountry()+"  City :: "+dailyPlan.getDailyCity());
//
//            insertLinearLayout.addView(textView);
//
//            for(final PlanContent planContent : list){
//                TextView textPlanContentView = new TextView(DailyPlanActivity.this);
//
//                if(planContent.getContentText()!=null){
//                    textPlanContentView.setText(Html.fromHtml(planContent.getContentText()));
//                }
//
//                if(planContent.getContentImage()!=null){
//
//                    ImageView imageView = new ImageView(DailyPlanActivity.this);
//                    Picasso.with(DailyPlanActivity.this).load(imageUrl+planContent.getContentImage()).into(imageView);
//                    insertLinearLayout.addView(imageView);
//
//                }
//
//                if(planContent.getRoute()!=null){
//
//                    textPlanContentView.setText(Html.fromHtml("\n\n"+planContent.getRoute()));
//                    textPlanContentView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
//                            View mView = layoutInflaterAndroid.inflate(R.layout.route_dialog_box, null);
//
//                            String[] route=planContent.getRouteDescription().split(".png");
//                            AlertDialog.Builder alertDialogBuilderRoute = new AlertDialog.Builder(c);
//                            alertDialogBuilderRoute.setView(mView);
//                            final TextView routeInputDialog = (TextView) mView.findViewById(R.id.routeInputDialog);
//                            routeInputDialog.setText(Html.fromHtml(planContent.getRouteDescription()));
//                            alertDialogBuilderRoute.setNegativeButton("확인",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialogBox, int id) {
//                                                    dialogBox.cancel();
//                                                }
//                                            });
//
//                            AlertDialog alertDialogAndroid = alertDialogBuilderRoute.create();
//                            alertDialogAndroid.show();
//                        }
//                    });
//                }
//
//                if(planContent.getMapName() !=null){
//                    ImageView imageView = new ImageView(DailyPlanActivity.this);
//                    Picasso.with(DailyPlanActivity.this).load(planContent.getMapImage()).resize(800, 800).centerCrop().into(imageView);
//                    insertLinearLayout.addView(imageView);
//
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String url = planContent.getMapUrl();
//                            System.out.println("###DEBUG : " + url);
//                            Intent i = new Intent(Intent.ACTION_VIEW);
//                            i.setData(Uri.parse(url));
//                            startActivity(i);
//                        }
//                    });
//
//                    textPlanContentView.setText("[" + planContent.getMapName()+" ]"+"\n전체주소: " + planContent.getMapAddress() + "\n전화번호 : " + planContent.getMapPhone() + "\n웹사이트 : " + planContent.getMapWebsite());
//
//
//                }
//
//                insertLinearLayout.addView(textPlanContentView);
//            }
//
//            insertLinearLayout.setGravity(Gravity.CENTER);
//
//            scrollView.addView(insertLinearLayout);
//
//
//
//            //===========================Text Input Dialog ===========================
//            mButton = (Button) findViewById(R.id.openUserInputDialog);
//            mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
//                    View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
//                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
//                    alertDialogBuilderUserInput.setView(mView);
//
//                    final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
//                    alertDialogBuilderUserInput
//                            .setCancelable(false)
//                            .setPositiveButton("완성", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialogBox, int id) {
//                                    check=true;
//                                    String text = userInputDialogEditText.getText().toString();
//                                    PlanContent planContent = new PlanContent();
//                                    planContent.setDailyPlan(dailyPlan);
//                                    planContent.setContentText(text);
//                                    planContent.setContentType(1);
//                                    AddTextThread addTextThread = new AddTextThread(handler,planContent);
//                                    addTextThread.start();
//                                    System.out.println("### " + userInputDialogEditText.getText().toString());
//                                    if(check) {
//                                        Intent intentMainPlan = new Intent(DailyPlanActivity.this, DailyPlanActivity.class);
//                                        intentMainPlan.putExtra("userId", userId);
//                                        intentMainPlan.putExtra("mainPlanNo", mainPlanNo);
//                                        startActivity(intentMainPlan);
//                                        finish();
//                                    }
//
//                                }
//                            })
//
//                            .setNegativeButton("취소",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialogBox, int id) {
//                                            dialogBox.cancel();
//                                        }
//                                    });
//
//                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
//                    alertDialogAndroid.show();

            Map<String, Object> map = (Map<String, Object>)msg.obj;

            //ObjectMapper objectMapperResult = new ObjectMapper();
            dailyPlan = (DailyPlan)map.get("dailyPlan");
            //DailyPlan dailyPlan = objectMapperResult.readValue(((Map<String, Object>) msg.obj).get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});

            List<PlanContent> list = (List<PlanContent>) map.get("list");
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(DailyPlanActivity.this,R.layout.activity_inflatelist,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.dailyPlanScroll);

            mainDay.setText(day);
            date.setText(dailyPlan.getDailyDate()+"");
            country.setText(dailyPlan.getDailyCountry());
            city.setText(dailyPlan.getDailyCity());

            for(final PlanContent planContent : list){
                TextView textPlanContentView = new TextView(DailyPlanActivity.this);

                if(planContent.getContentText()!=null){
                    textPlanContentView.setText(Html.fromHtml(planContent.getContentText()));
                }

                if(planContent.getContentImage()!=null){

                    ImageView imageView = new ImageView(DailyPlanActivity.this);
                    Picasso.with(DailyPlanActivity.this).load(imageUrl+planContent.getContentImage()).into(imageView);

                    insertLinearLayout.addView(imageView);


                }

                if(planContent.getRoute()!=null){

                    textPlanContentView.setText(Html.fromHtml("\n\n"+planContent.getRoute()));
                    textPlanContentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                            View mView = layoutInflaterAndroid.inflate(R.layout.route_dialog_box, null);

                            String[] route=planContent.getRouteDescription().split(".png");
                            AlertDialog.Builder alertDialogBuilderRoute = new AlertDialog.Builder(c);
                            alertDialogBuilderRoute.setView(mView);
                            final TextView routeInputDialog = (TextView) mView.findViewById(R.id.routeInputDialog);
                            routeInputDialog.setText(Html.fromHtml(planContent.getRouteDescription()));
                            alertDialogBuilderRoute.setNegativeButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    });

                            AlertDialog alertDialogAndroid = alertDialogBuilderRoute.create();
                            alertDialogAndroid.show();
                        }
                    });
                }

                if(planContent.getMapName() !=null){
                    ImageView imageView = new ImageView(DailyPlanActivity.this);
                    Picasso.with(DailyPlanActivity.this).load(planContent.getMapImage()).resize(800, 800).centerCrop().into(imageView);
                    insertLinearLayout.addView(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = planContent.getMapUrl();
                            System.out.println("###DEBUG : " + url);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });

                    textPlanContentView.setText("[" + planContent.getMapName()+" ]"+"\n전체주소: " + planContent.getMapAddress() + "\n전화번호 : " + planContent.getMapPhone() + "\n웹사이트 : " + planContent.getMapWebsite());


                }

                insertLinearLayout.addView(textPlanContentView);
            }

            insertLinearLayout.setGravity(Gravity.CENTER);

            scrollView.addView(insertLinearLayout);



            //===========================Text Input Dialog ===========================
            mButton = (Button) findViewById(R.id.openUserInputDialog);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                    View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                    alertDialogBuilderUserInput.setView(mView);

                    final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setPositiveButton("완성", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    check=true;
                                    String text = userInputDialogEditText.getText().toString();
                                    PlanContent planContent = new PlanContent();
                                    planContent.setDailyPlan(dailyPlan);
                                    planContent.setContentText(text);
                                    planContent.setContentType(1);
                                    AddTextThread addTextThread = new AddTextThread(handler,planContent);
                                    addTextThread.start();
                                    System.out.println("### " + userInputDialogEditText.getText().toString());
                                    if(check) {
                                        Intent intentMainPlan = new Intent(DailyPlanActivity.this, DailyPlanActivity.class);
                                        intentMainPlan.putExtra("userId", userId);
                                        intentMainPlan.putExtra("mainPlanNo", mainPlanNo);
                                        startActivity(intentMainPlan);
                                        finish();
                                    }

                                }
                            })

                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    });

                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                    alertDialogAndroid.show();
                }
            });

        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dailyplan);

        //===========================Layout===========================
        date = (TextView)findViewById(R.id.date);
        country = (TextView)findViewById(R.id.country);
        city = (TextView)findViewById(R.id.city);
        mainDay = (TextView)findViewById(R.id.mainDay);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        mainPlanNo = intent.getIntExtra("mainPlanNo",0);
        dailyPlanNo = intent.getIntExtra("dailyPlanNo",0);
        day = intent.getStringExtra("day");

        //===========================Thread Start===========================
        dailyPlanThread = new DailyPlanThread(handler,dailyPlanNo);
        dailyPlanThread.start();

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