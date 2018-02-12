package com.twiio.good.twiio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText idEdit;
    EditText passwordEdit;
    Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.loginButton);
        joinButton = (Button)findViewById(R.id.joinButton);
        idEdit = (EditText)findViewById(R.id.idEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(view.getContext(), LoginActivity.class);
                intent.putExtra("userId", idEdit.getText().toString());
                intent.putExtra("password", passwordEdit.getText().toString());

                startActivityForResult(intent,100);
            }
        });


        //Join Button
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //로그인 실패

        if(requestCode == 100 && data != null){
            Toast.makeText(this,data.getStringExtra("returnValue"),Toast.LENGTH_SHORT).show();
        }
        //회원가입 성공
        else if(requestCode == 200){
            Toast.makeText(this,data.getStringExtra("returnValue"),Toast.LENGTH_SHORT).show();
        }
    }
}
