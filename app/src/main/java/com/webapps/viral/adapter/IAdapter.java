package com.webapps.viral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.model.InterestModel;
import com.webapps.viral.model.LanguageModel;

import java.util.ArrayList;


public class IAdapter extends RecyclerView.Adapter<IAdapter.ItemRowHolder> {

    private ArrayList<InterestModel.Datum> dataList;
    private Context mContext;
     private int AD_COUNT = 0;

    private  boolean status=false;
    private int selectedPosition = -1;
    private OnItemClickListener listene;
    public IAdapter(Context context, ArrayList<InterestModel.Datum> dataList) {
        this.dataList = dataList;
        this.mContext = context;
     //   this.selectedlist=selectedlist;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final InterestModel.Datum singleItem = dataList.get(position);
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

    public ArrayList<InterestModel.Datum> getAll() {
        return dataList;
    }

    public ArrayList<InterestModel.Datum> getSelected() {
        ArrayList<InterestModel.Datum> selected = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isCheck()) {
                selected.add(dataList.get(i));
            }
        }
        return selected;
    }
}
