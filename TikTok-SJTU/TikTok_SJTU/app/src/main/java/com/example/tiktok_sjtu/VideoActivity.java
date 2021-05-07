package com.example.tiktok_sjtu;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;


public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);

        isPlaying=false;

        //接受主页面传过来的视频url信息
        String videoUrl=getIntent().getStringExtra("videoUrl");

        videoView = findViewById(R.id.videoView);
        videoView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        videoView.setZOrderOnTop(true);
        videoView.setVideoPath(videoUrl); //设置url

        videoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    isPlaying=false;
                    videoView.pause();
                }else{
                    isPlaying=true;
                    videoView.start();
                }
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                videoView.start();
            }
        }).start();


    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
