package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.thread.GetUserThread;

public class GetUserActivity extends AppCompatActivity {
    String userId;
    GetUserThread getUserThread;

    TextView getUserId;
    TextView getUserName;
    TextView getUserRegDate;

    private Handler handler = new Handler() {
      public void handleMessage(Message message){
          User user = (User)message.obj;
          getUserId.setText(user.getUserId());
          getUserName.setText(user.getUserName());
          getUserRegDate.setText(user.getRegDate().toString());

      }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);

        //===========================Layout===========================
        getUserId = (TextView)findViewById(R.id.getUserId);
        getUserName = (TextView)findViewById(R.id.getUserName);
        getUserRegDate = (TextView)findViewById(R.id.getUserRegDate);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        getUserThread = new GetUserThread(handler,userId);
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
