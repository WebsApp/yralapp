package com.webapps.viral.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.ankushsachdeva.emojicon.EmojiconTextView;
import com.webapps.viral.R;
import com.webapps.viral.model.PostModel;

import java.util.ArrayList;


public class VideoViewPagerAdapter extends RecyclerView.Adapter<VideoViewPagerAdapter.ItemRowHolder> {

    ArrayList<PostModel.Datum> pager;

    Context context;
    public VideoViewPagerAdapter(ArrayList<PostModel.Datum> pager, Context context) {
         this.context = context;
        this.pager = pager;
    }
    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_pager_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        Glide.with(context).load(context.getString(R.string.base_url)+"utredns_admires/content/uploads/" + pager.get(position).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
        }).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.imageView);
    }

    @Override
    public int getItemCount()
    {
        Log.e("ggh",""+pager.size());
        return  (null != pager ? pager.size() : 0);
    }
    public void setItems(@NonNull final ArrayList<String> items) {
        pager.clear();
     //   pager.addAll(items);
    }
    public class ItemRowHolder extends RecyclerView.ViewHolder {

        EmojiconTextView textView_desc;
        TextView textViewname,time;
        ImageView imageView;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
             imageView = (ImageView) itemView.findViewById(R.id.image);
   }
    }
}
