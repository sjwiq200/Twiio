package com.twiio.good.twiio.rest;

import android.support.annotation.NonNull;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.domain.User;

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
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

/**
 * Created by JW on 2018. 2. 12..
 */

public class RestRoom {

//    String fixUrl = "http://192.168.0.29:8080/room/json/";
    String fixUrl = "http://192.168.0.9:8080/room/json/";

    public RestRoom() {
    }

    public List<Room> listRoom() throws Exception{
        System.out.println(this.getClass()+".listRoom()");

        Search search = new Search();

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"listRoom/";

        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(search);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity httpEntityResult = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntityResult.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONArray jsonobj = (JSONArray)JSONValue.parse(br);
        System.out.println(jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();

        List<Room> list = objectMapperResult.readValue(jsonobj.toString(), new TypeReference<List<Room>>() {});

        System.out.println("roomResult==>"+list);
        return list;
    }

    public List<Room> listMyRoom(Search search, String userId) throws Exception{

        System.out.println(this.getClass()+".listMyRoom()");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"listMyRoom/"+userId;

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

        List<Room> list = objectMapperResult.readValue(jsonobj.toString(), new TypeReference<List<Room>>() {});

        System.out.println("roomResult==>"+list);
        return list;
    }





}
