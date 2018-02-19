package com.twiio.good.twiio;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.thread.AddMainPlanThread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddMainPlanActivity extends AppCompatActivity {

   private static final int OPEN_CAMERA=1;
   private static final int OPEN_GALLERY=0;

   String userId;
   boolean flag;
   boolean boo;

   EditText mainPlanTitle;
   EditText country;
   EditText departureDate;
   EditText arrivalDate;

   ImageView mainThumbnail;

   MainPlan mainPlan;

   Button addMainPlanDone;
   Button cancel;

   AddMainPlanThread addMainPlanThread;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            //Inflation
            boo = (boolean)message.obj;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmainplan);

        //===========================Layout===========================
        addMainPlanDone = (Button)findViewById(R.id.addMainPlan_done);
        cancel = (Button)findViewById(R.id.cancel);

        mainPlanTitle = (EditText)findViewById(R.id.title_mainplan);
        country = (EditText)findViewById(R.id.country_mainplan);
        departureDate = (EditText)findViewById(R.id.departure_date);
        arrivalDate = (EditText)findViewById(R.id.arrival_date);
        mainThumbnail = (ImageView)findViewById(R.id.thumbnail_mianplan);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        System.out.println("userId :: "+userId);
        //userNo = intent.getIntExtra("userNo",0);

        //===========================Thread Start===========================
        //listMainPlanThread = new ListMainPlanThread(handler,userId);
        //listMainPlanThread.start();

        //===========================addMainPlanDone Click Event===========================
        addMainPlanDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("departureDate :: "+departureDate.getText());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date departureDateSql = java.sql.Date.valueOf(departureDate.getText().toString());
                java.sql.Date arrivalDateSql = java.sql.Date.valueOf(arrivalDate.getText().toString());
                mainPlan = new MainPlan();
                String[] str =country.getText().toString().split(",");
                mainPlan.setCountryList(str);
                mainPlan.setPlanTitle(mainPlanTitle.getText().toString());
                mainPlan.setDepartureDate(departureDateSql);
                mainPlan.setArrivalDate(arrivalDateSql);

                System.out.println("userId :: :: "+userId);
                AddMainPlanThread addMainPlanThread = new AddMainPlanThread(handler, userId, mainPlan);
                addMainPlanThread.start();

                if(boo){
                    Intent intentAddMainPlan = new Intent(AddMainPlanActivity.this,ListMainPlanActivity.class);
                    intentAddMainPlan.putExtra("userId",userId);

                    startActivity(intentAddMainPlan);
                }
                //Intent intentAddMainPlan = new Intent(AddMainPlanActivity.this,)
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDate(calendar);
            }
        };

        //===========================departureDate Click Event===========================
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=true;
                new DatePickerDialog(AddMainPlanActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //===========================arrivalDate Click Event===========================
        arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                new DatePickerDialog(AddMainPlanActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //===========================mainThumbnail Click Event===========================
        mainThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] str ={"갤러리","카메라"};
                new AlertDialog.Builder(AddMainPlanActivity.this)
                        .setTitle("이미지 선택")
                        .setNegativeButton("취소",null)
                        .setItems(str, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getApplicationContext(),str[i]+i,Toast.LENGTH_SHORT).show();
                                if(i==0){
                                    openCamera();
                                }else if(i==1){
                                    openGallery();
                                }
                            }
                        })
                        .show();
            }
        });

        //===========================cancel Click Event===========================
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent,OPEN_CAMERA);
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(galleryIntent,OPEN_GALLERY);
    }

    private void updateDate(Calendar calendar){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);

        if(flag){
            departureDate.setText(sdf.format(calendar.getTime()));
        }else{
            arrivalDate.setText(sdf.format(calendar.getTime()));
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//            case OPEN_GALLERY:
//            {
//
//            }
//            case OPEN_CAMERA:
//            {
//
//            }
//        }
//    }

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
