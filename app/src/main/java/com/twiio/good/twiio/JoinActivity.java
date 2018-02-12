package com.twiio.good.twiio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.rest.RestUser;
import com.twiio.good.twiio.thread.AddUserThread;

public class JoinActivity extends AppCompatActivity {

    EditText idJoin;
    EditText passwordJoin;
    EditText nameJoin;
    Button addUser;
    Button back;
    User user;
    AddUserThread addUserThread;

    private Handler handler = new Handler(){
        public void handleMessage(Message message){

            if(message.what == 200) {
                Intent intent =  new Intent();
                intent.putExtra("returnValue", "회원가입을 축하합니다");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idJoin =  (EditText)findViewById(R.id.idJoin);
        passwordJoin = (EditText)findViewById(R.id.passwordJoin);
        nameJoin = (EditText)findViewById(R.id.nameJoin);
        addUser = (Button)findViewById(R.id.addUser);
        back = (Button)findViewById(R.id.back);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user  =  new User();
                user.setUserId(idJoin.getText().toString());
                user.setPassword(passwordJoin.getText().toString());
                user.setUserName(nameJoin.getText().toString());

                addUserThread = new AddUserThread(handler, user);
                addUserThread.start();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    protected void onDestroy(){
        super.onDestroy();
        addUserThread.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
