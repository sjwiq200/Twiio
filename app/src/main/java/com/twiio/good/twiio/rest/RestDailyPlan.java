package com.twiio.good.twiio.rest;

import android.util.Log;

import com.twiio.good.twiio.domain.DailyPlan;
import com.twiio.good.twiio.domain.PlanContent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JW on 2018. 2. 12..
 */

public class RestDailyPlan {

//    String fixUrl = "http://192.168.0.54:8080/dailyplan/json/";
    String fixUrl;
    //String fixUrl = "http://192.168.0.33:8080/dailyplan/json/";

    public RestDailyPlan() {
    }

    public RestDailyPlan(String url){
        this.fixUrl = url+":8080/dailyplan/json/";
    }

    public Map<String, Object> getDailyPlan(int dailyPlanNo) throws Exception{
        System.out.println(this.getClass()+".getDailyPlan");

        System.out.println("@@@@debug : " + dailyPlanNo);
        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"getDailyPlan/"+dailyPlanNo;
        System.out.println("Result : @@@ " + url);

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

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();
        DailyPlan dailyPlan = objectMapperResult.readValue(jsonobj.get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});
        List<PlanContent> list = objectMapperResult.readValue(jsonobj.get("list").toString(), new TypeReference<List<PlanContent>>() {});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dailyPlan",dailyPlan);
        map.put("list",list);

        System.out.println("getDailyPlan==>"+map);

        return map;
    }

    //====================================daeun editing==================================
    public void uploadImage(String imagePath) throws  Exception{
        System.out.println(":: uploadImage start!! ::");
        try
        {
            HttpClient client = new DefaultHttpClient();
            File file = new File(imagePath);
            HttpPost post = new HttpPost(fixUrl+"uploadImage/");

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addBinaryBody("file", file);
            // add more key/value pairs here as needed

            HttpEntity entity = entityBuilder.build();
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();

            Log.v("result", EntityUtils.toString(httpEntity));
            System.out.println(":: uploadImage end!! ::");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public  Map<String, Object> addImage(PlanContent planContent,String imagePath) throws  Exception{

        System.out.println(this.getClass()+".addImage()");
        System.out.println("planContent :: "+planContent);
        System.out.println("imagePath :: "+imagePath);

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"addImage/";
        System.out.println("##debug RestDailyPlan: " + url);
        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonValue = objectMapper.writeValueAsString(planContent);
        HttpEntity httpEntitySend = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntitySend);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();
        DailyPlan dailyPlan = objectMapperResult.readValue(jsonobj.get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});
        List<PlanContent> list = objectMapperResult.readValue(jsonobj.get("list").toString(), new TypeReference<List<PlanContent>>() {});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dailyPlan",dailyPlan);
        map.put("list",list);

        System.out.println("listMainPlanResult==>"+map.get("dailyPlan").toString());

        uploadImage(imagePath);

        return map;
    }
    //====================================================================================


    public Map<String, Object> addText(PlanContent planContent) throws Exception{

        System.out.println(this.getClass()+".listMainPlan()");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"addText/";
        System.out.println("##debug RestDailyPlan: " + url);
        // POST request
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();


        String jsonValue = objectMapper.writeValueAsString(planContent);
        HttpEntity httpEntitySend = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntitySend);
        HttpResponse httpResponse = httpClient.execute(httpPost);

        //==> Response 중 entity(DATA) 확인
        HttpEntity httpEntity = httpResponse.getEntity();

        //==> InputStream 생성
        InputStream is = httpEntity.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

        ObjectMapper objectMapperResult = new ObjectMapper();
        DailyPlan dailyPlan = objectMapperResult.readValue(jsonobj.get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});
        List<PlanContent> list = objectMapperResult.readValue(jsonobj.get("list").toString(), new TypeReference<List<PlanContent>>() {});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dailyPlan",dailyPlan);
        map.put("list",list);

        System.out.println("listMainPlanResult==>"+map.get("dailyPlan").toString());
        return map;
    }

    public Map<String, Object> listDailyPlan(int mainPlanNo) throws Exception{
        System.out.println(this.getClass()+".listDailyPlan()");

        // HttpClient : Http Protocol 의 client 추상화
        HttpClient httpClient = new DefaultHttpClient();
        String url = fixUrl+"listDailyPlan/"+mainPlanNo;

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

        JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
        System.out.println("JSON Simple Object : " + jsonobj);

//        if( jsonobj == null){
//            return null;
//        }


        ObjectMapper objectMapperResult = new ObjectMapper();
        List<DailyPlan> list = objectMapperResult.readValue(jsonobj.get("list").toString(), new TypeReference<List<DailyPlan>>() {});

        ////////////*dayoung add for imageView-picasso<START> *////////////////
        Map<String, Object> map = new HashMap<String, Object>();
        if(jsonobj.get("cityList") != null) {
            String[] cityList = objectMapperResult.readValue(jsonobj.get("cityList").toString(), new TypeReference<String[]>() {
            });
            map.put("cityList", cityList);
        }
        ///////////////*dayoung add for imageView-picasso<END> *///////////////
        map.put("list",list);

        System.out.println("listDailyPlanResult==>"+map);
        return map;
    }


}
