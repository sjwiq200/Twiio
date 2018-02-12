package com.twiio.good.twiio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.thread.LoginThread;

/**
 * Created by JW on 2018. 2. 12..
 */

public class LoginActivity extends AppCompatActivity{

    String userId;
    String password;
    TextView userIdText;
    LoginThread loginThread;
    Button mypage;
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
                    userIdText.setText(user.getUserId() + "님 로그인!");

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

        //===========================Layout===========================
        userIdText = (TextView)findViewById(R.id.userIdText);
        mypage = (Button)findViewById(R.id.mypage);

        //===========================Intent===========================

        final Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        password = intent.getStringExtra("password");

        //===========================Thread Start===========================

        loginThread = new LoginThread(handler,userId);
        loginThread.start();


        //===========================mypage Click Event===========================
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMypage = new Intent(LoginActivity.this,MypageActivity.class);
                intentMypage.putExtra("userId",userId);
                startActivity(intentMypage);
            }
        });

        }

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
