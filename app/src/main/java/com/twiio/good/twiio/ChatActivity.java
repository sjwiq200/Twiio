package com.twiio.good.twiio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.thread.ChatThread;

import java.util.Properties;

public class ChatActivity extends AppCompatActivity {

    Button listRoom;
    Button listMyRoom;
    Button listSchedule;

    String userId;
    int userNo;

    ChatThread chatThread;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;

    private Handler handlerUser = new Handler(){
      public void handleMessage(Message message) {
          userNo = ((User)message.obj).getUserNo();
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");

        //===========================Layout===========================
        listRoom = (Button)findViewById(R.id.listRoom);
        listMyRoom = (Button)findViewById(R.id.listMyRoom);
        listSchedule = (Button) findViewById(R.id.listSchedule);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        chatThread = new ChatThread(handlerUser, userId, TWIIOurl);
        chatThread.start();

        //===========================listRoom Click Event===========================
        listRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListRoom = new Intent(ChatActivity.this,ListRoomActivity.class);
                intentListRoom.putExtra("userId",userId);
                intentListRoom.putExtra("userNo",userNo);

                startActivity(intentListRoom);
            }
        });

        //===========================listMyRoom Click Event===========================
        listMyRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListMyRoom = new Intent(ChatActivity.this,ListMyRoomActivity.class);
                intentListMyRoom.putExtra("userId",userId);
                intentListMyRoom.putExtra("userNo",userNo);
                startActivity(intentListMyRoom);

            }
        });
        //===========================listSchedule Click Event===========================
        listSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListSchedule = new Intent(ChatActivity.this,ListScheduleActivity.class);
                intentListSchedule.putExtra("userId",userId);
                startActivity(intentListSchedule);

            }
        });

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
