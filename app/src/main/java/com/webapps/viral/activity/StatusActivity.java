package com.webapps.viral.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.webapps.viral.FullStoryActivity;
import com.webapps.viral.FullVideoActivity;
import com.webapps.viral.R;
import com.webapps.viral.adapter.StatusAdapter;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.model.StoryModel;
import com.webapps.viral.model.UserStory;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.Helper;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;
import com.webapps.viral.utils.SharedPreferenceUtil;
import com.webapps.viral.utils.VideoPlayerConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.shts.android.storiesprogressview.StoriesProgressView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatusActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener{
    private StoriesProgressView storiesProgressView;
    private int counterImage = 0, counterStory = 0;
    private ImageView storyImage;
    CircleImageView storyUserImage;
    private TextView storyUserName,view;
    private ProgressBar storyProgress;
    private ArrayList<UserStory> storyUsers;
    private ArrayList<StoryModel> stories;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String DATA_STORY_USERS = "StoryUsers";

    // private DrService weService;
    long pressTime = 0L;
    long limit = 800L;
    SharedPref sharedPref;
    String uid;
    ImageView play;
    Retrofit retrofit;
    APIService service;
    StatusAdapter statusAdapter;
    ArrayList<StoryModel> category;
    RecyclerView recyclerView;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_status);
        storyUsers= new ArrayList<>();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        view=findViewById(R.id.view);
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        storyUsers = getIntent().getParcelableArrayListExtra(DATA_STORY_USERS);
        initUi();
      //  Log.e("Leng","A"+storyUsers.size());
        startStory(counterStory);
    }

    private void startStory(int pos) {
        resetComplete();
       Glide.with(this)
                .load(getString(R.string.base_url)+"utredns_admires/content/uploads/"+storyUsers.get(pos).getPhoto())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Helper.dp2px(this, 8))).override(Helper.dp2px(this, 38), Helper.dp2px(this, 38)).placeholder(R.drawable.photo_error))
                .into(storyUserImage);
        storyUserName.setText(storyUsers.get(pos).getName());
        storyProgress.setVisibility(View.VISIBLE);
        Call<ArrayList<StoryModel>> userCall = service.get_story_user(storyUsers.get(pos).getId());
        userCall.enqueue(new Callback<ArrayList<StoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StoryModel>> call, Response<ArrayList<StoryModel>> response) {
                //  progressDialog.dismiss();
                try {
                    setupStories(response.body());
                   // category=response.body().getData().getItems();
                    //statusAdapter = new StatusAdapter(StatusActivity.this, category    );
                    //recyclerView.setAdapter(statusAdapter);
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<StoryModel>> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                storyProgress.setVisibility(View.INVISIBLE);
                Log.d("onFailure", t.toString());
            }
        });


    }

    public  void addview(String id)
    {
        Call<RegisterModel> userCall = service.AddStoryview(id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                //  progressDialog.dismiss();
                try {
                   }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                storyProgress.setVisibility(View.INVISIBLE);
                Log.d("onFailure", t.toString());
            }
        });
    }
    private void resetComplete() {
        Field field = null;
        try {
            field = storiesProgressView.getClass().getDeclaredField("isComplete");
            // Allow modification on the field
            field.setAccessible(true);
            // Sets the field to the new value for this instance
            field.set(storiesProgressView, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void setupStories(ArrayList<StoryModel> posts) {
        counterImage = 0;
        this.stories = posts;
        storiesProgressView.setStoriesCount(stories.size());
        storiesProgressView.setStoryDuration(3000L);
        storiesProgressView.setStoriesListener(StatusActivity.this);
        view.setText(""+stories.get(counterImage).getStory_view());
        addview(""+stories.get(counterImage).getId());
        if(stories.get(counterImage).getType().equalsIgnoreCase("photo")) {
            storyImage.setVisibility(View.VISIBLE);
            play.setVisibility(View.GONE);
            Glide.with(this).load(getString(R.string.base_url)+"API/" + stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    storyProgress.setVisibility(View.INVISIBLE);
                    storiesProgressView.startStories();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    storyProgress.setVisibility(View.INVISIBLE);
                    storiesProgressView.startStories();
                    return false;
                }
            }).into(storyImage);
        }
        else {
            storyImage.setVisibility(View.VISIBLE);
            play.setVisibility(View.VISIBLE);
            Glide.with(this).load(getString(R.string.base_url)+"API/" + stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    storyProgress.setVisibility(View.INVISIBLE);
                    storiesProgressView.startStories();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    storyProgress.setVisibility(View.INVISIBLE);
                    storiesProgressView.startStories();
                    return false;
                }
            }).into(storyImage);
        }
    }
    private void initUi() {
        storyImage = findViewById(R.id.image);
        play = findViewById(R.id.video);
        storyUserImage = findViewById(R.id.storyUserImage);
        storyUserName = findViewById(R.id.storyUserName);
        storiesProgressView = findViewById(R.id.stories);
        storyProgress = findViewById(R.id.storyProgress);
        // bind reverse view
        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(StatusActivity.this, FullStoryActivity.class);
                    i.putExtra("url", "" + stories.get(counterImage).getSrc());
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onNext() {
        counterImage++;
        if (counterImage < stories.size()) {
            //storiesProgressView.pause();
            addview(""+stories.get(counterImage).getId());
            if(stories.get(counterImage).getType().equalsIgnoreCase("photo")) {

                Glide.with(this).load(getString(R.string.base_url)+"API/" + stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
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
                }).into(storyImage);
            }else {
                storyImage.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                Glide.with(this).load(getString(R.string.base_url)+"API/" + stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.photo_error)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        storyProgress.setVisibility(View.INVISIBLE);
                        storiesProgressView.startStories();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        storyProgress.setVisibility(View.INVISIBLE);
                        storiesProgressView.startStories();
                        return false;
                    }
                }).into(storyImage);
            }
        }
    }

    @Override
    public void onPrev() {
        if (counterImage > 0) {
            counterImage--;
            //storiesProgressView.pause();
            if(stories.get(counterImage).getType().equalsIgnoreCase("photo")) {

                Glide.with(this).load(getString(R.string.base_url)+"API/"+stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.logo)).listener(new RequestListener<Drawable>() {
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
            }).into(storyImage);}
        else {
            storyImage.setVisibility(View.VISIBLE);
            play.setVisibility(View.GONE);
            Uri uri = Uri.parse(getString(R.string.base_url)+"API/"+stories.get(counterImage).getSrc());
         // buildMediaSource(uri);
                Glide.with(this).load(getString(R.string.base_url)+"API/"+stories.get(counterImage).getSrc()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.logo)).listener(new RequestListener<Drawable>() {
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
                }).into(storyImage);
        }
        } else if (counterStory != 0) {
            counterStory--;
            storiesProgressView.destroy();
            startStory(counterStory);
        }
    }

    @Override
    public void onComplete() {
        //finish();
        counterStory++;
        if (counterStory < storyUsers.size()) {
            startStory(counterStory);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

    public static Intent newIntent(Context context, ArrayList<UserStory> storyUsers, int pos) {
        Intent intent = new Intent(context, StatusActivity.class);
        ArrayList<UserStory> toPass = new ArrayList<>();
        for (int i = pos; i < storyUsers.size(); i++) {
            toPass.add(storyUsers.get(i));
        }
        intent.putParcelableArrayListExtra(DATA_STORY_USERS, toPass);
        return intent;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        sharedPref=new SharedPref(StatusActivity.this);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        uid=getIntent().getExtras().getString("id");
        GetStory();
    }
    public  void GetStory()
    {

        Call<StoryUser> userCall = service.get_story_user(uid);
        userCall.enqueue(new Callback<StoryUser>() {
            @Override
            public void onResponse(Call<StoryUser> call, Response<StoryUser> response) {
                //  progressDialog.dismiss();
                try {

                    category=response.body().getData().getItems();
                    statusAdapter = new StatusAdapter(StatusActivity.this, category    );
                    recyclerView.setAdapter(statusAdapter);
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<StoryUser> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }*/
}
