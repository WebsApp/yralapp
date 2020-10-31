package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.webapps.viral.FullVideoActivity;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CommentAdapter;
import com.webapps.viral.model.CommentModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostdetailsActivity extends AppCompatActivity {

    ArrayList<CommentModel> childModels;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    Integer[] image ={R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
    String[] s= {"wow","good","nice","amazing","well"};
   TextView back,text_desc,liker,comment,share;
   VideoView videoView;
   ZoomageView imageView;
   String desc,user_id,slike,scomment,type2;
   SharedPref sharedPref;
   ImageView btn_post_comment,user_picture,like;
   EditText add_a_comment_edittext;
    String id,type,src,sshare;
    APIService service;
    Retrofit retrofit;
    String c="";
    private final int REQUEST_CODE_PERMISSION = 55;
    int li=0;
    private int si = 0,ci=0,sharei=0;
    String fileUri;
    ProgressDialog progressDialog;
    LinearLayout commentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        childModels= new ArrayList<>();
        text_desc=findViewById(R.id.text_desc);
        like=findViewById(R.id.like);
        liker=findViewById(R.id.liker);

        share=findViewById(R.id.share);
        comment=findViewById(R.id.comment);
        add_a_comment_edittext= findViewById(R.id.add_a_comment_edittext);
        btn_post_comment= findViewById(R.id.btn_post_comment);
        imageView=findViewById(R.id.image);
        videoView=findViewById(R.id.videoview);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        commentContainer=findViewById(R.id.commentContainer);
       /* Display display = getWindowManager().getDefaultDisplay();
        int width =  display.getWidth();
        int height = ((display.getHeight()*70)/100);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
        imageView.setLayoutParams(parms);
        */try {
            c=getIntent().getExtras().getString("cp");
            li=Integer.parseInt(getIntent().getExtras().getString("islike"));
            Log.e("Islike",""+li);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(c.equalsIgnoreCase("0")){

            commentContainer.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(PostdetailsActivity.this,"Comment is disabled",Toast.LENGTH_SHORT).show();
            commentContainer.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        user_picture=findViewById(R.id.user_picture);
        sharedPref= new SharedPref(this);
        //https://theutrend.com/UTREDN_ADMIN_VIRAL/content/uploads
        Glide.with(this).load(getString(R.string.base_url)+"utredns_admires/content/uploads"+sharedPref.getPic()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
        type=getIntent().getExtras().getString("type");
        type2=getIntent().getExtras().getString("type2");
        id=getIntent().getExtras().getString("id");
        src=getIntent().getExtras().getString("src");
        desc=getIntent().getExtras().getString("desc");
        slike=getIntent().getExtras().getString("like");
        scomment=getIntent().getExtras().getString("comment");
        sshare=getIntent().getExtras().getString("share");
        comment.setText(""+scomment);
        share.setText(""+sshare);
        liker.setText(slike);
        try {
            si = Integer.parseInt(slike);
            ci = Integer.parseInt(scomment);
            sharei = Integer.parseInt(sshare);
        }catch (Exception e){
            e.printStackTrace();
        }
        text_desc.setText(desc);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostdetailsActivity.this);
                alertDialog.setMessage("Where are you want to share?");
                alertDialog.setPositiveButton("Viral Utrend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        sharei=sharei+1;
                        sshare=""+sharei;
                        share.setText(""+sshare);
                        PostShare(id);
                    }
                });
                alertDialog.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Click the below link and download "+src;
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "The Utrend Viral");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));*/
                        if (checkStoragePermissions()) {
                            shareImage(getString(R.string.base_url)+"utredns_admires/content/uploads"+src);
                        }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
                        }
                    }
                    }
                });
                alertDialog.create().show();
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                videoView.pause();
                Intent i = new Intent(PostdetailsActivity.this, FullVideoActivity.class);
                i.putExtra("url",src);
                startActivity(i);
                return false;
            }
        });

      /*  imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostdetailsActivity.this, FullViewActivity.class);
                i.putExtra("src",getString(R.string.base_url)+"utredns_admires/content/uploads"+src);
                i.putExtra("name","0");
                startActivity(i);
            }
        });*/
        liker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostdetailsActivity.this, AllLikerActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
        if(li==1)
        {
            like.setImageResource(R.drawable.favorite);


        }else {
            like.setImageResource(R.drawable.favorite_border);
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(li==0)
                {
                    Drawable img = getResources().getDrawable(R.drawable.favorite);
                    img.setBounds(0, 0, 50, 50);
                    like.setImageResource(R.drawable.favorite);

                    si=si+1;
                    liker.setText(""+si);
                    li=1;
                }else {
                    si=si-1;
                    li=0;
                    liker.setText(""+si);
                    Drawable img = getResources().getDrawable(R.drawable.favorite_border);
                    img.setBounds(0, 0, 50, 50);
                    like.setImageResource(R.drawable.favorite_border);

                }
                AddLike();
            }
        });
        if(type.equalsIgnoreCase("photos"))
        {
            /*Glide.with(this).load(getString(R.string.base_url)+"utredns_admires/content/uploads"+src).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).listener(new RequestListener<Drawable>() {
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
            }).into(imageView);
*/
            Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+src).error(R.drawable.photo_error).into(imageView);
        }else if(type.equalsIgnoreCase("video"))
        {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(getString(R.string.base_url)+"utredns_admires/content/uploads"+src);
            videoView.setVideoURI(uri);
            videoView.start();

        }else  if(type.equalsIgnoreCase("post"))
        {
             imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);

        }
        else if(type.equalsIgnoreCase("shared"))
        {
            if(type2.equalsIgnoreCase("photos")) {

                Glide.with(this).load(getString(R.string.base_url)+"utredns_admires/content/uploads"+src).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
                }).into(imageView);
            }
            else  if(type2.equalsIgnoreCase("video"))
            {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(getString(R.string.base_url)+"utredns_admires/content/uploads"+src);
                videoView.setVideoURI(uri);
                videoView.start();
            }else  if(type2.equalsIgnoreCase("post"))
            {
               // Log.e("L",""+singleItem.getText());
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
            }
        }
       /* comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostdetailsActivity.this,CommentActivity.class);
              i.putExtra("id",""+id);
              i.putExtra("desc",""+desc);
                startActivity(i);
            }
        });*/
      //  recyclerView.setAdapter(notificationAdapter);
    back= findViewById(R.id.back);
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    });
        progressDialog = new ProgressDialog(PostdetailsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
      //  progressDialog.show();
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
                ci=ci+1;
                comment.setText(""+ci);
AddComment();
            }
        }
    });
    }
    public void shareImage(String url) {
        Log.e("URK",""+url);
        Picasso.get().load(url).into(new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/viral");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(fileUri),null,null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT,"Welcome to the utrend Viral download our App https://play.google.com/store/apps/details?id="+getPackageName());
                startActivity(Intent.createChooser(share, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }
    private boolean checkStoragePermissions() {
        return
                ContextCompat.checkSelfPermission(PostdetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(PostdetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(PostdetailsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && checkStoragePermissions()) {

        }
    }
    public  void AddComment()
    {
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(cDate);
        Log.e("Time",""+fDate);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

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
                Toast.makeText(PostdetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

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

                    childModels.addAll(response.body());
                    if(childModels.size()>0) {
                       // commentAdapter.notifyDataSetChanged();
                        commentAdapter = new CommentAdapter(PostdetailsActivity.this, childModels);
                        recyclerView.setAdapter(commentAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }}catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CommentModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(PostdetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }

    public  void AddLike()
    {
       /* SharedPref sharedPref= new SharedPref(mContext);
       String id=sharedPref.getID();*/
        APIService service;
        Retrofit retrofit;
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(cDate);
        Log.e("Time",""+fDate);

        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        Call<RegisterModel> userCall = service.AddLike(id,user_id,fDate);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {


                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //hidepDialog();
                Toast.makeText(PostdetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void PostShare(String  post_id)
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String cDate = format.format(c);
        SharedPref sharedPref= new SharedPref(PostdetailsActivity.this);
        String id=sharedPref.getID();
        APIService service;
        Retrofit retrofit;
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        Call<RegisterModel> userCall = service.sharepost(id,post_id,cDate);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //hidepDialog();
                Toast.makeText(PostdetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
