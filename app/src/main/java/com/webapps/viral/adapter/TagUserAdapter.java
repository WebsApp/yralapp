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
import com.webapps.viral.model.TopUserModel;

import java.util.ArrayList;
import java.util.List;


public class TagUserAdapter extends RecyclerView.Adapter<TagUserAdapter.ItemRowHolder> implements Filterable {

    private ArrayList<TopUserModel> employees;
    private ArrayList<TopUserModel> employeesfull;
    private Context mContext;
     private int AD_COUNT = 0;

    public TagUserAdapter(Context context, ArrayList<TopUserModel> dataList) {
        this.employees = dataList;
        this.mContext = context;
        this.employeesfull=dataList;

    }
 /*   public void setEmployees(ArrayList<TopUserModel> employees) {
        this.employees = new ArrayList<>();
        this.employees = employees;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_user_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final TopUserModel singleItem = employees.get(position);
          holder.textViewname.setText(singleItem.getUserName());
        Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getSrc()).error(R.drawable.c1).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleItem.setChecked(!singleItem.isChecked());
                holder.check.setVisibility(singleItem.isChecked() ? View.VISIBLE : View.INVISIBLE);
            }
        });
       holder.check.setVisibility(singleItem.isChecked() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return (null != employees ? employees.size() : 0);
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
       private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TopUserModel> filteredList = new ArrayList();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(employeesfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (TopUserModel item : employeesfull) {
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
            employees.clear();
            employees.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView textViewname,txt_title;
        ImageView imageView,check;
        RelativeLayout lyt_parent;
        private ItemRowHolder(View itemView) {
            super(itemView);
              textViewname = itemView.findViewById(R.id.textView);
              txt_title=itemView.findViewById(R.id.title);
              imageView = itemView.findViewById(R.id.image);
            check = itemView.findViewById(R.id.imageView);
              lyt_parent=itemView.findViewById(R.id.root);
        }
    }
    public ArrayList<TopUserModel> getAll() {
        return employees;
    }

    public ArrayList<TopUserModel> getSelected() {
        ArrayList<TopUserModel> selected = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).isChecked()) {
                selected.add(employees.get(i));
            }
        }
        return selected;
    }
   }
