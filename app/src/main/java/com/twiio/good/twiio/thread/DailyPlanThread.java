package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestDailyPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class DailyPlanThread extends Thread {

    private Handler handler;
    private int dailyPlanNo;
    private String url;

    public DailyPlanThread() {
    }

    public DailyPlanThread(Handler handler, int dailyPlanNo,String url) {
        this.handler = handler;
        this.dailyPlanNo = dailyPlanNo;
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestDailyPlan restDailyPlan = new RestDailyPlan(url);

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restDailyPlan.getDailyPlan(dailyPlanNo);
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
