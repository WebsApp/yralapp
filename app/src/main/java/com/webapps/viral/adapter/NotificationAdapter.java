package com.webapps.viral.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.webapps.viral.R;
import com.webapps.viral.activity.N_PostdetailsActivity;
import com.webapps.viral.activity.PostdetailsActivity;
import com.webapps.viral.activity.UserDetailsActivity;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.NotificationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ItemRowHolder> {

    private ArrayList<NotificationModel> dataList;
    private Context mContext;
     private int AD_COUNT = 0;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final NotificationModel singleItem = dataList.get(position);
          holder.textViewname.setText(singleItem.getUserName());
          holder.textView_desc.setText(singleItem.getText());
          Glide.with(mContext).load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getUserPicture()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
        }).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, UserDetailsActivity.class);
                i.putExtra("id",""+singleItem.getUserId());
                mContext.startActivity(i);
            }
        });
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, N_PostdetailsActivity.class);
                i.putExtra("id",""+singleItem.getPostId());
                mContext.startActivity(i);
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        String cDate = simpleDateFormat.format(c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String formattedDate ="";
        try {
            Date  date = format.parse(singleItem.getTime());
            System.out.println(date);
            formattedDate=    simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long different=0;
            Date date1 = simpleDateFormat.parse(formattedDate);
            Date date2 = simpleDateFormat.parse(cDate);
            if(date2.getTime() < date1.getTime()){

                different = ((date2.getTime() + 43200000) - date1.getTime());

            }
            else {
                different = date2.getTime() - date1.getTime();
            }
            System.out.println("startDate : " + date1);
            System.out.println("endDate : "+ date2);
            System.out.println("different : " + different);
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;
            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;
            long elapsedSeconds = different / secondsInMilli;
            if(elapsedDays!=0)
            {
                holder.time.setText(elapsedDays+" Days Ago");
            }else if(elapsedHours!=0)
            {
                holder.time.setText(elapsedHours+" Hours Ago");

            }else if(elapsedMinutes!=0){
                holder.time.setText(elapsedMinutes+" Minutes Ago");

            }
            else {
                holder.time.setText(elapsedSeconds+" Seconds Ago");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname,time;
        CircleImageView imageView;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.textView);
             textView_desc = itemView.findViewById(R.id.text_desc);
            time=itemView.findViewById(R.id.time);
             imageView = itemView.findViewById(R.id.image);
              lyt_parent=itemView.findViewById(R.id.root);
        }
    }
}
