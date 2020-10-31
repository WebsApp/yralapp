package com.webapps.viral.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.ankushsachdeva.emojicon.EmojiconEditText;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CommentAdapter;
import com.webapps.viral.model.CommentModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentActivity extends AppCompatActivity {



    String desc,user_id,id,scomment,type2;
    SharedPref sharedPref;
    TextView back,text_desc;
    ImageView btn_post_comment,user_picture;
    EmojiconEditText add_a_comment_edittext;
    ProgressDialog progressDialog;
    ArrayList<CommentModel> childModels;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    Retrofit retrofit;
    APIService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);



        childModels= new ArrayList<>();
        add_a_comment_edittext= findViewById(R.id.add_a_comment_edittext);
        btn_post_comment= findViewById(R.id.btn_post_comment);
        text_desc=findViewById(R.id.text_desc);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        user_picture=findViewById(R.id.user_picture);
        sharedPref= new SharedPref(this);
        Glide.with(this).load(getString(R.string.base_url)+"utredns_admires/content/uploads"+sharedPref.getPic()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.user2)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                //storiesProgressView.resume();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                //storiesProgressView.resume();
                return false;
            }
        }).into(user_picture);

        user_id=sharedPref.getID();
        id=getIntent().getExtras().getString("id");
        desc=getIntent().getExtras().getString("desc");

        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(CommentActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        GetComment();
        btn_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_a_comment_edittext.getText().length()==0)
                {
                    add_a_comment_edittext.setError("Cann't Empty");
                }else{

                    AddComment();
                }
            }
        });
    }
    public  void AddComment()
    {
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(cDate);
        Log.e("Time",""+fDate);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Log.e("t",""+add_a_comment_edittext.getText().toString());
        Call<RegisterModel> userCall = service.AddComment(user_id,id,""+fDate,add_a_comment_edittext.getText().toString(),"post","user");
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        add_a_comment_edittext.setText("");
                        GetComment();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(CommentActivity.this,"Network Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GetComment()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<CommentModel>> userCall = service.GetComment(id);
        userCall.enqueue(new Callback<ArrayList<CommentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentModel>> call, Response<ArrayList<CommentModel>> response) {
                progressDialog.dismiss();
                try {
                    childModels.clear();

                    Log.e("response",""+response.body());
                    childModels.addAll(response.body());
                    if(childModels.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        commentAdapter = new CommentAdapter(CommentActivity.this, childModels);
                        recyclerView.setAdapter(commentAdapter);
                        commentAdapter.notifyDataSetChanged();
                    }}catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CommentModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(CommentActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}