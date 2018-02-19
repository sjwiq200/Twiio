package com.twiio.good.twiio.rest;

import com.twiio.good.twiio.domain.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by JW on 2018. 2. 12..
 */

public class RestUser{

//    String fixUrl = "http://192.168.0.29:8080/user/json/";
  String fixUrl = "http://192.168.0.45:8080/user/json/";
  //  String fixUrl = "http://172.30.1.37:8080/user/json/";

    public RestUser() {

    }

    public User getUser(String userId) throws Exception{
        System.out.println(this.getClass()+".getUser(String userId");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url = fixUrl+"getUserAndroid/"+userId.trim();

        // HttpGet : Http Protocol 의 GET 방식 Request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");

        // HttpResponse : Http Protocol 응답 Message 추상화
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONObject jsonobj = (JSONObject) JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        if( jsonobj == null){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonobj.toString(), User.class);
        System.out.println("Login()"+user);

        return user;

    }

    public void addUser(User user) throws Exception{
        System.out.println(this.getClass()+".addUser(User user)");
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();

        String url= fixUrl+"addUser";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(user);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        System.out.println("response ==>" +httpResponse);


        //response value

        /*HttpEntity httpEntityResult = httpResponse.getEntity();

        InputStream is = httpEntityResult.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println(jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();
        User userResult = objectMapperResult.readValue(jsonobj.toString(), User.class);
        System.out.println("userResult==>"+userResult);*/

    }

}
