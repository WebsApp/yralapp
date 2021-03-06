package com.webapps.viral.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.activity.AllLikerActivity;
import com.webapps.viral.activity.EditPostActivity;
import com.webapps.viral.activity.PostdetailsActivity;
import com.webapps.viral.activity.PostdetailsVideoViewPagerActivity;
import com.webapps.viral.activity.PostdetailsViewPagerActivity;
import com.webapps.viral.activity.UserDetailsActivity;
import com.webapps.viral.model.PostModel;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ItemRowHolder> {
    private ArrayList<PostModel> dataList;
    private Context mContext;
    private int i = 0;
    String type;
    ProgressDialog progressDialog;
    String fileUri;
    boolean [] li;
    SharedPref sharedPref;
    public MyPostAdapter(Context context, ArrayList<PostModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        li= new boolean[dataList.size()];
        for (i=0;i<dataList.size();i++){
            li[i]=false;
        }
        sharedPref=new SharedPref(mContext);
    }
    public void clear() {
        int size = dataList.size();
        dataList.clear();
        notifyItemRangeRemoved(0, size);
    }
    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_item, parent, false);
        return new ItemRowHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final PostModel singleItem = dataList.get(position);
          holder.textViewname.setText(singleItem.getUsername());
          holder.txt_post.setText(singleItem.getText());
      try {
           Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/" + singleItem.getPic()).error(R.drawable.user2).into(holder.userimage);
        }catch (Exception e){}
       if(singleItem.getType().equalsIgnoreCase("photos")) {
           if(singleItem.getData().size()==1) {
               holder.viewPager.setVisibility(View.VISIBLE);
               holder.rl_viewpager.setVisibility(View.VISIBLE);
               Glide.with(mContext).load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/" + singleItem.getData().get(0).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
               }).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.imageView);
           }
           else if (singleItem.getData().size()>1){
               Log.e("len",""+singleItem.getData().size());
               ((ItemRowHolder) holder).imageView.setVisibility(View.GONE);
               holder.viewPager.setVisibility(View.VISIBLE);
               holder.rl_viewpager.setVisibility(View.VISIBLE);
               holder.viewPager.setHasFixedSize(false);
               holder.viewPager.setNestedScrollingEnabled(true);
               holder.viewPager.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
               ViewAdapter pagerAdapter = new ViewAdapter(singleItem.getData(),mContext);
               holder.viewPager.setAdapter(pagerAdapter);
               holder.scrollingPagerIndicator.attachToRecyclerView(holder.viewPager);
           }   holder.play.setVisibility(View.GONE);
       }
       else  if(singleItem.getType().equalsIgnoreCase("video"))
       {
           holder.textView_desc.setVisibility(View.VISIBLE);
           if(singleItem.getData().size()==1) {
               holder.viewPager.setVisibility(View.GONE);
               holder.rl_viewpager.setVisibility(View.GONE);

               holder.play.setVisibility(View.VISIBLE);
               Glide.with(mContext).load(mContext.getString(R.string.base_url) + "utredns_admires/content/uploads/" + singleItem.getData().get(0).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
               }).into(holder.imageView);
           }
           else if (singleItem.getData().size()>1){
               holder.viewPager.setVisibility(View.VISIBLE);
               holder.rl_viewpager.setVisibility(View.VISIBLE);
               holder.imageView.setVisibility(View.GONE);
               holder.viewPager.setNestedScrollingEnabled(true);
               holder.viewPager.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
               VideoViewPagerAdapter pagerAdapter = new VideoViewPagerAdapter(singleItem.getData(),mContext);
               holder.viewPager.setAdapter(pagerAdapter);

               holder.scrollingPagerIndicator.attachToRecyclerView(holder.viewPager);
           }
       }else  if(singleItem.getType().equalsIgnoreCase("post"))
       {
           Log.e("L",""+singleItem.getText());
           holder.play.setVisibility(View.GONE);
           holder.imageView.setVisibility(View.GONE);
           holder.viewPager.setVisibility(View.GONE);
           holder.rl_viewpager.setVisibility(View.GONE);

           holder.textView_desc.setVisibility(View.VISIBLE);
           holder.textView_desc.setText(""+singleItem.getText());
       }
       else if(singleItem.getType().equalsIgnoreCase("shared"))
       {
           if(singleItem.getStype().equalsIgnoreCase("photos")) {
               holder.viewPager.setVisibility(View.GONE);
               holder.rl_viewpager.setVisibility(View.GONE);
               holder.imageView.setVisibility(View.VISIBLE);
               holder.play.setVisibility(View.GONE);

               Glide.with(mContext).load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getData().get(0).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
               }).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(holder.imageView);
               // Picasso.get().load("" + singleItem.getData().get(0).getSource()).error(R.drawable.user1).into(holder.imageView);
               holder.play.setVisibility(View.GONE);
           }
           else  if(singleItem.getStype().equalsIgnoreCase("video"))
           {
               holder.play.setVisibility(View.VISIBLE);
               holder.viewPager.setVisibility(View.GONE);
               holder.rl_viewpager.setVisibility(View.GONE);
               holder.imageView.setVisibility(View.VISIBLE);

               Glide.with(mContext).load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/"+singleItem.getData().get(0).getSource()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
               }).into(holder.imageView);
           }else  if(singleItem.getStype().equalsIgnoreCase("post"))
           {
               Log.e("L",""+singleItem.getText());
               holder.play.setVisibility(View.GONE);
               holder.imageView.setVisibility(View.GONE);
               holder.viewPager.setVisibility(View.GONE);
               holder.rl_viewpager.setVisibility(View.GONE);

               holder.textView_desc.setVisibility(View.VISIBLE);
               holder.textView_desc.setText(""+singleItem.getText());
           }
           String text = singleItem.getSusername()+"<font color="+mContext.getResources().getColor(R.color.lightgrey)+"> share</font> <font color=#cc0029>"+singleItem.getUsername()+"</font> <font color="+mContext.getResources().getColor(R.color.lightgrey)+">'Posts</font>";
           holder.textViewname.setText(Html.fromHtml(text));
           // holder.textViewname.setText(singleItem.getSusername()+" share ");
           try {
               Picasso.get().load(mContext.getString(R.string.base_url)+"utredns_admires/content/uploads/" + singleItem.getSpic()).error(R.drawable.user2).into(holder.userimage);
           }catch (Exception e){}
       }

        holder. viewPager.setOnTouchListener(
                new View.OnTouchListener() {
                    private boolean moved;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            moved = false;
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            moved = true;
                        }
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (!moved) {
                                view.performClick();
                            }
                        }

                        return false;
                    }
                }
        );

// then you can simply use the standard onClickListener ...
        holder.viewPager.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if(singleItem.getType().equalsIgnoreCase("video")){
                            Intent i = new Intent(mContext, PostdetailsVideoViewPagerActivity.class);
                            if (singleItem.getType().equalsIgnoreCase("shared")) {
                                Log.e("ID ", "" + dataList.get(position).getPost_id());
                                i.putExtra("id", dataList.get(position).getPost_id());
                            } else {
                                i.putExtra("id", dataList.get(position).getData().get(0).getPostId());
                            }
                            i.putExtra("desc", dataList.get(position).getText());
                            i.putExtra("comment", singleItem.getComments());
                            i.putExtra("like", singleItem.getReactionLikeCount());
                            i.putExtra("share", singleItem.getShare());
                            i.putExtra("cp", singleItem.getComments_disabled());
                            i.putExtra("islike",""+ singleItem.getIslike());

                            mContext.startActivity(i);
                        }else {
                            Intent i = new Intent(mContext, PostdetailsViewPagerActivity.class);
                            if (singleItem.getType().equalsIgnoreCase("shared")) {
                                Log.e("ID ", "" + dataList.get(position).getPost_id());
                                i.putExtra("id", dataList.get(position).getPost_id());
                            } else {
                                i.putExtra("id", dataList.get(position).getData().get(0).getPostId());
                            }
                            i.putExtra("desc", dataList.get(position).getText());
                            i.putExtra("comment", singleItem.getComments());
                            i.putExtra("like", singleItem.getReactionLikeCount());
                            i.putExtra("share", singleItem.getShare());
                            i.putExtra("cp", singleItem.getComments_disabled());
                            i.putExtra("islike",""+ singleItem.getIslike());

                            mContext.startActivity(i);
                        }
                    }
                } );

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PostdetailsActivity.class);
                i.putExtra("id",dataList.get(position).getData().get(0).getPostId());
                i.putExtra("src",dataList.get(position).getData().get(0).getSource());
                i.putExtra("type",dataList.get(position).getType());
                i.putExtra("type2",dataList.get(position).getStype());
                i.putExtra("desc",dataList.get(position).getText());
                i.putExtra("comment",singleItem.getComments());
                i.putExtra("like",singleItem.getReactionLikeCount());
                i.putExtra("share",singleItem.getShare());
                i.putExtra("cp", singleItem.getComments_disabled());
                i.putExtra("islike",""+ singleItem.getIslike());

                mContext.startActivity(i);
            }
        });
       holder.textViewname.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              /* Intent i = new Intent(mContext, UserDetailsActivity.class);
               i.putExtra("id",dataList.get(position).getUser_id());
               mContext.startActivity(i);*/
           }
       });
        holder.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, UserDetailsActivity.class);
                i.putExtra("id",dataList.get(position).getUser_id());
                mContext.startActivity(i);
            }
        });
        holder.liker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, AllLikerActivity.class);
                i.putExtra("id",dataList.get(position).getPost_id());
                mContext.startActivity(i);
            }
        });
       if(singleItem.getComments()==null)
       {  singleItem.setComments("0");

           holder.comment.setText("0");
       }else {
           holder.comment.setText(""+singleItem.getComments());

       }
       if(singleItem.getReactionLikeCount()==null)
        {
            holder.liker.setText("0");
            singleItem.setReactionLikeCount("0");

        }else {
        holder.liker.setText(""+singleItem.getReactionLikeCount());
        }
        if(singleItem.getShare()==null)
        { holder.share.setText("0");
            singleItem.setShare("0");

        }else {
            holder.share.setText(""+singleItem.getShare());
        }
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setMessage("Where are you want to share?");
                alertDialog.setPositiveButton("Viral Utrend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        int s=Integer.parseInt(singleItem.getShare());
                        Log.e("a",""+(s++));
                        PostShare(""+singleItem.getData().get(0).getPostId());
                        singleItem.setShare(""+s);
                        holder.share.setText(""+s);
                    }
                });
                alertDialog.setNegativeButton("Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(singleItem.getData().size()>1){
                            multipleshare(singleItem.getData());
                        }else {
                            shareImage(mContext.getString(R.string.base_url) + "utredns_admires/content/uploads/" + dataList.get(position).getData().get(0).getSource());
                        }
                    }
                });
                alertDialog.create().show();
            }
        });

        if(sharedPref.getID().equalsIgnoreCase(singleItem.getUser_id()))
        {
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.delete.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.delete);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_main);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                alertDialog.setMessage("are you sure  to delete this post?");
                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        DeletePost(""+singleItem.getData().get(0).getPostId(),position);
                                    }
                                });
                                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                    }
                                });
                                alertDialog.create().show();
                                return true;
                            case R.id.edit:
                                Intent i = new Intent(mContext, EditPostActivity.class);
                                i.putExtra("id",dataList.get(position).getPost_id());
                                i.putExtra("type",type);
                                mContext.startActivity(i); return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();


            }
        });

        if(singleItem.getIslike()==1){

            //Drawable img = mContext.getResources().getDrawable(R.drawable.favorite);
           // img.setBounds(0, 0, 60, 60);
            holder.like.setImageResource(R.drawable.favorite);

        }else {
            //Drawable img = mContext.getResources().getDrawable(R.drawable.favorite_border);
           // img.setBounds(0, 0, 60, 60);
            holder.like.setImageResource(R.drawable.favorite_border);
        }
        Drawable img = mContext.getResources().getDrawable(R.drawable.comment);
        img.setBounds(0, 0, 60, 60);
        holder.comment.setCompoundDrawables(img,null,null,null);
        Drawable img2 = mContext.getResources().getDrawable(R.drawable.share);
        img2.setBounds(0, 0, 60, 60);
        holder.share.setCompoundDrawables(img2,null,null,null);

        holder.like.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

         i=Integer.parseInt(singleItem.getReactionLikeCount());
        if(li[position]==false){
            li[position] = true;
            i=i+1;
            Drawable img = mContext.getResources().getDrawable(R.drawable.favorite);
            img.setBounds(0, 0, 60, 60);
            holder.like.setImageResource(R.drawable.favorite);

            singleItem.setIslike(1);
        }else {
            singleItem.setIslike(0);
            li[position] = false;
            i = i - 1;
            Drawable img = mContext.getResources().getDrawable(R.drawable.favorite_border);
            img.setBounds(0, 0, 60, 60);
            holder.like.setImageResource(R.drawable.favorite_border);

        }
        AddLike("" + singleItem.getPost_id());
        //  AddLike(""+singleItem.getData().get(0).getPostId());
       Log.e("a",""+i);
       singleItem.setReactionLikeCount(""+i);
        holder.liker.setText(""+i);
    }
});
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date c = Calendar.getInstance().getTime();
         String cDate = simpleDateFormat.format(c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate ="";
        try {
       Date  date = format.parse(singleItem.getTime());
              formattedDate=    simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long different;
            Date date1 = simpleDateFormat.parse(formattedDate);
            Date date2 = simpleDateFormat.parse(cDate);
            if(date2.getTime() < date1.getTime()){

                different = ((date2.getTime()+43200000) - date1.getTime());
            }
            else {
                different = date2.getTime() - date1.getTime();
            }
             long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;
            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;
            long elapsedSeconds = different / secondsInMilli;
         if(elapsedDays!=0)
         {
             holder.time.setText(elapsedDays+" Days Ago");
         }else if(elapsedHours!=0)
         {
             holder.time.setText(elapsedHours+" Hours Ago");

         }else if(elapsedMinutes!=0){
             holder.time.setText(elapsedMinutes+" Minutes Ago");

         }
         else {
             holder.time.setText(elapsedSeconds+" Seconds Ago");

         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void multipleshare(ArrayList<PostModel.Datum> a)
    {
        ArrayList<Uri> files = new ArrayList<Uri>();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "Welcome to the utrend Viral download our App https://play.google.com/store/apps/details?id=" + mContext.getPackageName());

        for(int i =0;i<a.size();i++) {
            String path =mContext.getString(R.string.base_url) + "utredns_admires/content/uploads"+a.get(i).getSource();
            Log.e("Pat",""+path);
            Uri uri= Uri.parse(path);
            Picasso.get().load(path).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        File mydir = new File(Environment.getExternalStorageDirectory() + "/viral");
                        if (!mydir.exists()) {
                            mydir.mkdirs();
                        }
                        Log.e("Exception", " vv1");
                        fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                        FileOutputStream outputStream = new FileOutputStream(fileUri);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        Log.e("Exception", " vv" + e);
                        e.printStackTrace();
                    }
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), BitmapFactory.decodeFile(fileUri), null, null));
                    files.add(uri);
                    // use intent to share image
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e("Exception", " vv" + e);
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
        if(!files.isEmpty()) {
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            mContext.startActivity(intent);
        }
        else{
            progressDialog= new ProgressDialog(mContext);
            progressDialog.setTitle("Please Wait...");
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                    mContext.startActivity(intent);
                }
            },5000);
        }
    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView textView_desc,textViewname,txt_post;
        ImageView imageView,play,delete,like;
        CircleImageView userimage;
        TextView liker,comment,share,time;
        LinearLayout lyt_parent;
        RecyclerView viewPager;
        ScrollingPagerIndicator scrollingPagerIndicator;
        RelativeLayout rl_viewpager;
        private ItemRowHolder(View itemView) {
            super(itemView);
            scrollingPagerIndicator=itemView.findViewById(R.id.indicator);
            rl_viewpager=itemView.findViewById(R.id.rl_viewpager);
            viewPager=itemView.findViewById(R.id.viewpager);
            textViewname = itemView.findViewById(R.id.txt_name);
          textView_desc = itemView.findViewById(R.id.text_desc);
            txt_post=itemView.findViewById(R.id.text_post);
              imageView = itemView.findViewById(R.id.image);
            like = itemView.findViewById(R.id.like);
            liker = itemView.findViewById(R.id.liker);

            time= itemView.findViewById(R.id.time);
            comment = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            userimage = itemView.findViewById(R.id.img);

            delete=itemView.findViewById(R.id.delete);
            play = itemView.findViewById(R.id.play);
            lyt_parent=itemView.findViewById(R.id.root);
        }
    }
    public  void AddLike(String  id)
    {
        SharedPref sharedPref= new SharedPref(mContext);
       String uid=sharedPref.getID();
        APIService service;
        Retrofit retrofit;
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(cDate);
        Log.e("Time",""+fDate);

        retrofit = RetrofitClient.getClient(mContext.getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        Call<RegisterModel> userCall = service.AddLike(id,uid,fDate);
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
                Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void DeletePost(String  id,int p)
    {
        SharedPref sharedPref= new SharedPref(mContext);
        String uid=sharedPref.getID();
        APIService service;
        Retrofit retrofit;

        retrofit = RetrofitClient.getClient(mContext.getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        Call<RegisterModel> userCall = service.PostDelete(uid,id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {
                    removeAt(p);

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //hidepDialog();
                Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public void removeAt(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }
    public  void PostShare(String  post_id)
    {Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String cDate = format.format(c);
        SharedPref sharedPref= new SharedPref(mContext);
       String id=sharedPref.getID();
        APIService service;
        Retrofit retrofit;

        retrofit = RetrofitClient.getClient(mContext.getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        Call<RegisterModel> userCall = service.sharepost(id,post_id,cDate);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {

                    Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //hidepDialog();
               // Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
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
                Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(mContext.getContentResolver(), BitmapFactory.decodeFile(fileUri),null,null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT,"Welcome to the utrend Viral download our app https://play.google.com/store/apps/details?id="+mContext.getPackageName());
                mContext.startActivity(Intent.createChooser(share, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }
}
