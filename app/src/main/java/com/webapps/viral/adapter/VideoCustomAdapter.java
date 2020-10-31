package com.webapps.viral.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.webapps.viral.FullVideoActivity;
import com.webapps.viral.R;
import com.webapps.viral.activity.PostdetailsVideoViewPagerActivity;
import com.webapps.viral.model.PostModel;

import java.util.ArrayList;

public class VideoCustomAdapter extends PagerAdapter {
    Context context;
    private ArrayList<PostModel.Datum> dataList;
    LayoutInflater layoutInflater;
    public VideoCustomAdapter(Context context, ArrayList<PostModel.Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return dataList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.video_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
        Glide.with(context).load(context.getString(R.string.base_url)+"utredns_admires/content/uploads/" + dataList.get(position).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //storiesProgressView.resume();
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                //storiesProgressView.resume();
                return false;
            }
        }).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(imageView);
        container.addView(itemView);
        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(itemView.getContext(), FullVideoActivity.class);
                i.putExtra("url",dataList.get(position).getSource());
               itemView.getContext().startActivity(i);
              //  Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}