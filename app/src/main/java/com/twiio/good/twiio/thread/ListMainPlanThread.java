package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestMainPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class ListMainPlanThread extends Thread {

    private Handler handler;
    private String userId;
    private String url;

    public ListMainPlanThread() {
    }

    public ListMainPlanThread(Handler handler, String userId, String url) {
        this.handler = handler;
        this.userId = userId;
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestMainPlan restMainPlan = new RestMainPlan(url);

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restMainPlan.listMainPlan(userId);
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
