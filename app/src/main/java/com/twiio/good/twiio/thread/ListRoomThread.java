package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.rest.RestRoom;
import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class ListRoomThread extends Thread{
    private Handler handler;
    private Search search;
    private String url;

    public ListRoomThread() {
    }

    public ListRoomThread(Handler handler, Search search,String url){
        this.handler = handler;
        this.search = search;
        this.url = url;
    }


    public void run(){
        System.out.println(this.getClass()+".run()");
        RestRoom restRoom = new RestRoom(url);

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restRoom.listRoom(search);
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
