package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class GetUserThread extends Thread{

    private Handler handler;
    private String userId;
    public GetUserThread() {
    }

    public GetUserThread(Handler handler, String userId){
        this.handler = handler;
        this.userId = userId;
    }

    public void run(){
        RestUser restUser = new RestUser();
        System.out.println("GetUserThread.run() ==>" + userId);

        try{
            Message message =  new Message();
            message.obj = restUser.getUser(userId);
            message.what = 200;
            this.handler.sendMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onDestroy(){
        this.interrupt();
    }
}
