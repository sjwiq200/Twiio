package com.twiio.good.twiio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.thread.GetUserThread;

import java.util.Properties;

public class GetUserActivity extends AppCompatActivity {
    String userId;
    GetUserThread getUserThread;

    TextView getUserName;
    TextView getUserBirthday;
    TextView getUserPhone;
    TextView getUserHost;
    TextView getUserRegDate;
    TextView getUserEmail;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;

    private Handler handler = new Handler() {
      public void handleMessage(Message message){
          User user = (User)message.obj;
          getUserName.setText(user.getUserName());
          if(user.getUserBirthday()!=null){
            getUserBirthday.setText(user.getUserBirthday());
          }else{
              getUserBirthday.setText("-");
          }
          if(user.getUserPhone()!=null) {
              getUserPhone.setText(user.getUserPhone().toString());
          }else{
              getUserPhone.setText("-");
          }
          getUserHost.setText(user.getUserEval()+"");
          getUserRegDate.setText(user.getRegDate().toString());
          getUserEmail.setText(user.getUserEmail().toString());

      }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");


        //===========================Layout===========================
        getUserName = (TextView)findViewById(R.id.getUserName);
        getUserBirthday = (TextView)findViewById(R.id.getUserBirthday);
        getUserPhone = (TextView)findViewById(R.id.getUserPhone);
        getUserHost = (TextView)findViewById(R.id.getUserHost);
        getUserRegDate = (TextView)findViewById(R.id.getUserRegDate);
        getUserEmail = (TextView)findViewById(R.id.getUserEmail);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        getUserThread = new GetUserThread(handler,userId,TWIIOurl);
        getUserThread.start();


    }

    protected void onDestroy(){
        super.onDestroy();
        getUserThread.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
