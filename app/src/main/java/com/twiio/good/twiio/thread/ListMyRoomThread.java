package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.rest.RestRoom;


/**
 * Created by JW on 2018. 2. 12..
 */

public class ListMyRoomThread extends Thread{
    private Handler handler;
    private String userId;
    private Search search;

    public ListMyRoomThread() {
    }

    public ListMyRoomThread(Handler handler, String userId, Search search){
        this.handler = handler;
        this.userId = userId;
        this.search = search;
    }


    public void run(){
        RestRoom restRoom = new RestRoom();

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restRoom.listMyRoom(search,userId);
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
