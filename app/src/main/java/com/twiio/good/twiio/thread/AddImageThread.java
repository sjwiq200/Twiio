package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.PlanContent;
import com.twiio.good.twiio.rest.RestDailyPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddImageThread extends Thread {

    private Handler handler;
    private PlanContent planContent;
    private String imagePath;

    public AddImageThread() {
    }

    public AddImageThread(Handler handler, PlanContent planContent, String imagePath) {
        this.handler = handler;
        this.planContent = planContent;
        this.imagePath = imagePath;

    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestDailyPlan restDailyPlan = new RestDailyPlan();
        System.out.println("##debug  addImageThread run(): " + planContent);
        System.out.println("##debug  addImageThread run() imagePath :: " + imagePath);
        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restDailyPlan.addImage(planContent, imagePath);
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
