package com.twiio.good.twiio.rest;

import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.domain.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class RestMainPlan {

    String fixUrl = "http://192.168.0.73:8080/mainplan/json/";

    public RestMainPlan() {
    }

    public List<MainPlan> listMainPlan(String userId) throws Exception{
        System.out.println(this.getClass()+".listMainPlan()");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"listMainPlan/";

        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User();
        user.setUserId(userId);

        System.out.println("##DEBUG : REST MAIN PLAN ###" + userId);

        String jsonValue = objectMapper.writeValueAsString(user);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        System.out.println("##DEBUG RESTMAINPLAN.JAVA" + httpResponse);
        HttpEntity httpEntityResult = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntityResult.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONArray jsonobj = (JSONArray)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

//        if( jsonobj == null){
//            return null;
//        }

        ObjectMapper objectMapperResult = new ObjectMapper();

        List<MainPlan> list = objectMapperResult.readValue(jsonobj.toString(), new TypeReference<List<MainPlan>>() {});

        System.out.println("listMainPlanResult==>"+list);
        return list;
    }

    public Boolean addMainPlan(MainPlan mainPlan) throws Exception{
        System.out.println(this.getClass()+".addMainPlan()");
        System.out.println("mainPlan :: "+mainPlan);

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"addMainPlan/";

        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(mainPlan);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity httpEntityResult = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntityResult.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

//        if( jsonobj == null){
//            return null;
//        }

        ObjectMapper objectMapperResult = new ObjectMapper();

        Boolean boo = objectMapperResult.readValue(jsonobj.toString(), new TypeReference<Boolean>() {});

        System.out.println("addMainPlanResult==>"+boo);
        return boo;
    }

}
