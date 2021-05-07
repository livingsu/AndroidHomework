package com.example.tiktok_sjtu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiktok_sjtu.model.UploadResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final int REQUEST_CODE_VIDEO = 102;
    private static final String COVER_IMAGE_TYPE = "image/*";
    private static final String VIDEO_TYPE = "video/*";
    private IApi api;
    private Uri coverImageUri;
    private Uri videoUri;
    private ImageView coverSD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        setContentView(R.layout.activity_upload);
        coverSD = findViewById(R.id.sd_cover);
        findViewById(R.id.btn_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "选择图片");
            }
        });

        findViewById(R.id.btn_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_VIDEO, VIDEO_TYPE, "选择视频");
            }
        });

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                coverSD.setImageURI(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }
            } else {
                Log.d(TAG, "file pick fail");
            }
        }else if(requestCode==REQUEST_CODE_VIDEO) {
            if (resultCode == Activity.RESULT_OK) {
                videoUri = data.getData();

                if (videoUri != null) {
                    Log.d(TAG, "pick video " + videoUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }
            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    private void initNetwork() {
        // 创建Retrofit实例
        // 生成api对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(IApi.class);
    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private void submit() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] videoData = readDataFromUri(videoUri);
        if (videoData == null || videoData.length == 0) {
            Toast.makeText(this, "视频不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length + videoData.length > MAX_FILE_SIZE) {
            Toast.makeText(this, "封面和视频总大小不能超过30MB！", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("上传数据："+(coverImageData.length+videoData.length));

        // 使用api.submitMessage()方法提交视频
        // 如果提交成功则关闭activity，否则弹出toast
        new Thread(new Runnable() {
            @Override
            public void run() {
                MultipartBody.Part coverPart=MultipartBody.Part.createFormData("cover_image","cover.png",
                        RequestBody.create(MediaType.parse("multipart/form-data"),coverImageData));
                MultipartBody.Part videoPart=MultipartBody.Part.createFormData("video","video.mp4",
                        RequestBody.create(MediaType.parse("multipart/form-data"),videoData));

                Call<UploadResponse> call= api.submitMessage(Constants.STUDENT_ID,Constants.USER_NAME,"",coverPart,videoPart,Constants.token);
                try {
                    Response<UploadResponse> response=call.execute();
                    System.out.println("得到请求："+"1:"+response.isSuccessful()+"2:"+response.code()+"3:"+response.headers());
                    Looper.prepare();
                    if(response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else  Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"网络异常"+e.toString(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
            }
        }).start();

    }


    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}
