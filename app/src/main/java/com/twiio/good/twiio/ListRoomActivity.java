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
import android.widget.Toast;

import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.thread.ListRoomThread;

import java.util.List;

public class ListRoomActivity extends AppCompatActivity {

    String userId;
    int userNo;
    ListRoomThread listRoomThread;
    private Handler handler = new Handler(){
      public void handleMessage(Message message){


          /*
          //Inflation
          List<Room> list = (List<Room>)message.obj;
          LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ListRoomActivity.this,R.layout.activity_inflatelist,null); //new Layout
          ScrollView scrollView = (ScrollView)findViewById(R.id.listRoomScroll);

          for(final Room room : list){
              //TextView Dynamic Create
              TextView textView = new TextView(ListRoomActivity.this);
              textView.setText("roomName = "+room.getRoomName()+"Country = " + room.getCountry());
              //Button Dynamic Create
              Button button = new Button(ListRoomActivity.this);
              button.setText(room.getRoomKey());
              //Button Dynamic Click Event
              button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Toast.makeText(ListRoomActivity.this,room.getRoomKey(),Toast.LENGTH_SHORT).show();
                      Intent intentChat = new Intent(ListRoomActivity.this,ChatRoomActivity.class);
                      intentChat.putExtra("userId",userId);
                      intentChat.putExtra("userNo",userNo);
                      intentChat.putExtra("roomKey",room.getRoomKey());
                      intentChat.putExtra("master",room.getUserNo());
                      startActivity(intentChat);
                  }
              });

              insertLinearLayout.addView(textView);
              insertLinearLayout.addView(button);

          }
          insertLinearLayout.setGravity(Gravity.CENTER);
          scrollView.addView(insertLinearLayout);
          */

          List<Room> list = (List<Room>)message.obj;
          ScrollView scrollView = (ScrollView)findViewById(R.id.listRoomScroll);
          LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ListRoomActivity.this, R.layout.activity_inflatelist,null); //new Layout

          for(final Room room : list){
              LinearLayout customLinearLayout = (LinearLayout)View.inflate(ListRoomActivity.this, R.layout.activity_custom_inflate_chat,null); //new Layout
              RelativeLayout relativeLayout = (RelativeLayout)customLinearLayout.findViewById(R.id.roomListRelat);
              RelativeLayout relativeLayout2 = (RelativeLayout)customLinearLayout.findViewById(R.id.roomListRelat2);
              TextView roomName = (TextView)customLinearLayout.findViewById(R.id.roomNameText);
              TextView country = (TextView)customLinearLayout.findViewById(R.id.locationText);
              ImageView img = (ImageView)customLinearLayout.findViewById(R.id.locationImg);
              Button button = (Button)customLinearLayout.findViewById(R.id.entry_button);
              customLinearLayout.removeAllViews();
              //TextView Dynamic Create
              roomName.setText(room.getRoomName());
              country.setText(room.getCountry());
              //Button Dynamic Click Event
              button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Toast.makeText(ListRoomActivity.this,room.getRoomName()+"에 입장하였습니다.",Toast.LENGTH_SHORT).show();
                      Intent intentChat = new Intent(ListRoomActivity.this,ChatRoomActivity.class);
                      intentChat.putExtra("userId",userId);
                      intentChat.putExtra("userNo",userNo);
                      intentChat.putExtra("roomKey",room.getRoomKey());
                      intentChat.putExtra("master",room.getUserNo());
                      startActivity(intentChat);
                  }
              });
              ((ViewGroup)country.getParent()).removeView(country);
              ((ViewGroup)img.getParent()).removeView(img);
              relativeLayout2.addView(country);
              relativeLayout2.addView(img);

              ((ViewGroup)relativeLayout2.getParent()).removeView(relativeLayout2);
              ((ViewGroup)roomName.getParent()).removeView(roomName);
              ((ViewGroup)button.getParent()).removeView(button);
              relativeLayout.addView(roomName);
              relativeLayout.addView(relativeLayout2);
              relativeLayout.addView(button);

              ViewGroup parent = (ViewGroup) relativeLayout.getParent();
              if(parent != null){
                  parent.removeView(relativeLayout);
              }
              insertLinearLayout.addView(relativeLayout);
              insertLinearLayout.setGravity(Gravity.CENTER);
          }
          scrollView.addView(insertLinearLayout);


      }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listroom);

        //===========================Layout===========================
        Intent intent =  this.getIntent();
        userId = intent.getStringExtra("userId");
        userNo = intent.getIntExtra("userNo",0);


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
