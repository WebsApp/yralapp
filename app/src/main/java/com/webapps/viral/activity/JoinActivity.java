package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.webapps.viral.R;
import com.webapps.viral.adapter.ChallengeAdapter;
import com.webapps.viral.adapter.JoinAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;
import com.webapps.viral.utils.SharedPreferenceUtil;
import com.webapps.viral.utils.ShowHideViewListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoinActivity extends AppCompatActivity {

    String c="Challenge";
    JoinAdapter foodAdapter;

    RecyclerView rv_food;
    ArrayList<ChildModel> foodlist,mislist;
    ArrayList<PostModel>   postModels;

    TextView back;
    String id;
    ArrayList<ChildModel>   donation;
    Retrofit retrofit;
    APIService service;

    EditText search;
    Button challenge;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    final int REQUEST_CODE_PERMISSION = 55;
    ShowHideViewListener showHideViewListener;
    ImageView error;
    private static float MAX_SWIPE_DISTANCE_FACTOR = 0.6f;
    private static int DEFAULT_REFRESH_TRIGGER_DISTANCE = 200;
    private static final int HIDE_THRESHOLD = 25;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true, wait;
    View emptyView;
    TextView empty_view_text;

    SharedPref sharedPref;
    LinearLayoutManager linearLayoutManager;
    private String postType;
    SharedPreferenceUtil sharedPreferenceUtil;
    int pageNumber = 1, userId;
    boolean allDone, isLoading, storyProgress, showStory;
    private int refreshTriggerDistance = DEFAULT_REFRESH_TRIGGER_DISTANCE;

    private NestedScrollView.OnScrollChangeListener nestedScrollViewChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY > oldScrollY) {
            }
            if (scrollY < oldScrollY) {
            }

            if (showHideViewListener != null && !wait) {
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    showHideViewListener.hideView();
                    wait = true;
                    controlsVisible = false;
                    scrolledDistance = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wait = false;
                        }
                    }, 600);
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    showHideViewListener.showView();
                    wait = true;
                    controlsVisible = true;
                    scrolledDistance = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wait = false;
                        }
                    }, 600);
                }
                int dy = scrollY - oldScrollY;
                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;
                }
            }

            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && !isLoading && !allDone) {
                        Log.e("CHECK_LAST", "loading more");
                        pageNumber++;
                        //homeRecyclerAdapter.showLoading();
                        GetPost();
                    }
                }
            }
        }
    };
    public void refresh() {
        pageNumber = 1;
        foodAdapter.clear();
        allDone = false;
        GetPost();
        emptyView.setVisibility(View.GONE);
        rv_food.setVisibility(View.VISIBLE);
    }
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        try {
            id=getIntent().getExtras().getString("id");
            Log.e("id",""+id);

        }catch (Exception e){

        }
        sharedPref= new SharedPref(this);
        shimmerFrameLayout=findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();
        emptyView = findViewById(R.id.empty_view_container);
        empty_view_text = findViewById(R.id.empty_view_text);
        linearLayoutManager = new LinearLayoutManager(this);
        swipeRefreshLayout = findViewById(R.id.frag_home_feeds_swipe_refresh_layout);
        nestedScrollView = findViewById(R.id.scrollView);
        nestedScrollView.setOnScrollChangeListener(nestedScrollViewChangeListener);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        challenge=findViewById(R.id.challenge);
        error=findViewById(R.id.error);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Float mDistanceToTriggerSync = Math.min(swipeRefreshLayout.getHeight() * MAX_SWIPE_DISTANCE_FACTOR, refreshTriggerDistance * metrics.density);
        swipeRefreshLayout.setDistanceToTriggerSync(mDistanceToTriggerSync.intValue());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        rv_food= findViewById(R.id.rv_food);
        foodlist = new ArrayList<>();
        postModels = new ArrayList<>();
        rv_food=findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(true);
        rv_food.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Log.e("Po",""+c);
        GetPost();

    }
    public  void GetPost()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        Call<ArrayList<PostModel>> userCall = service.GetChPost(""+c,""+id);
        userCall.enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                //  progressDialog.dismiss();
                try {

                    postModels=response.body();
                    if(postModels.size()>0) {
                        Log.e("Leng", "A" + postModels.size());
                        foodAdapter = new JoinAdapter(JoinActivity.this, postModels);
                        rv_food.setAdapter(foodAdapter);  // Toast.makeText(AddActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    }
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                //progressDialog.dismiss();
                error.setVisibility(View.VISIBLE);
                rv_food.setVisibility(View.GONE);
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
}