package com.webapps.viral.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
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
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.LanguageModel;
import com.webapps.viral.model.TopUserModel;

import java.util.ArrayList;
import java.util.Random;


public class Adapter extends RecyclerView.Adapter<Adapter.ItemRowHolder> {

    private ArrayList<LanguageModel.Datum> dataList=new ArrayList<>();

   // private ArrayList<LanguageModel.Datum> selecteddataList=new ArrayList<>();
    private Context mContext;
     private int AD_COUNT = 0;

    private  boolean status=false;
    private int selectedPosition = -1;
    private OnItemClickListener listene;
    public Adapter(Context context, ArrayList<LanguageModel.Datum> dataList) {
        this.dataList = dataList;
        this.mContext = context;
       // this.selecteddataList=selecteddataList;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final LanguageModel.Datum singleItem = dataList.get(position);
        holder.textViewname.setText(singleItem.getTitle());
       // holder.textView_desc.setText(singleItem.getDescription());
       // holder.textView_desc.setVisibility(View.GONE);
       // holder.imageView.setImageResource(singleItem.getImage());
        Picasso.get().load(singleItem.getImg()).into(holder.imageView);
       //   Picasso.with(mContext).load(singleItem.getImageUrl()).into(holder.imageView);
      /*  Random rnd = new Random();
        Random rnd2 = new Random();

        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
       // ColorDrawable c= new ColorDrawable(color);
        int color2 = Color.argb(255, rnd2.nextInt(256), rnd2.nextInt(256), rnd2.nextInt(256));
        Log.e("color",""+color+","+color2);
        int h = holder.lyt_parent.getHeight();
        ShapeDrawable mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setShader(new LinearGradient(0, 0, 0, h, color, color2, Shader.TileMode.REPEAT));
        holder.lyt_parent.setBackgroundDrawable(mDrawable);
*/
        // holder.lyt_parent.setBackgroundColor(color);

       /* if(selecteddataList.contains(dataList.get(position))) {
           // holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightgrey));
            holder.imageView1.setVisibility(View.VISIBLE);

        }else {
           // holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey));
            holder.imageView1.setVisibility(View.GONE);

        }
*/
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleItem.setCheck(!singleItem.isCheck());
                holder.imageView1.setVisibility(singleItem.isCheck() ? View.VISIBLE : View.INVISIBLE);
            }
        });
        holder.imageView1.setVisibility(singleItem.isCheck() ? View.VISIBLE : View.INVISIBLE);

    }
    public interface OnItemClickListener {
        void onItemClicked(String amount, Object object);
    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname;
        ImageView imageView,imageView1;
        LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.txt_name);
             imageView = itemView.findViewById(R.id.image);
            imageView1 = itemView.findViewById(R.id.image1);
            lyt_parent=itemView.findViewById(R.id.root);
        }
    }
    public ArrayList<LanguageModel.Datum> getAll() {
        return dataList;
    }

    public ArrayList<LanguageModel.Datum> getSelected() {
        ArrayList<LanguageModel.Datum> selected = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isCheck()) {
                selected.add(dataList.get(i));
            }
        }
        return selected;
    }
}