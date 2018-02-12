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

public class ChatActivity extends AppCompatActivity {

    Button listRoom;
    Button listMyRoom;
    Button listSchedule;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //===========================Layout===========================
        listRoom = (Button)findViewById(R.id.listRoom);
        listMyRoom = (Button)findViewById(R.id.listMyRoom);
        listSchedule = (Button) findViewById(R.id.listSchedule);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================listRoom Click Event===========================
        listRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListRoom = new Intent(ChatActivity.this,ListRoomActivity.class);
                startActivity(intentListRoom);
            }
        });

        //===========================listMyRoom Click Event===========================
        listMyRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListMyRoom = new Intent(ChatActivity.this,ListMyRoomActivity.class);
                intentListMyRoom.putExtra("userId",userId);
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
