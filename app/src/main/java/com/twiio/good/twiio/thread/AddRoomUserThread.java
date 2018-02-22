package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestRoom;
import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class AddRoomUserThread extends Thread{

    private Handler handler;
    private int userNo;
    private String roomKey;
    public AddRoomUserThread() {
    }

    public AddRoomUserThread(Handler handler, int userNo, String roomKey){
        this.handler = handler;
        this.userNo = userNo;
        this.roomKey = roomKey;
    }

    public void run(){
        System.out.println(this.getClass()+".run()");
        RestRoom restRoom = new RestRoom();
        try{
            Message message = new Message();
            message.what = 200;
            restRoom.addRoomUser(userNo,roomKey);
            this.handler.sendMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onDestroy(){
        this.interrupt();
    }
}
