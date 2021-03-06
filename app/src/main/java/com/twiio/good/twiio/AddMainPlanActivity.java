package com.twiio.good.twiio;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.twiio.good.twiio.common.AssetsPropertyReader;
import com.twiio.good.twiio.domain.MainPlan;
import com.twiio.good.twiio.thread.AddMainPlanThread;
import com.twiio.good.twiio.thread.ListMainPlanThread;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by bitcamp on 2018-02-14.
 */

public class AddMainPlanActivity extends AppCompatActivity {

    private static final int OPEN_CAMERA=1;
    private static final int OPEN_GALLERY=0;
    private Uri imageUri;

    private String currentPhotoPath;//실제 사진 파일 경로
    String imageCaptureName;//이미지 이름

    String userId;
    boolean flag;
    String string;
    String fileName;
    String imagePath;

    EditText mainPlanTitle;
    EditText country;
    EditText departureDate;
    EditText arrivalDate;

    ImageView mainThumbnail;

    MainPlan mainPlan;

    Button addMainPlanDone;
    Button cancel;

    AddMainPlanThread addMainPlanThread;

    String TWIIOurl;

    private AssetsPropertyReader assetsPropertyReader;
    private Context context;
    private Properties p;


    private Handler handler = new Handler(){
        public void handleMessage(Message message){

            //Inflation
            string = (String) message.obj;
            if (string.equals("ok")) {
                Intent intentAddMainPlan = new Intent(AddMainPlanActivity.this, ListMainPlanActivity.class);
                intentAddMainPlan.putExtra("userId", userId);

                startActivity(intentAddMainPlan);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmainplan);

        //===========================properties===========================
        context = this;
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("TwiioURL.properties");
        TWIIOurl = p.getProperty("TwiioURL");

        //===========================Layout===========================
        addMainPlanDone = (Button)findViewById(R.id.addMainPlan_done);
        cancel = (Button)findViewById(R.id.cancel);

        mainPlanTitle = (EditText)findViewById(R.id.title_mainplan);
        country = (EditText)findViewById(R.id.country_mainplan);
        departureDate = (EditText)findViewById(R.id.departure_date);
        arrivalDate = (EditText)findViewById(R.id.arrival_date);
        mainThumbnail = (ImageView)findViewById(R.id.thumbnail_mianplan);

        //===========================Intent===========================
        Intent intent = this.getIntent();
        userId = intent.getStringExtra("userId");
        System.out.println("userId :: "+userId);
        //userNo = intent.getIntExtra("userNo",0);


        //===========================addMainPlanDone Click Event===========================
        addMainPlanDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("departureDate :: "+departureDate.getText());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date departureDateSql = java.sql.Date.valueOf(departureDate.getText().toString());
                java.sql.Date arrivalDateSql = java.sql.Date.valueOf(arrivalDate.getText().toString());
                mainPlan = new MainPlan();
                String[] str =country.getText().toString().split(",");
                mainPlan.setCountryList(str);
                mainPlan.setPlanTitle(mainPlanTitle.getText().toString());
                mainPlan.setDepartureDate(departureDateSql);
                mainPlan.setArrivalDate(arrivalDateSql);
                mainPlan.setMainThumbnail(fileName);
                System.out.println("mainPlan.fileName :: "+mainPlan.getMainThumbnail());

                System.out.println("userId :: :: "+userId);
                addMainPlanThread = new AddMainPlanThread(handler, userId, mainPlan, imagePath,TWIIOurl);
                addMainPlanThread.start();


                //Intent intentAddMainPlan = new Intent(AddMainPlanActivity.this,)
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateDate(calendar);
            }
        };

        //===========================departureDate Click Event===========================
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=true;
                new DatePickerDialog(AddMainPlanActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //===========================arrivalDate Click Event===========================
        arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                new DatePickerDialog(AddMainPlanActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //===========================mainThumbnail Click Event===========================
        mainThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] str ={"갤러리","카메라"};
                //final String[] str ={"갤러리"};
                new AlertDialog.Builder(AddMainPlanActivity.this)
                        .setTitle("이미지 선택")
                        .setNegativeButton("취소",null)
                        .setItems(str, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getApplicationContext(),str[i]+i,Toast.LENGTH_SHORT).show();
                                if(i==0){
                                    openGallery();
                                }else if(i==1){
                                    openCamera();
                                }
                            }
                        })
                        .show();
            }
        });

        //===========================cancel Click Event===========================
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    //===========================open Gallery===========================
    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent,OPEN_GALLERY);
    }

    private void updateDate(Calendar calendar){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);

        if(flag){
            departureDate.setText(sdf.format(calendar.getTime()));
        }else{
            arrivalDate.setText(sdf.format(calendar.getTime()));
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

        ExifInterface exif = null;

        try{
            exif = new ExifInterface(imagePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegree(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        mainThumbnail.setImageBitmap(rotate(bitmap, exifDegree));
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
        mainThumbnail.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
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

    private Bitmap rotate(Bitmap src, float degree){
        //create matrix object
        Matrix matrix = new Matrix();
        //set rotation degree
        matrix.postRotate(degree);
        //create bitmap object setting image&matrix
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
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
