package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestDailyPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class ListDailyPlanThread extends Thread {

    private Handler handler;
    private int mainPlanNo;
    private String url;

    public ListDailyPlanThread() {
    }

    public ListDailyPlanThread(Handler handler, int mainPlanNo,String url) {
        this.handler = handler;
        this.mainPlanNo = mainPlanNo;
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestDailyPlan restDailyPlan = new RestDailyPlan(url);

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restDailyPlan.listDailyPlan(mainPlanNo);
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
