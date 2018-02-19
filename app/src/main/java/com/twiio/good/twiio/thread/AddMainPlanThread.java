package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.domain.User;
import com.twiio.good.twiio.rest.RestMainPlan;

import org.apache.http.entity.mime.MultipartEntityBuilder;


/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddMainPlanThread extends Thread {

    private Handler handler;
    private String userId;
    private MainPlan mainPlan;
    private User user;
    //private MultipartEntityBuilder builder;
    private String imagePath;

    public AddMainPlanThread() {
    }

    public AddMainPlanThread(Handler handler, String userId, MainPlan mainPlan, String imagePath) {
        this.handler = handler;
        this.userId = userId;
        this.mainPlan = mainPlan;
        //this.builder = builder;
        this.imagePath = imagePath;
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
            message.obj = restMainPlan.addMainPlan(mainPlan, imagePath);
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
