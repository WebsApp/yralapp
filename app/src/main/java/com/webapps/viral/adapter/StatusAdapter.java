package com.webapps.viral.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.model.StoryModel;

import java.util.ArrayList;


public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ItemRowHolder> {

    private ArrayList<StoryModel> dataList;
    private Context mContext;
     private int AD_COUNT = 0;

    public StatusAdapter(Context context, ArrayList<StoryModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.status, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final StoryModel singleItem = dataList.get(position);
        holder.textViewname.setText(singleItem.getLinkText());
      //  holder.imageView.setImageResource(singleItem.getImage());
       if(singleItem.getType().equalsIgnoreCase("photo")) {
            Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc()).error(R.drawable.user1).into(holder.imageView);
            holder.videoView.setVisibility(View.GONE);
        }
       else {
           holder.imageView.setVisibility(View.GONE);
           holder.videoView.setVisibility(View.VISIBLE);
           holder.videoView.setVideoPath(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc());
           holder.videoView.start();
       }
    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname;
       ImageView imageView;
       VideoView videoView;
        RelativeLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.textView);
          videoView=itemView.findViewById(R.id.videoview);
            imageView = itemView.findViewById(R.id.image);
            lyt_parent=itemView.findViewById(R.id.root);
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);

            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            videoView.setLayoutParams(params);
        }
    }
}
