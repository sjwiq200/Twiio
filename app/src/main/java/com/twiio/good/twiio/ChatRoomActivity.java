package com.twiio.good.twiio;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {


    String userId;
    int userNo;
    String roomKey;
    int master;
    Button imageButton;

    EditText editTextMessage;
    Button sendButton;
    ScrollView scrollView;

    String userAvatar = "http://localhost:8080/resources/images/room/";
    String url = "http://192.168.0.29:8282/#/v1/";

    private Socket socket;

    private final int GALLERY_CODE = 5000;

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

    //===========================Change Date Format===========================
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
        imageButton = (Button)findViewById(R.id.img_button);
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
            System.out.println("Socket Connection");
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
        //===========================history request Emit===========================
        try{
            System.out.println("Socket Request ");
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName",userId);
            jsonObject.put("roomKey",roomKey);
            jsonObject.put("isSchedule",false);
            socket.emit("history request", jsonObject);
        }
        catch(Exception e){
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

        //===========================Send Image Button Event===========================
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_CODE);

            }
        });

        //===========================Socket On===========================
        try{
            socket.on("new message",onMessageReceived);
            socket.on("history response",onHistory);
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
                    System.out.println("onMessageReceived run() ==>" + response);
                    // inflation
                    LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ChatRoomActivity.this,R.layout.activity_inflatechat,null); //new Layout
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chatRoomMessage);


                    /*
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

                    */

                    TextView textView = new TextView(ChatRoomActivity.this);
                    ViewGroup.LayoutParams width = null;
                    Drawable drawableTo = getResources().getDrawable(
                            R.drawable.rounded_edittext2);
                    Drawable drawableFrom = getResources().getDrawable(
                            R.drawable.rounded_edittext);
                    try{
                        textView.setText("  "+response.get("userName")+" : " + response.get("msg"));
                        if(response.get("userName").toString().equals(userId)){
                            textView.setText(response.get("msg")+" ");
                            textView.setBackground(drawableTo);
                            textView.setTextSize(20);
                            width = new ViewGroup.LayoutParams((("  "+response.get("msg")+" ").length()*55)/2, 90);
                            insertLinearLayout.setGravity(Gravity.RIGHT);
                            insertLinearLayout.setPadding(10,10,10, 0);
                            textView.setGravity(Gravity.RIGHT);


                        }
                        else{
                            textView.setWidth((response.get("userName")+" : " + response.get("msg")).length());
                            textView.setBackground(drawableFrom);
                            textView.setTextSize(20);
                            width = new ViewGroup.LayoutParams(((response.get("userName")+" : " + response.get("msg")).length()*55)/2, 90);
                            insertLinearLayout.setGravity(Gravity.LEFT);
                            insertLinearLayout.setPadding(10,10,10, 0);
                            textView.setGravity(Gravity.LEFT);
                        }
                        insertLinearLayout.addView(textView, width);
                        linearLayout.addView(insertLinearLayout);
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }



                }//END run()
            });
        }

    };//END onMessageReceived

    private Emitter.Listener onHistory = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONObject response = (JSONObject) args[0];
            System.out.println("onHistory ==> " + response);
            //=========================== Make Dynamic Ui Using Thread ===========================
            ChatRoomActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("onHistory.run() ==>"+response);
                    try{
                        JSONArray responseArray = response.getJSONArray("history");
                        for (int i = 0; i < responseArray.length(); i++ ) {
                            System.out.println("JSONArray for Loof ==>" + responseArray.get(i));
                            JSONObject jsonObjectResponse = (JSONObject) responseArray.get(i);

                            //=========================== Inflation ===========================
                            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(ChatRoomActivity.this, R.layout.activity_inflatechat, null); //new Layout
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chatRoomMessage);

                            TextView textView = new TextView(ChatRoomActivity.this);
                            ViewGroup.LayoutParams width = null;
                            Drawable drawableTo = getResources().getDrawable(R.drawable.rounded_edittext2);
                            Drawable drawableFrom = getResources().getDrawable(R.drawable.rounded_edittext);
                            textView.setText("  "+jsonObjectResponse.get("userName")+" : " + jsonObjectResponse.get("msg"));

                            if(jsonObjectResponse.get("userName").toString().equals(userId)){
                                textView.setText(jsonObjectResponse.get("msg")+" ");
                                textView.setBackground(drawableTo);
                                textView.setTextSize(20);
                                width = new ViewGroup.LayoutParams((("  "+jsonObjectResponse.get("msg")+" ").length()*55)/2, 90);
                                insertLinearLayout.setGravity(Gravity.RIGHT);
                                insertLinearLayout.setPadding(10,10,10, 0);
                                textView.setGravity(Gravity.RIGHT);


                            }
                            else{
                                textView.setWidth((jsonObjectResponse.get("userName")+" : " + jsonObjectResponse.get("msg")).length());
                                textView.setBackground(drawableFrom);
                                textView.setTextSize(20);
                                width = new ViewGroup.LayoutParams(((jsonObjectResponse.get("userName")+" : " + jsonObjectResponse.get("msg")).length()*55)/2, 90);
                                insertLinearLayout.setGravity(Gravity.LEFT);
                                insertLinearLayout.setPadding(10,10,10, 0);
                                textView.setGravity(Gravity.LEFT);
                            }

                            insertLinearLayout.addView(textView, width);
                            linearLayout.addView(insertLinearLayout);
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                            /*
                            TextView textView = new TextView(ChatRoomActivity.this);
                            textView.setText( jsonObjectResponse.get("userName") +" : " + jsonObjectResponse.get("msg"));

                            if(jsonObjectResponse.get("userName").toString().equals(userId)){
                                textView.setGravity(Gravity.RIGHT);
                            }
                            else{
                                textView.setGravity(Gravity.LEFT);
                            }
                            insertLinearLayout.addView(textView);
                            linearLayout.addView(insertLinearLayout);
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            */
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    };//END onHistory

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK){
                String uri = getImageNameToUri(data.getData());
                try{
                    //=========================== Inflation ===========================
                    LinearLayout insertLinearLayout = (LinearLayout)View.inflate(ChatRoomActivity.this,R.layout.activity_inflatechat,null); //new Layout
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.chatRoomMessage);
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    ImageView imageView = new ImageView(ChatRoomActivity.this);
                    imageView.setImageBitmap(image_bitmap);
                    insertLinearLayout.addView(imageView);
                    linearLayout.addView(insertLinearLayout);
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }//END requestCode
    }//END onActivityResult

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
