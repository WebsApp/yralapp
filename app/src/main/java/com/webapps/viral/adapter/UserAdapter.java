package com.webapps.viral.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.UserDetailsActivity;
import com.webapps.viral.model.LanguageModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.model.UserModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemRowHolder> {

    private ArrayList<UserModel> dataList;
    private Context mContext;
     private int AD_COUNT = 0;

    private  boolean status=false;
    private int selectedPosition = -1;
    private OnItemClickListener listene;
    APIService service;
    Retrofit retrofit;
    SharedPref sharedPref;
    String  user_id;
    public UserAdapter(Context context, ArrayList<UserModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        sharedPref= new SharedPref(mContext);
        retrofit = RetrofitClient.getClient(mContext.getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
          user_id=sharedPref.getID();
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final UserModel singleItem = dataList.get(position);

        holder.textusername.setText(""+singleItem.getPostId());
       if(singleItem.getCity()!=null) {
           holder.location.setText("" + singleItem.getCity());
       }
        try {
            Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads"+singleItem.getSrc()).error(R.drawable.user2).into(holder.profile_pic);
        }catch (Exception ex){}
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow(singleItem.getId(),position);
            }
        });
        holder.btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, UserDetailsActivity.class);
                i.putExtra("id",dataList.get(position).getId());
                mContext.startActivity(i);
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });
    }
    public void removeAt(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }
    public interface OnItemClickListener {
        void onItemClicked(String amount, Object object);
    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }
    public  void Follow(String  id,int selectedPosition)
    {
        Call<RegisterModel> userCall = service.AddFollow(id,user_id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();

                          Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        removeAt(selectedPosition);
                    }
                    else {
                        Toast.makeText(mContext,""+response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //hidepDialog();
                //  Toast.makeText(UserDetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView location,textusername;
        ImageView profile_pic,cover_pic,close;
        RelativeLayout lyt_parent;
       TextView follow,btnview;
        private ItemRowHolder(View itemView) {
            super(itemView);
            textusername = itemView.findViewById(R.id.txt_username);
             location = itemView.findViewById(R.id.location);
            cover_pic = itemView.findViewById(R.id.cover_pic);
            profile_pic=itemView.findViewById(R.id.profile_pic);
            close=itemView.findViewById(R.id.close);
            follow=itemView.findViewById(R.id.follow);
            btnview=itemView.findViewById(R.id.view);

        }
    }
}
