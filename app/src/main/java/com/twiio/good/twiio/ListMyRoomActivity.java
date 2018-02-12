package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.thread.ListMyRoomThread;

import java.util.List;

public class ListMyRoomActivity extends AppCompatActivity {

    ListMyRoomThread listMyRoomThread;
    String userId;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            List<Room> list = (List<Room>)message.obj;
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ListMyRoomActivity.this,R.layout.activity_inflatelist,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.listRoomScroll);
            for(Room room : list){
                TextView textView = new TextView(ListMyRoomActivity.this);
                textView.setText("roomName = "+room.getRoomName()+"Country = " + room.getCountry());

                insertLinearLayout.addView(textView);
            }
            insertLinearLayout.setGravity(Gravity.CENTER);
            scrollView.addView(insertLinearLayout);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listroom);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================Thread Start===========================
        listMyRoomThread = new ListMyRoomThread(handler,userId);
        listMyRoomThread.start();


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
