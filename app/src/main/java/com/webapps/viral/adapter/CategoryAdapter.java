package com.webapps.viral.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.EntertainmentActivity;
import com.webapps.viral.activity.StatusActivity;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.StoryModel;
import com.webapps.viral.model.UserStory;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRowHolder> {

    private ArrayList<UserStory> dataList;
    private Context mContext;
     private int AD_COUNT = 0;
     StoryClickListener storyClickListener;

    public CategoryAdapter(Context context, ArrayList<UserStory> dataList,StoryClickListener storyClickListener) {
        this.dataList = dataList;
        this.mContext = context;
       this.storyClickListener=storyClickListener;
    }
    public interface StoryClickListener {
        void showStory(int pos);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final UserStory singleItem = dataList.get(position);

        holder.textViewname.setText(singleItem.getName());
      //  holder.imageView.setImageResource(singleItem.getImage());
       // if(singleItem.getItems().get(position).getType().equalsIgnoreCase("photo")) {
            Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads"+singleItem.getPhoto()).error(R.drawable.user1).into(holder.imageView);
        //}

        Log.e("story path","https://theutrend.com/utredns_admires/content/uploads"+singleItem.getPhoto());

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname;
        CircleImageView imageView;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.textView);

            imageView = itemView.findViewById(R.id.image);
            lyt_parent=itemView.findViewById(R.id.root);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                            storyClickListener.showStory(pos);


                }
            });
        }
    }
}
