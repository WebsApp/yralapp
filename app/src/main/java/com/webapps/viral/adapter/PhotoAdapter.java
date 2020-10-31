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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.FullViewActivity;
import com.webapps.viral.activity.N_PostdetailsActivity;
import com.webapps.viral.activity.PostdetailsActivity;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PhotoModel;
import com.webapps.viral.model.TopUserModel;

import java.util.ArrayList;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ItemRowHolder> implements Filterable {

    private ArrayList<PhotoModel> dataList;
    private ArrayList<PhotoModel> exampleListFull;
    private Context mContext;
    private int AD_COUNT = 0;

    public PhotoAdapter(Context context, ArrayList<PhotoModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        exampleListFull = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final PhotoModel singleItem = dataList.get(position);
          //holder.txt_title.setText(singleItem.getTitle());
       // holder.textView_desc.setText(singleItem.getDescription());
        if(position==0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.textView_desc.setTextColor(mContext.getColor(R.color.idle_dot));
            }
        }
        //holder.txt_user.setText(singleItem.getName());
      //  holder.imageView.setImageResource(singleItem.getImage());
       Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc()).error(R.drawable.photo_error).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, N_PostdetailsActivity.class);
               /* i.putExtra("src",mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc());
                i.putExtra("name",singleItem.getId());
*/              i.putExtra("id",""+singleItem.getPostId());
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
        RelativeLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
          //  textViewname = itemView.findViewById(R.id.txt_name);
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
            ArrayList<PhotoModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PhotoModel item : exampleListFull) {
                    if (item.getSrc().toLowerCase().contains(filterPattern)) {
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
