package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.webapps.viral.R;

public class FullViewActivity extends AppCompatActivity {

    ZoomageView imageView;
    String src,name;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        imageView=findViewById(R.id.imageView);
        src=getIntent().getExtras().getString("src");
        name=getIntent().getExtras().getString("name");

        Picasso.get().load(src).error(R.drawable.nopreview).into(imageView);
        back=findViewById(R.id.back);
        back.setText("");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
