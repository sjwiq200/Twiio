package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class LoginThread extends Thread{

    private Handler handler;
    private String userId;
    private boolean flag = false;
    public LoginThread() {
    }

    public LoginThread(Handler handler, String userId){
        this.handler = handler;
        this.userId = userId;
    }

    public void run(){
        RestUser restUser = new RestUser();
        System.out.println("SOcketThread.run() ==>" + userId);

        try{
            if (restUser.getUser(userId) == null){

                Message message =  new Message();
                message.what = 100;
                this.handler.sendMessage(message);

            }else{
                Message message = new Message();
                message.what = 200;
                message.obj = restUser.getUser(userId);
                this.handler.sendMessage(message);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onDestroy(){
        this.interrupt();
    }
}
