package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.rest.RestUser;


/**
 * Created by JW on 2018. 2. 12..
 */

public class AddUserThread extends Thread{

    private Handler handler;
    private User user;
    private String url;
    public AddUserThread() {
    }

    public AddUserThread(Handler handler, User user,String url){
        this.handler = handler;
        this.user = user;
        this.url = url;
    }

    public void run(){
        RestUser restUser = new RestUser(url);
        System.out.println("AddUserThread.run() ==>" + user);

        try{
            restUser.addUser(user);
            Message message =  new Message();
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
