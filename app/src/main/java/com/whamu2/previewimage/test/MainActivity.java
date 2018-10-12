package com.whamu2.previewimage.test;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.whamu2.previewimage.Preview;
import com.whamu2.previewimage.engine.impl.GlideEngine;
import com.whamu2.previewimage.entity.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int REQUEST_PERMISSION_CALL = 100;

    public static final int REQUEST_PERMISSION_READ_STORAGE = 101;
    private static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private Intent callIntent;
    ImageView imageView;
    Button  btn_readimg;
    Button btn;
    String[] urls = {
            "http://img3.16fan.com/live/origin/201805/21/E421b24c08446.jpg",

            "http://img3.16fan.com/live/origin/201805/21/E421b24c08446.jpg",
            "http://img3.16fan.com/live/origin/201805/21/4D7B35fdf082e.jpg",
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg", //  5760 * 3840
            "http://img3.16fan.com/live/origin/201805/21/2D02ebc5838e6.jpg",
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
            "http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
            "http://img3.16fan.com/live/origin/201805/21/A1B17c5f59b78.jpg",
            "http://img3.16fan.com/live/origin/201805/21/94699b2be3cfa.jpg",
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
            "http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
            "http://img3.16fan.com/live/origin/201805/21/EB298ce595dd2.jpg",
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg", //  5760 * 3840
            "http://img3.16fan.com/live/origin/201805/21/264Ba4860d469.jpg",
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
            "http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg" //  5760 * 3840
    };

    //
//
//    String imageUri = "http://site.com/image.png"; // from Web
//    String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
//    String imageUri = "content://media/external/audio/albumart/13"; // from content provider
//    String imageUri = "assets://image.png"; // from assets
//    String imageUri = "drawable://" + R.drawable.image; /
//            ---------------------
//    作者：ManLikeTheWind
//    来源：CSDN
//    原文：https://blog.csdn.net/a2241076850/article/details/78044310?utm_source=copy
//    版权声明：本文为博主原创文章，转载请附上博文链接！

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                Log.i("tchl","data url"+ data.getData().toString());
                imageView.setImageURI(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        btn = findViewById(R.id.btn_call);
        btn_readimg = findViewById(R.id.btn_readimg);
        //imageView.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("tchl",Uri.fromFile(new File("/sdcard/jpg.jpg")).toString());

                Log.i("tchl","image view click");
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        btn_readimg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                loadimage();
            }
        });


        callIntent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + "10086");
        callIntent.setData(uri);


        //imageView.setImageDrawable(this.getResources(R.drawable.jpg));
        //Uri uri = Uri.fromFile(new File("/sdcard/test.jpg"));
        //imageView.setImageURI(uri);

/*        Uri uri = Uri.fromFile(new File("file:///mnt/sdcard/jpg.jpg"));


        imageView.setImageURI(Uri.fromFile(new File("/sdcard/jpg.jpg")));*/
        //imageView.setImageURI(Uri.parse("file:///assets/tchl.jpg"));
        //Uri.parse("file:///assets/xxx.jpg")
        //imageView.setImageDrawable(this.getDrawable(R.drawable.jpg));
        List<Data> datas = new ArrayList<>();

        Data data = new Data();
        data.setOriginUrl("/sdcard/jpg.jpg");
        data.setThumbnailUrl("/sdcard/jpg.jpg");
        datas.add(data);


        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/E421b24c08446.jpg");
        datas.add(data);

        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/4D7B35fdf082e.jpg");
        datas.add(data);

        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/2D02ebc5838e6.jpg");
        datas.add(data);

        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg");
        datas.add(data);

        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/A1B17c5f59b78.jpg");
        datas.add(data);

        data = new Data();
        data.setOriginUrl("http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg");
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/94699b2be3cfa.jpg");
        datas.add(data);

        data = new Data();
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/EB298ce595dd2.jpg");
        datas.add(data);

        data = new Data();
        data.setThumbnailUrl("http://img3.16fan.com/live/origin/201805/21/264Ba4860d469.jpg");
        datas.add(data);


        List<Image> images = new ArrayList<>();
        for (Data d : datas) {
            Image image = new Image();
            image.setOriginUrl(d.getOriginUrl());
            image.setThumbnailUrl(d.getThumbnailUrl());
            images.add(image);
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preview.with(MainActivity.this)
                        .builder()
                        .load(images)
                        .displayCount(true)
                        .markPosition(0)
                        .showDownload(true)
                        .showOriginImage(true)
                        .downloadLocalPath("Preview")
                        .show();
            }
        });
    }

    private void loadimage() {
        Log.i("tchl","call loadimage");
        if(checkPermissionStorage(READ_STORAGE)){
            imageView.setImageURI(Uri.fromFile(new File("/sdcard/jpg.jpg")));
        } else {
            startRequestPermisionStorage(READ_STORAGE);
        }

    }

    private void call() {
        if (checkPermission(CALL_PHONE)) {
            startActivity(callIntent);
        } else {
            startRequestPermision(CALL_PHONE);
        }
    }
    private void startRequestPermision(String permission) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
    }
    private void startRequestPermisionStorage(String permission) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_STORAGE);
    }
    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("checkPermission", "PERMISSION_GRANTED" + ContextCompat.checkSelfPermission(this, CALL_PHONE));
                return true;
            } else {
                Log.e("checkPermission", "PERMISSION_DENIED" + ContextCompat.checkSelfPermission(this, CALL_PHONE));
                return false;
            }
        } else {
            Log.e("checkPermission", "M以下" + ContextCompat.checkSelfPermission(this, CALL_PHONE));
            return true;
        }
    }


    private boolean checkPermissionStorage(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, READ_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("checkPermission", "PERMISSION_GRANTED" + ContextCompat.checkSelfPermission(this, READ_STORAGE));
                return true;
            } else {
                Log.e("checkPermission", "PERMISSION_DENIED" + ContextCompat.checkSelfPermission(this, READ_STORAGE));
                return false;
            }
        } else {
            Log.e("checkPermission", "M以下" + ContextCompat.checkSelfPermission(this, READ_STORAGE));
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CALL) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    //如果拒绝授予权限,且勾选了再也不提醒
                    if (!shouldShowRequestPermissionRationale(permissions[0])) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("说明")
                                .setMessage("需要使用电话权限，进行电话测试")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                            showTipGoSetting();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        return;
                                    }
                                })
                                .create()
                                .show();
                    } else {
                        showTipGoSetting();
                    }
                }
            }
        }

        if(requestCode == REQUEST_PERMISSION_READ_STORAGE){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadimage();
                } else {
                    Log.i("tchl","read storage not PERMISSION_GRANTED  ");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    /**
     * 用于在用户勾选“不再提示”并且拒绝时，再次提示用户
     */
    private void showTipGoSetting() {
        new AlertDialog.Builder(this)
                .setTitle("电话权限不可用")
                .setMessage("请在-应用设置-权限-中，允许APP使用电话权限来测试")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false).show();

    }

    /**
     * 打开Setting
     */
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

}
