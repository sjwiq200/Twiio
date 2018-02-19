package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.rest.RestMainPlan;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddMainPlanThread extends Thread {

    private Handler handler;
    private String userId;
    private MainPlan mainPlan;
    private User user;

    public AddMainPlanThread() {
    }

    public AddMainPlanThread(Handler handler, String userId, MainPlan mainPlan) {
        this.handler = handler;
        this.userId = userId;
        this.mainPlan = mainPlan;
        user= new User();
        user.setUserId(userId);
        mainPlan.setUser(user);
    }

    @Override
    public void run() {
        System.out.println(this.getClass()+".run()");
        RestMainPlan restMainPlan = new RestMainPlan();

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restMainPlan.addMainPlan(mainPlan);
            //message.obj = restMainPlan.listMainPlan(userId);
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
