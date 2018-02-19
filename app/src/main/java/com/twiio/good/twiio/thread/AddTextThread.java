package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.PlanContent;
import com.twiio.good.twiio.rest.RestDailyPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddTextThread extends Thread {

    private Handler handler;
    private PlanContent planContent;

    public AddTextThread() {
    }

    public AddTextThread(Handler handler, PlanContent planContent) {
        this.handler = handler;
        this.planContent = planContent;

    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestDailyPlan restDailyPlan = new RestDailyPlan();
        System.out.println("##debug  addTextThread run(): " + planContent);
        try{
            Message message = new Message();
            message.what = 200;
            restDailyPlan.addText(planContent);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onDestroy(){
        this.interrupt();
    }
}
