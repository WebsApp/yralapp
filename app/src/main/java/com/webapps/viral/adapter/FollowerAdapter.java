package com.webapps.viral.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.UserDetailsActivity;
import com.webapps.viral.model.Follower;
import com.webapps.viral.model.TopUserModel;

import java.util.ArrayList;


public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ItemRowHolder>  {

    private ArrayList<Follower> dataList;
    private ArrayList<Follower> exampleListFull;
    private Context mContext;
     private int AD_COUNT = 0;

    public FollowerAdapter(Context context, ArrayList<Follower> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        exampleListFull = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final Follower singleItem = dataList.get(position);


        if(position==0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.textView_desc.setTextColor(mContext.getColor(R.color.idle_dot));
            }
        }
        holder.textViewname.setText(singleItem.getUserName());
         Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getUserPicture()).error(R.drawable.c1).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(mContext, UserDetailsActivity.class);
               i.putExtra("id",dataList.get(position).getUserId());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname,txt_title;
        ImageView imageView;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
           textViewname = itemView.findViewById(R.id.textView);
             textView_desc = itemView.findViewById(R.id.text_desc);
            txt_title=itemView.findViewById(R.id.title);
             imageView = itemView.findViewById(R.id.image);
              lyt_parent=itemView.findViewById(R.id.root);
        }
    }

}
