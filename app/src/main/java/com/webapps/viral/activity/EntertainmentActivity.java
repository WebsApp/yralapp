package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.webapps.viral.R;

public class EntertainmentActivity extends AppCompatActivity {

    ImageView imageView,play;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        imageView =findViewById(R.id.imageview);
        play=findViewById(R.id.play);
        videoView= findViewById(R.id.videoview);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }
}
