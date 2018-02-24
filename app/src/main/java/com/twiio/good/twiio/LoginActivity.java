package com.twiio.good.twiio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.thread.LoginThread;

import java.util.Properties;

/**
 * Created by JW on 2018. 2. 12..
 */

public class LoginActivity extends AppCompatActivity{

    String userId;
    String password;
    TextView userIdText;
    LoginThread loginThread;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;


    Button mypage;
    Button twiiChat;
    Button twiiBook;

    private Handler handler =  new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 100) {
                Intent intent = new Intent();
                intent.putExtra("returnValue", "id,password가 틀렸습니다");
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else if (message.what == 200) {

                User user = (User) message.obj;
                if (user.getPassword().equals(password)) {
//                    userIdText.setText(user.getUserId() + "님 로그인!");
                    userIdText.setText(user.getUserName() + "님 안녕하세요!");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("returnValue", "id,password가 틀렸습니다");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");


        //===========================Layout===========================
        userIdText = (TextView)findViewById(R.id.userIdText);
        mypage = (Button)findViewById(R.id.mypage);
        twiiChat = (Button)findViewById(R.id.twiiChat);
        twiiBook = (Button)findViewById(R.id.twiiBook);

        //===========================Intent===========================

        final Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        password = intent.getStringExtra("password");

        //===========================Thread Start===========================

        loginThread = new LoginThread(handler,userId,TWIIOurl);
        loginThread.start();


        //===========================mypage Click Event===========================
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMypage = new Intent(LoginActivity.this,GetUserActivity.class);
                intentMypage.putExtra("userId",userId);
                startActivity(intentMypage);

            }
        });
        //===========================TwiiChat Click Event===========================
        twiiChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTwiiChat = new Intent(LoginActivity.this,ChatActivity.class);
                intentTwiiChat.putExtra("userId",userId);
                startActivity(intentTwiiChat);
            }
        });

        //===========================TwiiBook Click Event===========================
        twiiBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTwiiBook = new Intent(LoginActivity.this, ListMainPlanActivity.class);
                intentTwiiBook.putExtra("userId", userId);
                startActivity(intentTwiiBook);
            }
        });

        }//END onCreate

    protected void onDestroy(){
        super.onDestroy();
        loginThread.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
