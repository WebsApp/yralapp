package com.webapps.viral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.model.BannerModel;

import java.util.ArrayList;

public class ImageSilder extends PagerAdapter {

    private Context context;
    String url;
    private ArrayList<BannerModel.Login> logins;
    private ArrayList<String> colorName;

    public ImageSilder(Context context, ArrayList<BannerModel.Login> logins,String url) {
        this.context = context;
        this.logins = logins;
        this.url=url;
    }

    @Override
    public int getCount() {
        return logins.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        ImageView textView =  view.findViewById(R.id.imageview);
        ImageView imageView2 =  view.findViewById(R.id.image2);
        Picasso.get().load(logins.get(position).getImage()).into(textView);

        Picasso.get().load(url).into(imageView2);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}