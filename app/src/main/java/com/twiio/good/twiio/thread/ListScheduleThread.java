package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.rest.RestRoom;
import com.twiio.good.twiio.rest.RestSchedule;


/**
 * Created by JW on 2018. 2. 12..
 */

public class ListScheduleThread extends Thread{
    private Handler handler;
    private String userId;

    public ListScheduleThread() {
    }

    public ListScheduleThread(Handler handler, String userId){
        this.handler = handler;
        this.userId = userId;
    }


    public void run(){
        RestSchedule restSchedule = new RestSchedule();

        try{
            Message message = new Message();
            message.what = 200;
            message.obj = restSchedule.listSchedule(userId);
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
