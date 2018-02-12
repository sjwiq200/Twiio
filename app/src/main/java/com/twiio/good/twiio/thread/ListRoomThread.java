package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestRoom;
import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class ListRoomThread extends Thread{
    private Handler handler;

    public ListRoomThread() {
    }

    public ListRoomThread(Handler handler){
        this.handler = handler;
    }


    public void run(){
        RestRoom restRoom = new RestRoom();

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restRoom.listRoom();
            handler.sendMessage(message);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onDestroy(){
        this.interrupt();
    }
}
