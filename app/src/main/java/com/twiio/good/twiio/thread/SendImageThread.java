package com.twiio.good.twiio.thread;

import android.os.Handler;
import android.os.Message;

import com.twiio.good.twiio.rest.RestRoom;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by JW on 2018. 2. 12..
 */

public class SendImageThread extends Thread{
    private Handler handler;
    private MultipartEntityBuilder builder;
    private String url = "http://192.168.0.54:8282/v1/uploadImage";



    public SendImageThread() {
    }

    public SendImageThread(Handler handler, MultipartEntityBuilder builder){
        this.handler = handler;
        this.builder = builder;
    }


    public void run(){
        System.out.println(this.getClass()+".run()");


        InputStream inputStream = null;
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(builder.build());
            httpClient.execute(httpPost);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            inputStream = httpEntity.getContent();
//
//            BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//
//            StringBuilder stringBuilder = new StringBuilder();
//
//            String line = null;

//            while ((line = bufferdReader.readLine()) != null) {
//                stringBuilder.append(line + "\n");
//            }
//            inputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }


        try{
            Message message = new Message();
            message.what = 200;
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
