package com.twiio.good.twiio.rest;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.domain.Schedule;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by JW on 2018. 2. 12..
 */

public class RestSchedule {

//    String fixUrl = "http://192.168.0.29:8080/schedule/json/";
    String fixUrl = "http://192.168.0.45:8080/schedule/json/";
//    String fixUrl = "http://172.30.1.37:8080/schedule/json/";

    public RestSchedule() {
    }

    public List<Schedule> listSchedule(Search search, String userId) throws Exception{

        System.out.println(this.getClass()+".listSchedule()");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"listSchedule/"+userId;

        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(search);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");
        //requestBody
        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity httpEntityResult = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntityResult.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONArray jsonobj = (JSONArray)JSONValue.parse(br);
        System.out.println(jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();

        List<Schedule> list = objectMapperResult.readValue(jsonobj.toString(), new TypeReference<List<Schedule>>() {});

        System.out.println("roomResult==>"+list);


        return list;
    }





}
