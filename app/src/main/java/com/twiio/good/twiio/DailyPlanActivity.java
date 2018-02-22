package com.twiio.good.twiio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twiio.good.twiio.domain.DailyPlan;
import com.twiio.good.twiio.domain.PlanContent;
import com.twiio.good.twiio.thread.AddImageThread;
import com.twiio.good.twiio.thread.AddTextThread;
import com.twiio.good.twiio.thread.DailyPlanThread;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class DailyPlanActivity extends AppCompatActivity {

    String userId;
    int mainPlanNo;
    int dailyPlanNo;
    DailyPlanThread dailyPlanThread;
    Boolean check = false;

    private Button mButton;
    final Context c = this;
    EditText textInput;
    String imageUrl = "http://192.168.0.33:8080/resources/images/dailyPlanContent/";

    DailyPlan dailyPlan;

    TextView date;
    TextView country;
    TextView city;
    TextView mainDay;
    String day;

    //==================================daeun editing====================
    //=========================addImage Button====================
    private Button imageButton;

    private static final int OPEN_CAMERA=1;
    private static final int OPEN_GALLERY=0;

    private Uri imageUri;

    String fileName;
    String imagePath;

    private String currentPhotoPath;//실제 사진 파일 경로
    //====================================================================

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            Map<String, Object> map = (Map<String, Object>)msg.obj;

            //ObjectMapper objectMapperResult = new ObjectMapper();
            dailyPlan = (DailyPlan)map.get("dailyPlan");
            //DailyPlan dailyPlan = objectMapperResult.readValue(((Map<String, Object>) msg.obj).get("dailyPlan").toString(), new TypeReference<DailyPlan>() {});

            List<PlanContent> list = (List<PlanContent>) map.get("list");
            LinearLayout insertLinearLayout = (LinearLayout) View.inflate(DailyPlanActivity.this,R.layout.activity_inflatelist_plan,null); //new Layout
            ScrollView scrollView = (ScrollView)findViewById(R.id.dailyPlanScroll);

            //===========================daeun editing ===========================
            scrollView.removeAllViews();
            //========================================================================

            mainDay.setText(day);
            date.setText(dailyPlan.getDailyDate()+"");
            country.setText(dailyPlan.getDailyCountry());
            city.setText(dailyPlan.getDailyCity());

            for(final PlanContent planContent : list){
                TextView textPlanContentView = new TextView(DailyPlanActivity.this);

                if(planContent.getContentText()!=null){
                    textPlanContentView.setText(Html.fromHtml(planContent.getContentText()));
                }

                if(planContent.getContentImage()!=null){

                    ImageView imageView = new ImageView(DailyPlanActivity.this);
                    Picasso.with(DailyPlanActivity.this).load(imageUrl+planContent.getContentImage()).into(imageView);

                    insertLinearLayout.addView(imageView);


                }

                if(planContent.getRoute()!=null){

                    textPlanContentView.setText(Html.fromHtml("\n\n"+planContent.getRoute()));
                    textPlanContentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                            View mView = layoutInflaterAndroid.inflate(R.layout.route_dialog_box, null);

                            String[] route=planContent.getRouteDescription().split(".png");
                            AlertDialog.Builder alertDialogBuilderRoute = new AlertDialog.Builder(c);
                            alertDialogBuilderRoute.setView(mView);
                            final TextView routeInputDialog = (TextView) mView.findViewById(R.id.routeInputDialog);
                            routeInputDialog.setText(Html.fromHtml(planContent.getRouteDescription()));
                            alertDialogBuilderRoute.setNegativeButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    });

                            AlertDialog alertDialogAndroid = alertDialogBuilderRoute.create();
                            alertDialogAndroid.show();
                        }
                    });
                }

                if(planContent.getMapName() !=null){
                    ImageView imageView = new ImageView(DailyPlanActivity.this);
                    Picasso.with(DailyPlanActivity.this).load(planContent.getMapImage()).resize(800, 800).centerCrop().into(imageView);
                    insertLinearLayout.addView(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = planContent.getMapUrl();
                            System.out.println("###DEBUG : " + url);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    });

                    textPlanContentView.setText("[" + planContent.getMapName()+" ]"+"\n전체주소: " + planContent.getMapAddress() + "\n전화번호 : " + planContent.getMapPhone() + "\n웹사이트 : " + planContent.getMapWebsite());


                }

                insertLinearLayout.addView(textPlanContentView);
            }

            insertLinearLayout.setGravity(Gravity.CENTER);

            scrollView.addView(insertLinearLayout);



            //===========================Text Input Dialog ===========================
            mButton = (Button) findViewById(R.id.openUserInputDialog);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                    View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                    alertDialogBuilderUserInput.setView(mView);

                    final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setPositiveButton("완성", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    check=true;
                                    String text = userInputDialogEditText.getText().toString();
                                    PlanContent planContent = new PlanContent();
                                    planContent.setDailyPlan(dailyPlan);
                                    planContent.setContentText(text);
                                    planContent.setContentType(1);
                                    AddTextThread addTextThread = new AddTextThread(handler,planContent);
                                    addTextThread.start();
                                    System.out.println("### " + userInputDialogEditText.getText().toString());
                                    if(check) {
                                        Intent intentMainPlan = new Intent(DailyPlanActivity.this, DailyPlanActivity.class);
                                        intentMainPlan.putExtra("userId", userId);
                                        intentMainPlan.putExtra("dailyPlanNo", dailyPlanNo);
                                        startActivity(intentMainPlan);
                                        finish();
                                    }

                                }
                            })

                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.cancel();
                                        }
                                    });

                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                    alertDialogAndroid.show();
                }
            });

            //========================================daeun edinting================================
            //=========================addImage============================================
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final String[] str ={"갤러리","카메라"};
                    final String[] str ={"갤러리"};
                    new android.app.AlertDialog.Builder(DailyPlanActivity.this)
                            .setTitle("이미지 선택")
                            .setNegativeButton("취소",null)
                            .setItems(str, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(getApplicationContext(),str[i]+i,Toast.LENGTH_SHORT).show();
                                    if(i==0){
                                        openGallery();
                                    }else if(i==1){
                                        //openCamera();
                                    }
                                }
                            })
                            .show();
                }
            });

            //==============================================================================

        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dailyplan);

        //===========================Layout===========================
        date = (TextView)findViewById(R.id.date);
        country = (TextView)findViewById(R.id.country);
        city = (TextView)findViewById(R.id.city);
        mainDay = (TextView)findViewById(R.id.mainDay);
        //====================daeun editing===========================
        imageButton = (Button)findViewById(R.id.imageButton);
        //===========================================================

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        mainPlanNo = intent.getIntExtra("mainPlanNo",0);
        dailyPlanNo = intent.getIntExtra("dailyPlanNo",0);
        day = intent.getStringExtra("day");

        //===========================Thread Start===========================
        dailyPlanThread = new DailyPlanThread(handler,dailyPlanNo);
        dailyPlanThread.start();

    }

    //===========================daeun editing===========================
    //===========================open Gallery===========================
    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent,OPEN_GALLERY);


    }

    //===========================open Camera===========================
    private void openCamera(){
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        startActivityForResult(cameraIntent,OPEN_CAMERA);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    imageUri = Uri.fromFile(photoFile);
                    System.out.println("openCamear imageUri :: "+imageUri);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    System.out.println("cameraIntent :: "+cameraIntent);
                    startActivityForResult(cameraIntent, OPEN_CAMERA);
                }
            }

        }


    }

    private void sendPicture(Uri imageUri){

        imagePath = getImageNameToUri(imageUri);
        fileName = imagePath.substring(imagePath.lastIndexOf("/")+1);
//
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//        builder.addTextBody("mainThumbnail", fileName, ContentType.create("Multipart/related", "UTF-8"));
//        builder.addTextBody("planTitle", fileName, ContentType.create("Multipart/related", "UTF-8"));
//        builder.addTextBody("filename", fileName, ContentType.create("Multipart/related", "UTF-8"));
//        builder.addTextBody("cityList", fileName, ContentType.create("Multipart/related", "UTF-8"));
//        builder.addTextBody("arrivalDate", fileName, ContentType.create("Multipart/related", "UTF-8"));

//        ExifInterface exif = null;
//
//        try{
//            exif = new ExifInterface(imagePath);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegree(exifOrientation);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//        //mainThumbnail.setImageBitmap(rotate(bitmap, exifDegree));



        PlanContent planContent = new PlanContent();
        planContent.setDailyPlan(dailyPlan);
        planContent.setContentType(2);
        planContent.setContentImage(fileName);
        dailyPlan.setDailyPlanNo(dailyPlanNo);
        planContent.setDailyPlan(dailyPlan);
        AddImageThread addImageThread = new AddImageThread(handler,planContent,imagePath);
        addImageThread.start();

//        Intent intentDailyPlan = new Intent(DailyPlanActivity.this, DailyPlanActivity.class);
//        intentDailyPlan.putExtra("userId", userId);
//        intentDailyPlan.putExtra("dailyPlanNo", dailyPlanNo);
//        startActivity(intentDailyPlan);
//        finish();

    }

    //================getImageUri=========================
    private String getImageNameToUri(Uri imageUri){
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imageUri, proj, null, null, null);
        //Cursor cursor = managedQuery(imageUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        String imgPath = cursor.getString(column_index);
        System.out.println("imgPath :: "+imgPath);

        return imgPath;
    }

    //========================set imageOrientation=================
    private int exifOrientationToDegree(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }

        return 0;
    }

//    private Bitmap rotate(Bitmap src, float degree){
//        //create matrix object
//        Matrix matrix = new Matrix();
//        //set rotation degree
//        matrix.postRotate(degree);
//        //create bitmap object setting image&matrix
//        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
//    }

    private File createImageFile() throws IOException {
        String fileName = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
//        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        imageCaptureName = timeStamp + ".png";
//
//        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/path/"+ imageCaptureName);

        File storageDir = new File(Environment.getExternalStorageDirectory(),fileName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void getPictureForPhoto(Uri imageUri) {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegree(exifOrientation);
        } else {
            exifDegree = 0;
        }

        System.out.println("currentPhotoPath :: "+currentPhotoPath);
        imagePath=currentPhotoPath;
        fileName = imagePath.substring(imagePath.lastIndexOf("/")+1);
        System.out.println("imagePath :: "+imagePath);
        //mainThumbnail.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case OPEN_GALLERY:
            {
                if(data!=null){
                    imageUri = data.getData();
                    sendPicture(imageUri);
                }
                break;
            }
            case OPEN_CAMERA:
            {
                //imageUri = data.getData();
                getPictureForPhoto(imageUri);
                break;
            }
        }
    }
    //=================================================================

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}