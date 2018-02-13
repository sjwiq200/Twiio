package com.twiio.good.twiio;

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

import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.thread.ListRoomThread;

import java.util.List;

public class ListRoomActivity extends AppCompatActivity {

    ListRoomThread listRoomThread;
    private Handler handler = new Handler(){
      public void handleMessage(Message message){
          List<Room> list = (List<Room>)message.obj;
          LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ListRoomActivity.this,R.layout.activity_inflatelist,null); //new Layout
          ScrollView scrollView = (ScrollView)findViewById(R.id.listRoomScroll);
          for(Room room : list){
              TextView textView = new TextView(ListRoomActivity.this);
              textView.setText("roomName = "+room.getRoomName()+"Country = " + room.getCountry());
              Button button = new Button(ListRoomActivity.this);
              button.setText(room.getRoomKey());

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
        setContentView(R.layout.activity_listroom);

        //===========================Thread Start===========================
        listRoomThread = new ListRoomThread(handler);
        listRoomThread.start();

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
