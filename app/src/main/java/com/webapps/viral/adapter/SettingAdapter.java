package com.webapps.viral.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapps.viral.AboutActivity;
import com.webapps.viral.R;
import com.webapps.viral.activity.ASettingActivity;
import com.webapps.viral.activity.AdActivity;
import com.webapps.viral.activity.AnalyticsActivity;
import com.webapps.viral.activity.ChangeEmailActivity;
import com.webapps.viral.activity.ChangePhoneActivity;
import com.webapps.viral.activity.DeleteAccountActivity;
import com.webapps.viral.activity.EditProfileActivity;
import com.webapps.viral.activity.LoginActivity;
import com.webapps.viral.activity.PrivacyActivity;
import com.webapps.viral.activity.QueryActivity;
import com.webapps.viral.activity.SavePostActivity;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ItemRowHolder> {

    private ArrayList<ChildModel> dataList;
    private Context mContext;
     private int AD_COUNT = 0;
   SharedPref sharedPref;
    public SettingAdapter(Context context, ArrayList<ChildModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        sharedPref= new SharedPref(mContext);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final ChildModel singleItem = dataList.get(position);

          holder.textViewname.setText(singleItem.getName());

        holder.textView_desc.setText(singleItem.getDescription());
if(position==10)
{
    holder.textView_desc.setVisibility(View.GONE);
}
        //holder.txt_user.setText(singleItem.getName());
        holder.imageView.setImageResource(singleItem.getImage());
     //  Picasso.with(mContext).load(singleItem.getImage()).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0) {
                     Intent i = new Intent(mContext, EditProfileActivity.class);
                          mContext.startActivity(i);
                }else if(position==1) {
                    Intent i = new Intent(mContext, ASettingActivity.class);
                    mContext.startActivity(i);
                }
                else if(position==2) {
                    Intent i = new Intent(mContext, ChangePhoneActivity.class);
                    mContext.startActivity(i);
                }else if(position==3) {
                    Intent i = new Intent(mContext, AnalyticsActivity.class);
                    mContext.startActivity(i);
                 }else if(position==4) {
                    Intent i = new Intent(mContext, AdActivity.class);
                    mContext.startActivity(i);
                }else if(position==5) {
                    Intent i = new Intent(mContext, PrivacyActivity.class);
                    mContext.startActivity(i);
                }else  if(position==6) {
                    Intent i = new Intent(mContext, ChangeEmailActivity.class);
                    mContext.startActivity(i);
                  }else  if(position==7) {
                    Intent i = new Intent(mContext, DeleteAccountActivity.class);
                    mContext.startActivity(i);
                }else  if(position==8) {
                    Intent i = new Intent(mContext, SavePostActivity.class);
                    mContext.startActivity(i);
                }else  if(position==9) {
                    Intent i = new Intent(mContext, AboutActivity.class);
                    mContext.startActivity(i);
                }else  if(position==10) {
                    Intent i = new Intent(mContext, QueryActivity.class);
                    mContext.startActivity(i);
                 }else  if(position==11){
                    sharedPref.clear();
                    Intent i = new Intent(mContext, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    mContext.startActivity(i);
                    ((Activity)mContext).finish();
                }
                }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname,txt_title;
        CircleImageView imageView;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
           textViewname = itemView.findViewById(R.id.textView);
             textView_desc = itemView.findViewById(R.id.text_desc);
          //  txt_title=itemView.findViewById(R.id.title);
             imageView = itemView.findViewById(R.id.image);
              lyt_parent=itemView.findViewById(R.id.root);
        }
    }
}
