package com.twiio.good.twiio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.twiio.good.twiio.thread.NodeThread;

import org.json.JSONObject;

import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {


    String userId;
    int userNo;
    String roomKey;
    int master;

    EditText editTextMessage;
    Button sendButton;
    ScrollView scrollView;

    String userAvatar = "http://localhost:8080/resources/images/room/";
    String url = "http://192.168.0.29:8282/#/v1/";

    private Socket socket;

    public String formatAMPM(Date date) {
        int hours = date.getHours();
        int minutes = date.getMinutes();
        String minute;
        String ampm = hours >= 12 ? "pm" : "am";
        hours = hours % 12;
        if(hours == 0){
            hours = 12;
        }
//        hours = hours ? hours : 12; // the hour '0' should be '12'
        if(minutes < 10){
            minute = "0"+minutes;
        }else{
            minute = minutes+"";
        }
//        minutes = minutes < 10 ? "0"+minutes : minutes;
//        String strTime = hours + ':' + minutes + ' ' + ampm;
        String strTime = hours + ':' + minute + ' ' + ampm;
        return strTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        //===========================Layout===========================
        editTextMessage = (EditText)findViewById(R.id.edittext_message);
        sendButton = (Button)findViewById(R.id.send_button);
        scrollView = (ScrollView)findViewById(R.id.chatRoomScroll);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        roomKey = intent.getStringExtra("roomKey");
        userNo = intent.getIntExtra("userNo",0);
        master = intent.getIntExtra("master",0);

        //===========================Thread Start===========================


        //===========================Socket.io Start===========================
        //===========================Connect===========================
        try{
            System.out.println("here is ChatRoomActivity.java");
            socket = IO.socket(url+roomKey+"/"+userId+"/"+userNo+"/"+master);
            socket.connect();
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName",userId);
            jsonObject.put("userAvatar",userAvatar+"Avatar1.jpg");
            jsonObject.put("roomKey",roomKey);
            jsonObject.put("userNo",userNo);

            //===========================new user Emit===========================
            socket.emit("new user", jsonObject, new Ack() {
                @Override
                public void call(Object... args) {
                    JSONObject response = (JSONObject)args[0];

                    //===========================new user Emit CallBack===========================
                    try{
                        String success = response.get("success").toString();
                        if(success.equals("true")){
                            //===========================get-online-members Emit===========================
                            socket.emit("get-online-members",jsonObject);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //===========================Send Button Event===========================
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextMessage.getText().toString().trim();
                System.out.println("here is a message"+message);
                if(TextUtils.isEmpty(message)){
                    return ;
                }
                else{
                    editTextMessage.setText("");
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("userName",userId);
                        jsonObject.put("userAvatar","Avatar1.jpg");
                        jsonObject.put("msg",message);
                        jsonObject.put("hasMsg",true);
                        jsonObject.put("hasFile",false);
                        jsonObject.put("msgTime",formatAMPM(new Date()));
                        jsonObject.put("roomKey",roomKey);
                        System.out.println("jsonObejct22222 ==>" + jsonObject);
                        //===========================send-message Emit===========================
                        socket.emit("send-message", jsonObject, new Ack() {
                            @Override
                            public void call(Object... args) {
                                JSONObject response = (JSONObject)args[0];
                                System.out.println("send-message Event +" +response);
                                try{
                                    System.out.println("success send-message==>"+response.get("success"));

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


        });

        //===========================new message On===========================
        try{
            socket.on("new message",onMessageReceived);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//end onCreate

    //===========================On Emitter.Listener===========================
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("Connection OK");
        }
    };

    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject response = (JSONObject) args[0];
            System.out.println("onMEssageReceived ==>"+response);
            //  Make Dynamic Ui Using Thread
            ChatRoomActivity.this.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    System.out.println("run() ==>" + response);
                    // inflation
                    LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ChatRoomActivity.this,R.layout.activity_inflatechat,null); //new Layout
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chatRoomMessage);

                    TextView textView = new TextView(ChatRoomActivity.this);
                    try{
                        textView.setText(response.get("userName")+" : " + response.get("msg"));
                        if(response.get("userName").toString().equals(userId)){
                            textView.setGravity(Gravity.RIGHT);

                        }
                        else{
                            textView.setGravity(Gravity.LEFT);
                        }
                        insertLinearLayout.addView(textView);
                        linearLayout.addView(insertLinearLayout);
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }//END run()
            });


        }

    };//END onMessageReceived

    protected void onDestroy(){
        super.onDestroy();
        socket.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
