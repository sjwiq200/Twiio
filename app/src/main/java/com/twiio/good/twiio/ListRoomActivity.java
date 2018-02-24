package com.twiio.good.twiio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.thread.ListRoomThread;

import java.util.List;
import java.util.Properties;

public class ListRoomActivity extends AppCompatActivity {

    String userId;
    int userNo;

    Button searchButton;
    Spinner searchCondition;
    EditText searchKeyword;
    ListRoomThread listRoomThread;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;

    Search search;

    private int page = 1;

    private ScrollView scrollView;
    private LinearLayout insertLinearLayout;




    private Handler handler = new Handler(){
      public void handleMessage(Message message){

          List<Room> list = (List<Room>)message.obj;
          System.out.println("List<Room> Size ==> " +list.size());


          if(page == 1){
              insertLinearLayout = (LinearLayout)View.inflate(ListRoomActivity.this, R.layout.activity_inflatelist,null); //new Layout
          }else{
              scrollView.removeAllViews();
          }

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
          }//END For Loof
          scrollView.addView(insertLinearLayout);

          //===========================Scroll View Listener===========================
          scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
              @Override
              public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                  int bottom = scrollView.getChildAt(0).getBottom() - (scrollView.getHeight() + scrollView.getScrollY() );

                  if(bottom == 0
//                          && ( insertLinearLayout.getChildCount() )%13 == 0
                          ){
                      System.out.println("test ENDLESS Scroll");
                      page++;
                      search.setCurrentPage(page);
                      listRoomThread = new ListRoomThread(handler,search,TWIIOurl);
                      listRoomThread.start();
                  }

              }
          });//END ScrollChangeListener




      }
    };//END Handler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listroom);

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");

        //===========================Layout===========================
        searchButton = (Button)findViewById(R.id.searchButton);
        searchCondition = (Spinner)findViewById(R.id.roomSearchCondition);
        searchKeyword = (EditText)findViewById(R.id.roomSearchKeyword);

        scrollView = (ScrollView)findViewById(R.id.listRoomScroll);


        //===========================Intent===========================
        Intent intent =  this.getIntent();
        userId = intent.getStringExtra("userId");
        userNo = intent.getIntExtra("userNo",0);


        //===========================Thread Start===========================
        search = new Search();
        search.setCurrentPage(page);
        listRoomThread = new ListRoomThread(handler,search,TWIIOurl);
        listRoomThread.start();


        //===========================SearchButton Event===========================
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ListRoomActivity.this, "searchConditon : "+searchCondition.getSelectedItem()+", searchKeyword: "+searchKeyword.getText(),Toast.LENGTH_SHORT).show();
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

                listRoomThread = new ListRoomThread(handler,search,TWIIOurl);
                listRoomThread.start();

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
