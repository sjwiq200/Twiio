package com.twiio.good.twiio.rest;

import android.util.Log;

import com.twiio.good.twiio.common.Search;
import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.domain.Room;
import com.twiio.good.twiio.domain.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class RestMainPlan {

//    String fixUrl = "http://192.168.0.54:8080/mainplan/json/";
    String fixUrl;
//    private String urlThumbnail = "http://192.168.0.54:8080/mainplan/json/uploadImage/";
    private String urlThumbnail;

    public RestMainPlan() {
    }
    public RestMainPlan(String url){
        this.fixUrl = url+":8080/mainplan/json/";
        this.urlThumbnail=url+":8080/mainplan/json/uploadImage/";

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

        String jsonValue = objectMapper.writeValueAsString(user);
        HttpEntity httpEntity = new StringEntity(jsonValue,"utf-8");

        httpPost.setEntity(httpEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);

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

    public String addMainPlan(MainPlan mainPlan, String imagePath) throws Exception{
        System.out.println(this.getClass()+".addMainPlan()");
        System.out.println("mainPlan :: "+mainPlan);
        System.out.println("imagePath :: "+imagePath);

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

        String string = br.readLine();
        br.close();
        System.out.println("string ::"+string);

//        HttpClient httpClient02 = new DefaultHttpClient();
//        HttpPost httpPost02 = new HttpPost(urlThumbnail);
//        httpPost02.setEntity(builder.build());
//        httpClient02.execute(httpPost);
        uploadImage(imagePath);

        return string;
    }

    private void uploadImage(String imagePath){
        System.out.println(":: uploadImage start!! ::");
        try
        {
            HttpClient client = new DefaultHttpClient();
            File file = new File(imagePath);
            HttpPost post = new HttpPost(urlThumbnail);

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

}
