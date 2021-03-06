package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class ChatThread extends Thread{

    private Handler handler;
    private String userId;
    private String url;
    public ChatThread() {
    }

    public ChatThread(Handler handler, String userId,String url){
        this.handler = handler;
        this.userId = userId;
        this.url = url;
    }

    public void run(){
        System.out.println(this.getClass()+".run()");
        RestUser restUser = new RestUser(url);
        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restUser.getUser(userId);
            this.handler.sendMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onDestroy(){
        this.interrupt();
    }
}
