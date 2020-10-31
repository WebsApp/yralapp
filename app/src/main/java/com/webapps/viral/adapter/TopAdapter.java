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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.UserDetailsActivity;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.TopUserModel;

import java.util.ArrayList;
import java.util.List;


public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ItemRowHolder> implements Filterable {

    private ArrayList<TopUserModel> dataList;
    private ArrayList<TopUserModel> exampleListFull;
    private Context mContext;
     private int AD_COUNT = 0;

    public TopAdapter(Context context, ArrayList<TopUserModel> dataList) {
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
        final TopUserModel singleItem = dataList.get(position);

          holder.textViewname.setText(singleItem.getUserName());

          if(singleItem.getUserBio()!=null) {
              holder.textView_desc.setText("" + singleItem.getUserBio());
          }else {
              holder.textView_desc.setText("");

          }
        if(position==0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.textView_desc.setTextColor(mContext.getColor(R.color.idle_dot));
            }
        }
        //holder.txt_user.setText(singleItem.getName());
         Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc()).error(R.drawable.c1).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(mContext, UserDetailsActivity.class);
               i.putExtra("id",dataList.get(position).getId());
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
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TopUserModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (TopUserModel item : exampleListFull) {
                    if (item.getUserName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
