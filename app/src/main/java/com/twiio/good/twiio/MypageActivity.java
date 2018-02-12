package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MypageActivity extends AppCompatActivity {
    String userId;
    Button getUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //===========================Layout===========================
        getUser = (Button)findViewById(R.id.getUser);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");

        //===========================getUser Button Event===========================
        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("getUser onClickListener");
                Intent intent1 = new Intent(MypageActivity.this,GetUserActivity.class);
                intent1.putExtra("userId",userId);
                startActivity(intent1);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
