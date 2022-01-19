package com.potholedetection.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.potholedetection.R;
import com.potholedetection.URL;

public class ShowImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        ImageView ig = findViewById(R.id.img);

        Intent i = getIntent();
        String url = i.getStringExtra("url");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(getApplicationContext()).load(url)
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .apply(requestOptions)
                .into(ig);
    }
}
