package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.webapps.viral.R;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.adapter.VPTrendAdapter;
import com.webapps.viral.adapter.ViewPagerAdapter;
import com.webapps.viral.model.CategoryModel;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.model.UserStory;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrendingActivity extends AppCompatActivity {
    RecyclerView rv_post;
    ArrayList<PostModel> postModels;
    PostAdapter postAdapter;
    Retrofit retrofit;
    APIService service;
    SharedPref sharedPref;
    String uid;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    VPTrendAdapter viewPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        postModels = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("Trending Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Trending Posts");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        rv_post = findViewById(R.id.recyclerview);
        rv_post.setHasFixedSize(false);
        rv_post.setNestedScrollingEnabled(true);
        rv_post.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service = retrofit.create(APIService.class);
        sharedPref = new SharedPref(this);
        uid = sharedPref.getID();
        Login();
    }

    public void Login() {

        Call<CategoryModel> userCall = service.GetCategory();
        userCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus() == 1) {
                        // recipientLists= response.body().getMessage();
                        viewPagerAdapter = new VPTrendAdapter(getSupportFragmentManager(), response.body().getData());
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPager.setOffscreenPageLimit(5);
                        tabLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                tabLayout.setupWithViewPager(viewPager);
                            }
                        });
                    } else {
                        Toast.makeText(TrendingActivity.this, "" + response.body().getMsg(), Toast.LENGTH_LONG).show();

                    }
                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
               // Toast.makeText(TrendingActivity.this, "Server Error", Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
