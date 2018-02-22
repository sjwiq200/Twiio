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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.thread.ListMyRoomThread;
import com.twiio.good.twiio.thread.ListRoomThread;

import java.util.List;

public class ListMyRoomActivity extends AppCompatActivity {

    String userId;
    int userNo;

    Button searchButton;
    Spinner searchCondition;
    EditText searchKeyword;
    ListMyRoomThread listMyRoomThread;

    Search search;

    private int page = 1;

    private ScrollView scrollView;
    private LinearLayout insertLinearLayout;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){

            List<Room> list = (List<Room>)message.obj;

            if(page == 1){
                insertLinearLayout = (LinearLayout)View.inflate(ListMyRoomActivity.this, R.layout.activity_inflatelist,null); //new Layout
            }else{
                scrollView.removeAllViews();
            }
//            ScrollView scrollView = (ScrollView)findViewById(R.id.listRoomScroll);
//            LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ListMyRoomActivity.this, R.layout.activity_inflatelist,null); //new Layout

            for(final Room room : list){
                LinearLayout customLinearLayout = (LinearLayout)View.inflate(ListMyRoomActivity.this, R.layout.activity_custom_inflate_chat,null); //new Layout
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
                        Toast.makeText(ListMyRoomActivity.this,room.getRoomName()+"에 입장하였습니다.",Toast.LENGTH_SHORT).show();
                        Intent intentChat = new Intent(ListMyRoomActivity.this,ChatRoomActivity.class);
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

            //===========================Scroll View Listener===========================
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                    int bottom = scrollView.getChildAt(0).getBottom() - (scrollView.getHeight() + scrollView.getScrollY() );

                    if(bottom == 0 && ( insertLinearLayout.getChildCount() )%13 == 0){
                        System.out.println("test ENDLESS Scroll");
                        page++;
                        search.setCurrentPage(page);
                        listMyRoomThread = new ListMyRoomThread(handler, userId, search);
                        listMyRoomThread.start();
                    }

                }
            });//END ScrollChangeListener


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listroom);

        //===========================Layout===========================
        searchButton = (Button)findViewById(R.id.searchButton);
        searchCondition = (Spinner)findViewById(R.id.roomSearchCondition);
        searchKeyword = (EditText)findViewById(R.id.roomSearchKeyword);

        scrollView = (ScrollView)findViewById(R.id.listRoomScroll);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        userNo = intent.getIntExtra("userNo",0);

        //===========================Thread Start===========================
        search = new Search();
        listMyRoomThread = new ListMyRoomThread(handler,userId, search);
        listMyRoomThread.start();

        //===========================SearchButton Event===========================
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ListMyRoomActivity.this, "searchConditon : "+searchCondition.getSelectedItem()+", searchKeyword: "+searchKeyword.getText(),Toast.LENGTH_SHORT).show();
                scrollView.removeAllViews();
                page = 1;
                search.setCurrentPage(page);

                if(searchCondition.getSelectedItem().equals("roomTitle")){
                    search.setSearchCondition("0");
                }
                else if(searchCondition.getSelectedItem().equals("Country")){
                    search.setSearchCondition("1");
                }else{
                    search.setSearchCondition("2");
                }

                search.setSearchKeyword(searchKeyword.getText().toString());

                listMyRoomThread = new ListMyRoomThread(handler, userId, search);
                listMyRoomThread.start();

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
